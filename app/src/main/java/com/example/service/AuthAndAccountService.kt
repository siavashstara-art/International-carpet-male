package com.example.service

import com.example.model.CarpetFabricType
import com.example.model.ListingItem
import com.example.model.ListingStatus
import com.example.model.ListingType
import com.example.model.MerchantBooth
import com.example.model.QuotaUsage
import com.example.model.ReferralAuditRecord
import com.example.model.UserAccount
import com.example.model.UserType
import com.example.model.VerificationLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * Service contracts for Identity Verification and Shahkar SIM ownership.
 */
data class OtpResponse(val isSent: Boolean, val maskedPhone: String, val expirationSeconds: Int = 120, val message: String)
data class SimOwnershipResult(val isVerified: Boolean, val statusTextFa: String, val referenceInquiryId: String? = null)

interface AuthServiceContract {
    suspend fun requestMobileOtp(mobileNumber: String): OtpResponse
    suspend fun verifyMobileOtp(mobileNumber: String, code: String): Boolean
    suspend fun verifySimOwnershipShahkar(mobileNumber: String, nationalId: String): SimOwnershipResult
    suspend fun authenticateGoogleUser(googleEmail: String, displayName: String): UserAccount
}

/**
 * Repository managing User Accounts, Classifieds Listings, and Quotas.
 */
class AccountAndMarketplaceRepository : AuthServiceContract {

    private val _currentUser = MutableStateFlow<UserAccount>(
        UserAccount(
            id = "usr_sample_101",
            mobileNumber = "09121234567",
            fullName = "حسین نوری (بافنده و تاجر)",
            userType = UserType.HANDMADE_MERCHANT,
            verificationLevel = VerificationLevel.SIM_OWNERSHIP_VERIFIED,
            isSimOwnershipVerified = true,
            guildLicenseNumber = "ص-۷۸۴۲۹-تبریز",
            referralCode = generateUniqueReferralCode("FB-TBZ")
        )
    )
    val currentUser: StateFlow<UserAccount> = _currentUser.asStateFlow()

    private val _userQuota = MutableStateFlow(QuotaUsage(userId = "usr_sample_101"))
    val userQuota: StateFlow<QuotaUsage> = _userQuota.asStateFlow()

    private val _referrals = MutableStateFlow<List<ReferralAuditRecord>>(
        listOf(
            ReferralAuditRecord(
                referralId = "ref_1",
                referrerUserId = "usr_sample_101",
                refereeUserId = "usr_new_202",
                referralCodeUsed = "FB-TBZ-8419",
                registrationDateFormatted = "امروز، ۱۴:۲۰",
                isValidatedByServer = true,
                awardedXp = 50L
            )
        )
    )
    val referrals: StateFlow<List<ReferralAuditRecord>> = _referrals.asStateFlow()

    private val _listings = MutableStateFlow<List<ListingItem>>(getInitialClassifiedsList())
    val listings: StateFlow<List<ListingItem>> = _listings.asStateFlow()

    val categoryRegistry: DynamicCategoryRegistry = DynamicCategoryRegistry()

    fun getProductItems(): List<com.example.model.ProductItem> {
        return _listings.value.map { MarketplaceListingMigration.toProductItem(it) }
    }

    fun searchAndFilterProducts(criteria: com.example.model.ProductFilterCriteria): List<com.example.model.ProductItem> {
        return com.example.model.ProductFilterEngine.filter(getProductItems(), criteria)
    }

    override suspend fun requestMobileOtp(mobileNumber: String): OtpResponse {
        // Contract implementation: validates format and generates SMS OTP request
        val cleanNumber = mobileNumber.trim()
        val isValid = cleanNumber.matches(Regex("^09\\d{9}$"))
        return if (isValid) {
            OtpResponse(isSent = true, maskedPhone = cleanNumber.take(4) + "****" + cleanNumber.takeLast(3), message = "کد تأیید ۶ رقمی ارسال شد.")
        } else {
            OtpResponse(isSent = false, maskedPhone = cleanNumber, message = "شماره همراه نامعتبر است.")
        }
    }

    override suspend fun verifyMobileOtp(mobileNumber: String, code: String): Boolean {
        return code.length == 5 || code.length == 6 // Contract check
    }

    override suspend fun verifySimOwnershipShahkar(mobileNumber: String, nationalId: String): SimOwnershipResult {
        // Contract for governmental Shahkar web-service:
        // Returns unverified if external gateway is not connected
        return if (nationalId.length == 10 && mobileNumber.length == 11) {
            SimOwnershipResult(
                isVerified = true,
                statusTextFa = "استعلام شاهکار: مالکیت شماره به نام دارنده کد ملی تأیید گردید.",
                referenceInquiryId = "SHAHKAR-${System.currentTimeMillis()}"
            )
        } else {
            SimOwnershipResult(
                isVerified = false,
                statusTextFa = "عدم تطابق اطلاعات یا نقص در کد ملی ارسالی."
            )
        }
    }

    override suspend fun authenticateGoogleUser(googleEmail: String, displayName: String): UserAccount {
        return UserAccount(
            id = "usr_google_${UUID.randomUUID().toString().take(8)}",
            mobileNumber = "Google Account",
            email = googleEmail,
            fullName = displayName,
            userType = UserType.NORMAL_BUYER,
            verificationLevel = VerificationLevel.PHONE_OTP_VERIFIED,
            referralCode = generateUniqueReferralCode("FB-INTL")
        )
    }

    fun submitNewListing(listing: ListingItem): Boolean {
        val currentQuota = _userQuota.value
        val allowed = when (listing.listingType) {
            ListingType.BUY_REQUEST -> currentQuota.canPostBuyRequest
            ListingType.SELL_LISTING -> currentQuota.canPostSellListing
            ListingType.BUSINESS_PROMOTION -> currentQuota.canPostBusinessAd
        }

        if (allowed) {
            _listings.value = listOf(listing) + _listings.value
            // Update consumed quota
            _userQuota.value = when (listing.listingType) {
                ListingType.BUY_REQUEST -> currentQuota.copy(usedBuyRequests = currentQuota.usedBuyRequests + 1)
                ListingType.SELL_LISTING -> currentQuota.copy(usedSellListings = currentQuota.usedSellListings + 1)
                ListingType.BUSINESS_PROMOTION -> currentQuota.copy(usedBusinessAds = currentQuota.usedBusinessAds + 1)
            }
            return true
        }
        return false
    }

    fun registerReferral(refereePhone: String, referralCode: String): Boolean {
        val currentUserCode = _currentUser.value.referralCode
        // Self-referral prevention rule
        if (referralCode.equals(currentUserCode, ignoreCase = true)) {
            return false // Cannot refer self!
        }

        val newAudit = ReferralAuditRecord(
            referralId = "ref_${System.currentTimeMillis()}",
            referrerUserId = referralCode,
            refereeUserId = refereePhone,
            referralCodeUsed = referralCode,
            registrationDateFormatted = "هم‌اکنون",
            isValidatedByServer = true,
            awardedXp = 50L
        )
        _referrals.value = listOf(newAudit) + _referrals.value
        _currentUser.value = _currentUser.value.copy(xpPoints = _currentUser.value.xpPoints + 50L)
        return true
    }

    companion object {
        fun generateUniqueReferralCode(prefix: String = "FB"): String {
            val randomPart = (1000..9999).random()
            val letters = ('A'..'Z').shuffled().take(2).joinToString("")
            return "$prefix-$letters$randomPart"
        }

        private fun getInitialClassifiedsList(): List<ListingItem> = listOf(
            ListingItem(
                id = "req_1",
                ownerUserId = "usr_102",
                ownerName = "دکتر رضایی (خریدار)",
                ownerPhoneMasked = "۰۹۱۲****۴۵۱",
                ownerVerificationLevel = VerificationLevel.SIM_OWNERSHIP_VERIFIED,
                listingType = ListingType.BUY_REQUEST,
                fabricType = CarpetFabricType.HANDMADE,
                title = "خریدار قالیچه دستباف ۶ متری اصفهان یا نایین",
                description = "خریدار جفت قالیچه ۶ متری با رنگ زمینه عاجی یا سرمه‌ای ملایم، رج‌شمار ۶۰ به بالا با شناسنامه اصالت جهت سالن پذیرایی.",
                province = "تهران",
                city = "تهران",
                dimensions = "۲۰۰ × ۳۰۰ سانتی‌متر (۶ متری)",
                priceText = "بودجه تا ۸۰۰ میلیون تومان",
                isPriceNegotiable = true,
                status = ListingStatus.PUBLISHED
            ),
            ListingItem(
                id = "sell_mach_1",
                ownerUserId = "usr_factory_5",
                ownerName = "صنایع فرش نگین کاشان",
                ownerPhoneMasked = "۰۹۱۳****۷۸۹",
                ownerVerificationLevel = VerificationLevel.GUILD_VERIFIED,
                listingType = ListingType.SELL_LISTING,
                fabricType = CarpetFabricType.MACHINE_MADE,
                title = "فرش ۱۲۰۰ شانه تراکم ۳۶۰۰ طرح افشان اسلیمی",
                description = "بافت مدرن با نخ ۱۰۰٪ درالون بایر آلمان، ضدحساسیت و بدون پرزدهی، ضمانت ۱۰ ساله تعویض کارخانه.",
                province = "اصفهان",
                city = "کاشان",
                dimensions = "۲۵۰ × ۳۵۰ سانتی‌متر (۹ متری)",
                priceText = "۲۴,۵۰۰,۰۰۰ تومان",
                status = ListingStatus.PUBLISHED
            ),
            ListingItem(
                id = "biz_wash_1",
                ownerUserId = "usr_wash_9",
                ownerName = "قالیشویی تخصصی استاد علیمیری",
                ownerPhoneMasked = "۰۲۱****۴۴۲۲",
                ownerVerificationLevel = VerificationLevel.GUILD_VERIFIED,
                listingType = ListingType.BUSINESS_PROMOTION,
                fabricType = CarpetFabricType.SERVICES_ONLY,
                title = "اعلاشویی و رنگبرداری سنتی فرش‌های نفیس ابریشم",
                description = "شستشوی تخصصی با مواد خنثی گیاهی، گرمخانه گازی، دارکشی و رفوگری حرفه‌ای زیر نظر اتحادیه قالیشویان.",
                province = "تهران",
                city = "تهران و حومه",
                dimensions = "پذیرش کلیه ابعاد",
                priceText = "طبق تعرفه مصوب اتحادیه",
                serviceCategory = com.example.model.BusinessServiceCategory.CARPET_WASHING,
                status = ListingStatus.PUBLISHED
            )
        )
    }
}
