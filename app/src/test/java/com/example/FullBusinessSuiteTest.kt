package com.example

import com.example.model.CarpetFabricType
import com.example.model.ListingItem
import com.example.model.ListingStatus
import com.example.model.ListingType
import com.example.model.QuotaPolicy
import com.example.model.QuotaUsage
import com.example.model.UserAccount
import com.example.model.UserType
import com.example.model.VerificationLevel
import com.example.service.AccountAndMarketplaceRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FullBusinessSuiteTest {

    @Test
    fun `verify mobile OTP authentication lifecycle and phone normalization`() = runBlocking {
        val repo = AccountAndMarketplaceRepository()

        // 1. Invalid phone number format rejection
        val invalidPhoneResult = repo.requestMobileOtp("12345")
        assertFalse("Invalid phone must be rejected", invalidPhoneResult.isSent)

        // 2. Valid Iranian phone request
        val validPhone = "09123456789"
        val otpReqResult = repo.requestMobileOtp(validPhone)
        assertTrue("Valid Iranian mobile should receive OTP prompt", otpReqResult.isSent)
        assertTrue("Masked phone should hide center digits", otpReqResult.maskedPhone.contains("****"))

        // 3. Short OTP code validation
        val badVerify = repo.verifyMobileOtp(validPhone, "12")
        assertFalse("Short OTP code must fail", badVerify)

        // 4. Valid 5-6 digit OTP code
        val successVerify = repo.verifyMobileOtp(validPhone, "123456")
        assertTrue("Correct OTP should pass", successVerify)
    }

    @Test
    fun `verify Shahkar SIM ownership contract behavior`() = runBlocking {
        val repo = AccountAndMarketplaceRepository()

        // 1. Incomplete national ID must be rejected by contract
        val emptyNationalIdResult = repo.verifySimOwnershipShahkar("09121112233", nationalId = "123")
        assertFalse("Incomplete national ID must not pass Shahkar verification", emptyNationalIdResult.isVerified)

        // 2. Valid 10-digit national ID and 11-digit phone simulates contract check
        val validNationalId = "0012345678"
        val shahkarResult = repo.verifySimOwnershipShahkar("09121112233", nationalId = validNationalId)
        assertTrue("Shahkar contract check should execute", shahkarResult.isVerified)
        assertNotNull(shahkarResult.referenceInquiryId)
        assertTrue("Status message should confirm Shahkar check", shahkarResult.statusTextFa.contains("شاهکار"))
    }

    @Test
    fun `verify quota enforcement with strict non-free policy`() = runBlocking {
        val repo = AccountAndMarketplaceRepository()
        val user = repo.currentUser.value

        // Setup a quota with strict limit (free tier inactive, max 2 buy requests)
        val strictPolicy = QuotaPolicy(
            isLaunchFreeTierActive = false,
            freeBuyRequestsLimit = 2,
            freeSellListingsLimit = 2
        )
        val strictQuota = QuotaUsage(
            userId = user.id,
            usedBuyRequests = 2, // Quota already exhausted
            usedSellListings = 0,
            policy = strictPolicy
        )

        assertFalse("Exhausted buy requests should block new submissions", strictQuota.canPostBuyRequest)
        assertTrue("Remaining sell listings should still be permitted", strictQuota.canPostSellListing)
        assertEquals(0, strictQuota.remainingBuyRequests)
        assertEquals(2, strictQuota.remainingSellListings)
    }

    @Test
    fun `verify Google international user authentication`() = runBlocking {
        val repo = AccountAndMarketplaceRepository()

        val googleUser = repo.authenticateGoogleUser(
            googleEmail = "collector@persiancarpets.global",
            displayName = "Robert Harrison"
        )

        assertNotNull(googleUser)
        assertEquals("Robert Harrison", googleUser.fullName)
        assertEquals("collector@persiancarpets.global", googleUser.email)
        assertEquals(VerificationLevel.PHONE_OTP_VERIFIED, googleUser.verificationLevel)
        assertTrue("Referral code should start with FB-INTL", googleUser.referralCode.startsWith("FB-INTL"))
    }
}
