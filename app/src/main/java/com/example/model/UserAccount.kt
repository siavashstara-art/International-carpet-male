package com.example.model

/**
 * User account types supported across the Farshbazar platform.
 */
enum class UserType(val titleFa: String) {
    NORMAL_BUYER("کاربر و خریدار عادی"),
    HANDMADE_MERCHANT("فروشنده و بافنده فرش دستباف"),
    MACHINE_FACTORY("کارخانه و تولیدکننده فرش ماشینی"),
    MACHINE_SELLER("فروشگاه و بنکدار فرش ماشینی"),
    SERVICE_SPECIALIST("متخصص خدمات (قالیشویی، رفوگری، رنگرزی، دارکشی)"),
    ADMIN("مدیر ارشد سامانه")
}

/**
 * Multi-tier identity verification statuses.
 * Differentiates SMS OTP from legal SIM ownership (Shahkar) and Guild licenses.
 */
enum class VerificationLevel(val titleFa: String, val badgeColorHex: Long) {
    UNVERIFIED("ثبت‌نام اولیه", 0xFF888888),
    PHONE_OTP_VERIFIED("شماره تأییدشده با پیامک", 0xFF3F51B5),
    SIM_OWNERSHIP_VERIFIED("مالکیت سیم‌کارت تأییدشده (شاهکار)", 0xFF009688),
    NATIONAL_ID_MATCHED("احراز هویت کامل هویتی", 0xFF2E7D32),
    GUILD_VERIFIED("دارای پروانه صنفی معتبر قالی", 0xFFD4AF37)
}

/**
 * Core User Account profile model.
 * Sensitive data (raw National ID, auth tokens) is strictly omitted from client representations.
 */
data class UserAccount(
    val id: String,
    val mobileNumber: String,
    val maskedMobileNumber: String = maskPhoneNumber(mobileNumber),
    val email: String? = null,
    val fullName: String,
    val userType: UserType = UserType.NORMAL_BUYER,
    val verificationLevel: VerificationLevel = VerificationLevel.PHONE_OTP_VERIFIED,
    val isSimOwnershipVerified: Boolean = false,
    val guildLicenseNumber: String? = null,
    val countryOrRegion: String = "ایران",
    val referralCode: String, // Unique, unguessable referral code (e.g., FB-9X28KP)
    val referredByCode: String? = null,
    val registrationTimestamp: Long = System.currentTimeMillis(),
    val monthlyAttendanceCount: Int = 1,
    val xpPoints: Long = 100L
)

/**
 * Referral link & audit relation record.
 */
data class ReferralAuditRecord(
    val referralId: String,
    val referrerUserId: String,
    val refereeUserId: String,
    val referralCodeUsed: String,
    val registrationDateFormatted: String,
    val isValidatedByServer: Boolean = true,
    val awardedXp: Long = 50L,
    val isAntiFraudPassed: Boolean = true
)

private fun maskPhoneNumber(phone: String): String {
    return if (phone.length >= 10) {
        phone.take(4) + "****" + phone.takeLast(3)
    } else phone
}
