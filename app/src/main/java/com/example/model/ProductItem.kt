package com.example.model

/**
 * Intent of the product ad: strictly separates buyer buy-requests from seller sale-offers.
 */
enum class ListingIntent(val titleFa: String) {
    BUY_REQUEST("تقاضای خرید (متقاضی)"),
    SELL_OFFER("پیشنهاد فروش (فروشنده)")
}

/**
 * Production technique for carpets and woven crafts.
 */
enum class ProductionMethod(val titleFa: String) {
    HANDMADE_KNOTTED("دستباف پرزدار و گره‌دار"),
    HANDMADE_FLATWEAVE("دستباف تخت‌بافت (بدون پرز)"),
    MACHINE_WOVEN("بافت ماشینی صنعتی"),
    EMBROIDERED_NEEDLEWORK("سوزن‌دوزی، تلفیقی و سنتی"),
    UNKNOWN("نامشخص / ذکرنشده")
}

/**
 * Physical condition of the item.
 */
enum class ItemCondition(val titleFa: String) {
    NEW("نو و استفاده‌نشده (چله‌بریده)"),
    LIKE_NEW("در حد نو (بسیار تمیز و کم‌کارکرد)"),
    USED("دست دوم (سالم و قابل استفاده)"),
    VINTAGE_ANTIQUE("آنتیک، تاریخی و نیازمند نگهداری خاص"),
    UNSPECIFIED("ذکر نشده")
}

/**
 * Universal product and listing data model for the Farshbazar marketplace.
 *
 * Rule Compliance:
 * - Belongs to exactly ONE primary category ([primaryCategoryId]).
 * - Supports multiple tags ([tags]) and filter attributes.
 * - Non-mandatory attributes (dimensions, raw materials, origin) are nullable to avoid guessing.
 * - Strictly differentiates buyer requests from seller offerings via [intent].
 */
data class ProductItem(
    val id: String,
    val title: String,
    val description: String,
    val primaryCategoryId: String,
    val subcategoryId: String? = null,
    val tags: List<String> = emptyList(),
    val imageUrls: List<String> = emptyList(),
    val imageResIds: List<Int> = emptyList(),
    // Product specifications (Optional / Nullable when unknown)
    val dimensions: String? = null,
    val rawMaterials: String? = null,
    val productionMethod: ProductionMethod = ProductionMethod.UNKNOWN,
    val condition: ItemCondition = ItemCondition.UNSPECIFIED,
    // Origin must NEVER be fabricated or guessed; null if unconfirmed
    val verifiedOriginRegion: String? = null,
    // Pricing (Optional if price on call or buyer negotiation)
    val priceToman: Long? = null,
    val priceUsd: Long? = null,
    val isPriceNegotiable: Boolean = false,
    val isPriceOnCall: Boolean = false,
    // Listing Intent (Strict separation between Buyer Requests and Seller Offers)
    val intent: ListingIntent = ListingIntent.SELL_OFFER,
    // Seller / Buyer Information
    val sellerUserId: String,
    val sellerName: String,
    val sellerPhoneMasked: String,
    val sellerProvince: String? = null,
    val sellerCity: String? = null,
    val sellerVerificationLevel: VerificationLevel = VerificationLevel.UNVERIFIED,
    // Booth Association & Permission Constraints
    val boothId: String? = null,
    val isHandmadeCarpetBoothApproved: Boolean = false,
    val isMachineCarpetBoothApproved: Boolean = false,
    val creationTimestamp: Long = System.currentTimeMillis()
)
