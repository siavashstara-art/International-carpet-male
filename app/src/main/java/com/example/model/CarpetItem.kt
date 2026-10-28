package com.example.model

data class CarpetItem(
    val id: String,
    val titleFa: String,
    val titleEn: String,
    val originCityFa: String,
    val originCityEn: String,
    val stylePatternFa: String,
    val stylePatternEn: String,
    val dimensions: String,
    val knotDensityRaj: Int, // رج‌شمار (e.g., 70, 60, 50)
    val knotTypeFa: String, // گره متقارن ترکی یا گره نامتقارن فارسی
    val knotTypeEn: String,
    val materialFa: String,
    val materialEn: String,
    val dyeingTypeFa: String,
    val weftCountFa: String,
    val weaverMaster: String,
    val certificateId: String,
    val priceToman: Long,
    val priceUsd: Long,
    val storyNarrativeFa: String,
    val storyNarrativeEn: String,
    val imageResId: Int,
    val isFeaturedShowcase: Boolean = false,
    val isMasterpiece: Boolean = false,
    val merchantName: String,
    val merchantLocation: String,
    val merchantPhone: String,
    val merchantRating: Float = 4.9f,
    val merchantVerified: Boolean = true,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

enum class CarpetCategory(val titleFa: String, val titleEn: String) {
    ALL("همه آثار", "All Carpets"),
    MUSEUM("شاهکارهای موزه‌ای", "Museum Pieces"),
    SILK("ابریشم نفیس", "Pure Silk"),
    CLASSIC("اصفهان و نایین", "Isfahan & Nain"),
    TRIBAL("عشایری و گبه", "Nomadic & Gabbeh"),
    ANTIQUE("عتیقه و آنتیک", "Antique Heritage")
}

enum class Currency(val symbolFa: String, val symbolEn: String, val rateFromToman: Double) {
    TOMAN("تومان", "Toman", 1.0),
    USD("$", "USD", 1.0 / 60_000.0),
    EUR("€", "EUR", 1.0 / 65_000.0)
}
