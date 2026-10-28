package com.example.model

enum class ListingType(val titleFa: String) {
    BUY_REQUEST("درخواست خرید قالی"),
    SELL_LISTING("آگهی فروش فرش"),
    BUSINESS_PROMOTION("تبلیغات کسب‌وکار و خدمات")
}

enum class CarpetFabricType(val titleFa: String) {
    HANDMADE("دستباف اصیل"),
    MACHINE_MADE("ماشینی مدرن"),
    SERVICES_ONLY("خدمات تخصصی فرش")
}

data class MachineCarpetSpecs(
    val reedsCount: Int, // شانه (e.g. 700, 1200, 1500)
    val density: Int, // تراکم (e.g. 2550, 3600, 4500)
    val yarnMaterial: String = "۱۰۰٪ اکریلیک هیت‌ست آلمان",
    val factoryBrandName: String
)

enum class BusinessServiceCategory(val titleFa: String) {
    CARPET_WASHING("قالیشویی مجاز و اعلاشویی"),
    POLISHING_FINISHING("پرداخت‌کاری و موگیری قالی"),
    DYEING("رنگرزی سنتی گیاهی و شیمیایی"),
    LOOM_STRETCHING("دارکشی و رفع کجی فرش"),
    MENDING("رفوگری، ریشه‌بافی و مرمت عتیقه"),
    RAW_MATERIALS("چله، خامه و ابزار قالیبافی"),
    OTHER_SERVICES("سایر خدمات تخصصی فرش")
}

enum class ListingStatus(val titleFa: String) {
    DRAFT("پیش‌نویس"),
    PENDING_REVIEW("در انتظار تایید مدیریت"),
    PUBLISHED("منتشر شده در بازار"),
    REJECTED("رد شده (نیاز به اصلاح)"),
    EXPIRED("منقضی شده"),
    INACTIVE("غیرفعال شده توسط کاربر")
}

/**
 * Unified Classifieds & Listings item.
 */
data class ListingItem(
    val id: String,
    val ownerUserId: String,
    val ownerName: String,
    val ownerPhoneMasked: String,
    val ownerVerificationLevel: VerificationLevel,
    val listingType: ListingType,
    val fabricType: CarpetFabricType,
    val title: String,
    val description: String,
    val province: String,
    val city: String,
    val dimensions: String,
    val priceText: String,
    val isPriceNegotiable: Boolean = false,
    val status: ListingStatus = ListingStatus.PUBLISHED,
    val imageResId: Int? = null,
    val machineSpecs: MachineCarpetSpecs? = null,
    val serviceCategory: BusinessServiceCategory? = null,
    val boothId: String? = null,
    val reportCount: Int = 0,
    val creationTimestamp: Long = System.currentTimeMillis()
)
