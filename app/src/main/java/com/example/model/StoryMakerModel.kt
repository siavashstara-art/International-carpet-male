package com.example.model

import android.graphics.Bitmap

enum class StoryTheme(val titleFa: String, val bgHex: Long, val accentHex: Long) {
    ROYAL_OBSIDIAN("مشکی مخملین و طلای مات", 0xFF0D0C10, 0xFFD4AF37),
    PARCHMENT_CLASSIC("پوست عاجی و تذهیب", 0xFFF7F4EB, 0xFF6B5320),
    MADDER_RUBY("روناس درباری و کهن", 0xFF5B101A, 0xFFE5C158)
}

data class StoryConfig(
    val carpetBitmap: Bitmap? = null,
    val title: String = "شاهکار ابریشم دستباف اصیل",
    val originCity: String = "تبریز",
    val raj: String = "۷۰ رج اعلا",
    val dimensions: String = "۲۰۰ × ۳۰۰ سانتی‌متر (۶ متری)",
    val priceText: String = "تماس / قیمت توافقی",
    val weaverName: String = "کارگاه استاد سالاری",
    val contactPhone: String = "۰۹۱۲۳۴۵۶۷۸۹",
    val socialHandle: String = "@Farshbazar_Official",
    val theme: StoryTheme = StoryTheme.ROYAL_OBSIDIAN,
    val showCertificateSeal: Boolean = true,
    val tagline: String = "بازار فرش ایران و جهان (دیجیتال مارکت)"
)

data class GeneratedStoryResult(
    val bitmap: Bitmap,
    val width: Int,
    val height: Int,
    val filePath: String? = null
)
