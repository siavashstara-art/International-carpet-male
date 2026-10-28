package ir.farshbazaar.domain.model

enum class Category(val id: String, val labelFa: String, val isActive: Boolean) {
    HANDMADE_RUG("HANDMADE_RUG", "فرش دستباف", true),
    MACHINE_RUG("MACHINE_RUG", "فرش ماشینی", true),
    SERVICES("SERVICES", "خدمات", true),
    GOODS("GOODS", "کالاها", false),
    HANDICRAFTS("HANDICRAFTS", "صنایع‌دستی", false),
    ARTWORKS("ARTWORKS", "آثار هنری", false);

    companion object {
        fun fromId(id: String): Category {
            return entries.find { it.id.equals(id, ignoreCase = true) } ?: HANDMADE_RUG
        }
    }
}

typealias RugCategory = Category

data class User(
    val id: String,
    val displayName: String,
    val bio: String,
    val isSeller: Boolean,
    val isDemo: Boolean = true,
    val city: String = "ایران",
    val isVerified: Boolean = true
)

typealias SellerUser = User

data class Rug(
    val id: String,
    val sellerId: String,
    val title: String,
    val description: String,
    val category: Category = Category.HANDMADE_RUG,
    val region: String,
    val era: String = "معاصر",
    val price: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val isDemo: Boolean = true,
    val raj: Int = 60,
    val knotType: String = "گره ترکی",
    val materials: String = "چله ابریشم، پرز کرک و ابریشم",
    val dimensions: String = "۲ × ۳ متر"
)

data class InspectionIndicator(
    val id: String,
    val titleFa: String,
    val titleEn: String = "",
    val bodyFa: String = "",
    val bodyEn: String = "",
    val category: String = "quality",
    val displayOrder: Int = 0,
    val isActive: Boolean = true,
    val isDemo: Boolean = true
)

data class Comment(
    val id: String,
    val rugId: String,
    val authorId: String,
    val authorName: String = "",
    val text: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isDemo: Boolean = true
)

enum class XpEventType(val basePoints: Int, val descriptionFa: String) {
    POST_CREATED(10, "ثبت فرش یا اثر در ویترین"),
    LIKE_RECEIVED(2, "دریافت پسند از علاقه‌مندان"),
    COMMENT_RECEIVED(0, "دریافت نظر کارشناسی (فاز ۲)"),
    FOLLOW_RECEIVED(0, "جذب دنبال‌کنندهٔ جدید (فاز ۲)");

    companion object {
        fun collaborationMultiplier(): Int = 1
    }
}
