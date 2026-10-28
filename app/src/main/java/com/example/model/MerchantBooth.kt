package com.example.model

enum class BoothType(val titleFa: String) {
    HANDMADE_PAVILION("غرفه تخصصی فرش دستباف"),
    MACHINE_MADE_PAVILION("غرفه فرش ماشینی و کارخانجات")
}

data class MerchantBooth(
    val id: String,
    val ownerUserId: String,
    val boothType: BoothType,
    val title: String,
    val city: String,
    val marketAddress: String,
    val tradeLicenseNumber: String? = null,
    val isGuildVerified: Boolean = true,
    val rating: Float = 4.95f,
    val activeListingCount: Int = 12,
    val contactPhoneMasked: String
)

/**
 * Server-controlled quota configuration.
 * Allows initial generous/free quotas without hardcoding client constants.
 */
data class QuotaPolicy(
    val policyVersion: String = "2026.1",
    val isLaunchFreeTierActive: Boolean = true, // Generous free launch policy
    val freeBuyRequestsLimit: Int = 5,
    val freeSellListingsLimit: Int = 10,
    val freeBusinessAdsLimit: Int = 3,
    val quotaCycleDays: Int = 30
)

data class QuotaUsage(
    val userId: String,
    val usedBuyRequests: Int = 1,
    val usedSellListings: Int = 2,
    val usedBusinessAds: Int = 0,
    val policy: QuotaPolicy = QuotaPolicy()
) {
    val remainingBuyRequests: Int
        get() = if (policy.isLaunchFreeTierActive) 999 else (policy.freeBuyRequestsLimit - usedBuyRequests).coerceAtLeast(0)

    val remainingSellListings: Int
        get() = if (policy.isLaunchFreeTierActive) 999 else (policy.freeSellListingsLimit - usedSellListings).coerceAtLeast(0)

    val remainingBusinessAds: Int
        get() = if (policy.isLaunchFreeTierActive) 999 else (policy.freeBusinessAdsLimit - usedBusinessAds).coerceAtLeast(0)

    val canPostBuyRequest: Boolean get() = remainingBuyRequests > 0
    val canPostSellListing: Boolean get() = remainingSellListings > 0
    val canPostBusinessAd: Boolean get() = remainingBusinessAds > 0
}
