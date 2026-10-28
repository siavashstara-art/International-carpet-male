package com.example.model

/**
 * Subcategory model allowing hierarchical categorization within a main category.
 * Dynamically configurable without app release.
 */
data class ProductSubcategory(
    val id: String,
    val categoryId: String,
    val titleFa: String,
    val descriptionFa: String = "",
    val orderIndex: Int = 0,
    val isEnabled: Boolean = true
)

/**
 * Extensible Product Category definition for the Iranian and Global Carpet Market.
 * Supports runtime addition, modification, or deactivation by authorized administrators.
 */
data class ProductCategory(
    val id: String,
    val titleFa: String,
    val descriptionFa: String,
    val iconIdentifier: String,
    val orderIndex: Int,
    val isEnabled: Boolean = true,
    val isSpecializedCarpetBoothRequired: Boolean = false,
    val linkedBoothType: BoothType? = null,
    val subcategories: List<ProductSubcategory> = emptyList()
)

/**
 * Canonical registry of the 15 standard product categories for Farshbazar.
 * All entries are extensible and manageable dynamically via [CategoryManagementContract].
 */
object CanonicalCategories {

    const val CAT_HANDMADE_CARPET = "cat_handmade_carpet"
    const val CAT_MACHINE_CARPET = "cat_machine_carpet"
    const val CAT_TABLEAU_CARPET = "cat_tableau_carpet"
    const val CAT_KILIM = "cat_kilim"
    const val CAT_JAJIM = "cat_jajim"
    const val CAT_ZILU = "cat_zilu"
    const val CAT_VERNEH_FLATWEAVE = "cat_verneh_flatweave"
    const val CAT_KHORJIN_JAWAL = "cat_khorjin_jawal"
    const val CAT_NAMAKDAN_STORAGE = "cat_namakdan_storage"
    const val CAT_TRADITIONAL_SOFREH = "cat_traditional_sofreh"
    const val CAT_HORSE_SADDLE_COVER = "cat_horse_saddle_cover"
    const val CAT_CUSHION_POSHTI_DECOR = "cat_cushion_poshti_decor"
    const val CAT_BAG_SHOE_ACCESSORIES = "cat_bag_shoe_accessories"
    const val CAT_VESSEL_COVERS_FUNCTIONAL = "cat_vessel_covers_functional"
    const val CAT_OTHER_WOVEN_HERITAGE = "cat_other_woven_heritage"

    val DEFAULT_CATEGORIES: List<ProductCategory> = listOf(
        ProductCategory(
            id = CAT_HANDMADE_CARPET,
            titleFa = "فرش دستباف",
            descriptionFa = "قالی، قالیچه، کناره و شاهکارهای دستباف اصیل با گره‌های متقارن و نامتقارن ایرانی",
            iconIdentifier = "ic_handmade_carpet",
            orderIndex = 1,
            isSpecializedCarpetBoothRequired = true,
            linkedBoothType = BoothType.HANDMADE_PAVILION,
            subcategories = listOf(
                ProductSubcategory("sub_hm_qali", CAT_HANDMADE_CARPET, "قالی بزرگ‌پارچه (۱۲، ۹ و ۶ متری)", orderIndex = 1),
                ProductSubcategory("sub_hm_qalicheh", CAT_HANDMADE_CARPET, "قالیچه و پرده‌ای (زرع‌ونیم و دوذرع)", orderIndex = 2),
                ProductSubcategory("sub_hm_runner", CAT_HANDMADE_CARPET, "کناره و راهرویی دستباف", orderIndex = 3),
                ProductSubcategory("sub_hm_nomadic", CAT_HANDMADE_CARPET, "گبه و بافته‌های عشایری و روستایی", orderIndex = 4)
            )
        ),
        ProductCategory(
            id = CAT_MACHINE_CARPET,
            titleFa = "فرش ماشینی",
            descriptionFa = "فرش‌های بافته‌شده ماشینی با شانه و تراکم استاندارد کارخانجات معتبر",
            iconIdentifier = "ic_machine_carpet",
            orderIndex = 2,
            isSpecializedCarpetBoothRequired = true,
            linkedBoothType = BoothType.MACHINE_MADE_PAVILION,
            subcategories = listOf(
                ProductSubcategory("sub_mc_700", CAT_MACHINE_CARPET, "۷۰۰ شانه تراکم ۲۵۵۰", orderIndex = 1),
                ProductSubcategory("sub_mc_1200", CAT_MACHINE_CARPET, "۱۲۰۰ شانه تراکم ۳۶۰۰ گل‌برجسته", orderIndex = 2),
                ProductSubcategory("sub_mc_1500", CAT_MACHINE_CARPET, "۱۵۰۰ شانه تراکم ۴۵۰۰ ابریشم‌نما", orderIndex = 3),
                ProductSubcategory("sub_mc_modern", CAT_MACHINE_CARPET, "وینتیج، شگی و فانتزی مدرن", orderIndex = 4)
            )
        ),
        ProductCategory(
            id = CAT_TABLEAU_CARPET,
            titleFa = "تابلوفرش و پرده‌فرش",
            descriptionFa = "تابلوفرش‌های دستباف و چاپی نفیس مینیاتور، آیات قرآنی، طبیعت و چهره",
            iconIdentifier = "ic_tableau_carpet",
            orderIndex = 3,
            subcategories = listOf(
                ProductSubcategory("sub_tab_handmade", CAT_TABLEAU_CARPET, "تابلوفرش دستباف سردرود و تبریز", orderIndex = 1),
                ProductSubcategory("sub_tab_printed", CAT_TABLEAU_CARPET, "تابلوفرش چاپی و کلاریس", orderIndex = 2),
                ProductSubcategory("sub_tab_pardeh", CAT_TABLEAU_CARPET, "پرده‌فرش و آویز دیواری سنتی", orderIndex = 3)
            )
        ),
        ProductCategory(
            id = CAT_KILIM,
            titleFa = "گلیم",
            descriptionFa = "انواع گلیم‌های دو رو، چاک‌دار، پودپیچ، هرسین، بیجار، قشقایی و شاهسون",
            iconIdentifier = "ic_kilim",
            orderIndex = 4,
            subcategories = listOf(
                ProductSubcategory("sub_kilim_qashqai", CAT_KILIM, "گلیم قشقایی و شیراز", orderIndex = 1),
                ProductSubcategory("sub_kilim_harsin", CAT_KILIM, "گلیم هرسین و کرمانشاه", orderIndex = 2),
                ProductSubcategory("sub_kilim_shahsevan", CAT_KILIM, "گلیم ورنی‌بافت و شاهسون", orderIndex = 3),
                ProductSubcategory("sub_kilim_modern", CAT_KILIM, "گلیم‌های دکوراتیو هندسی و مدرن", orderIndex = 4)
            )
        ),
        ProductCategory(
            id = CAT_JAJIM,
            titleFa = "جاجیم",
            descriptionFa = "دستبافته‌های رنگارنگ تارنما، راه‌راه و موج‌دار پشمی و پنبه‌ای مناطق مختلف ایران",
            iconIdentifier = "ic_jajim",
            orderIndex = 5,
            subcategories = listOf(
                ProductSubcategory("sub_jajim_ardabil", CAT_JAJIM, "جاجیم خلخال و اردبیل", orderIndex = 1),
                ProductSubcategory("sub_jajim_lori", CAT_JAJIM, "جاجیم لری و بختیاری", orderIndex = 2),
                ProductSubcategory("sub_jajim_silk", CAT_JAJIM, "جاجیم ابریشمی و ترکیبی", orderIndex = 3)
            )
        ),
        ProductCategory(
            id = CAT_ZILU,
            titleFa = "زیلو",
            descriptionFa = "زیرانداز تاریخی، بادوام، نخی و خنک مناسب اقلیم کویری بافته میبد و یزد",
            iconIdentifier = "ic_zilu",
            orderIndex = 6,
            subcategories = listOf(
                ProductSubcategory("sub_zilu_meybod", CAT_ZILU, "زیلوی سنتی میبد", orderIndex = 1),
                ProductSubcategory("sub_zilu_mosque", CAT_ZILU, "زیلوی محرابی و کتیبه‌دار", orderIndex = 2)
            )
        ),
        ProductCategory(
            id = CAT_VERNEH_FLATWEAVE,
            titleFa = "ورنی و سایر بافته‌های تخت منطقه‌ای",
            descriptionFa = "ورنی دشت مغان و ارسباران، سوماک، رند و دستبافته‌های بدون پرز گره‌پیچ",
            iconIdentifier = "ic_verneh",
            orderIndex = 7,
            subcategories = listOf(
                ProductSubcategory("sub_verneh_moghan", CAT_VERNEH_FLATWEAVE, "ورنی مغان و ارسباران", orderIndex = 1),
                ProductSubcategory("sub_verneh_soumak", CAT_VERNEH_FLATWEAVE, "سوماک سنتی", orderIndex = 2),
                ProductSubcategory("sub_verneh_shiriki", CAT_VERNEH_FLATWEAVE, "شیریکی‌پیچ سیرجان", orderIndex = 3)
            )
        ),
        ProductCategory(
            id = CAT_KHORJIN_JAWAL,
            titleFa = "خورجین، جوال و کیسه‌های بافتنی",
            descriptionFa = "بافته‌های عشایری دوطرفه حمل بار، جوال غلات، مفرش و کیسه‌های سنتی",
            iconIdentifier = "ic_khorjin",
            orderIndex = 8,
            subcategories = listOf(
                ProductSubcategory("sub_khorjin_antique", CAT_KHORJIN_JAWAL, "خورجین‌های آنتیک عشایری", orderIndex = 1),
                ProductSubcategory("sub_jawal_grain", CAT_KHORJIN_JAWAL, "جوال و مفرش پشمی", orderIndex = 2)
            )
        ),
        ProductCategory(
            id = CAT_NAMAKDAN_STORAGE,
            titleFa = "نمکدان و بافته‌های نگهداری و حمل مواد",
            descriptionFa = "کیسه‌های سنتی گلوباریک برای نگهداری سنگ نمک، حبوبات و دانه‌ها با نقوش اصیل",
            iconIdentifier = "ic_namakdan",
            orderIndex = 9
        ),
        ProductCategory(
            id = CAT_TRADITIONAL_SOFREH,
            titleFa = "سفره‌های سنتی و زیرانداز",
            descriptionFa = "سفره آردی، سفره نان، سفره کردی و زیراندازهای ضخیم سنتی بافندگی",
            iconIdentifier = "ic_sofreh",
            orderIndex = 10,
            subcategories = listOf(
                ProductSubcategory("sub_sofreh_ardi", CAT_TRADITIONAL_SOFREH, "سفره آردی عشایری", orderIndex = 1),
                ProductSubcategory("sub_sofreh_kordi", CAT_TRADITIONAL_SOFREH, "سفره نان و غذای کردی", orderIndex = 2)
            )
        ),
        ProductCategory(
            id = CAT_HORSE_SADDLE_COVER,
            titleFa = "جل اسب، پوشش زین و پالان",
            descriptionFa = "دستبافته‌های تشریفاتی و کاربردی اسب، پوشش زین و روپالانی نقشبند",
            iconIdentifier = "ic_horse_saddle",
            orderIndex = 11,
            subcategories = listOf(
                ProductSubcategory("sub_saddle_ceremonial", CAT_HORSE_SADDLE_COVER, "جل اسب تشریفاتی و عروسی", orderIndex = 1),
                ProductSubcategory("sub_saddle_turkmen", CAT_HORSE_SADDLE_COVER, "پوشش زین ترکمنی و قشقایی", orderIndex = 2)
            )
        ),
        ProductCategory(
            id = CAT_CUSHION_POSHTI_DECOR,
            titleFa = "پشتی، پادری، روکش کوسن و محصولات دکوراتیو",
            descriptionFa = "پشتی‌های سنتی پرشده، پادری‌های نفیس دستباف و روکش کوسن‌های فرشینه‌ای",
            iconIdentifier = "ic_decor_poshti",
            orderIndex = 12,
            subcategories = listOf(
                ProductSubcategory("sub_poshti_traditional", CAT_CUSHION_POSHTI_DECOR, "پشتی سنتی و لنگری", orderIndex = 1),
                ProductSubcategory("sub_padari_handmade", CAT_CUSHION_POSHTI_DECOR, "پادری دستباف و نیم‌متری", orderIndex = 2),
                ProductSubcategory("sub_cushion_cover", CAT_CUSHION_POSHTI_DECOR, "روکش کوسن گلیمی و قالیچه‌ای", orderIndex = 3)
            )
        ),
        ProductCategory(
            id = CAT_BAG_SHOE_ACCESSORIES,
            titleFa = "کیف، کفش و لوازم جانبی بافته‌شده",
            descriptionFa = "کیف‌های دوشی و دستی با ترکیب گلیم و چرم طبیعی، پاپوش و کفش‌های سنتی",
            iconIdentifier = "ic_accessories",
            orderIndex = 13,
            subcategories = listOf(
                ProductSubcategory("sub_bag_kilim_leather", CAT_BAG_SHOE_ACCESSORIES, "کیف تلفیقی گلیم و چرم", orderIndex = 1),
                ProductSubcategory("sub_shoe_giveh", CAT_BAG_SHOE_ACCESSORIES, "گیوه و پاپوش‌های دستبافت", orderIndex = 2)
            )
        ),
        ProductCategory(
            id = CAT_VESSEL_COVERS_FUNCTIONAL,
            titleFa = "پوشش‌های بافتنی ظروف و سایر دستبافته‌های کاربردی",
            descriptionFa = "کاورهای سنتی سماور، روکش‌های تزیینی کتری، کوزه، دیزی و قوری",
            iconIdentifier = "ic_vessel_covers",
            orderIndex = 14
        ),
        ProductCategory(
            id = CAT_OTHER_WOVEN_HERITAGE,
            titleFa = "سایر محصولات مرتبط با فرش و بافت سنتی",
            descriptionFa = "نوارهای چادر، تزیینات آلاچیق، ریسمان‌های رنگی، دار و لوازم تخصصی بافندگی",
            iconIdentifier = "ic_other_heritage",
            orderIndex = 15
        )
    )
}
