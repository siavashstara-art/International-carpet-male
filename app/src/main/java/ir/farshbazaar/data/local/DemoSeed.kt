package ir.farshbazaar.data.local

import ir.farshbazaar.data.local.entity.CommentEntity
import ir.farshbazaar.data.local.entity.InspectionIndicatorEntity
import ir.farshbazaar.data.local.entity.LikeEntity
import ir.farshbazaar.data.local.entity.RugEntity
import ir.farshbazaar.data.local.entity.UserEntity
import ir.farshbazaar.data.local.entity.XpEventEntity

object DemoSeed {

    val users = listOf(
        UserEntity(
            id = "user-self",
            displayName = "حاج احمد تهرانی (کاربر نمونه)",
            bio = "علاقه‌مند و پژوهشگر قالی‌های کهن دستباف ایران | خریدار آزمایشی فرشبازار",
            isSeller = false,
            isDemo = true
        ),
        UserEntity(
            id = "seller-salari",
            displayName = "استاد سالاری تبریز",
            bio = "تولیدکننده و صادرکننده قالی‌های تمام ابریشم و کرک تبریز طرح سالاری در سرای مظفریه",
            isSeller = true,
            isDemo = true
        ),
        UserEntity(
            id = "seller-seirafian",
            displayName = "حجره صیرفیان اصفهان",
            bio = "بافت قالی‌های اصیل شاه‌عباسی و لچک‌ترنج با چله ابریشم در میدان نقش‌جهان اصفهان",
            isSeller = true,
            isDemo = true
        ),
        UserEntity(
            id = "seller-erami",
            displayName = "کارگاه برادران ارمی قم",
            bio = "پیشکسوت بافت ابریشم خالص با تراکم بالای ۹۰۰ هزار گره در مترمربع",
            isSeller = true,
            isDemo = true
        ),
        UserEntity(
            id = "seller-qashqai",
            displayName = "تعاونی بافندگان ایل قشقایی",
            bio = "احیاگر دست‌بافته‌های ذهنی‌باف، گبه‌های طبیعی و رنگرزی سنتی گیاهی عشایر فارس",
            isSeller = true,
            isDemo = true
        )
    )

    val indicators = listOf(
        InspectionIndicatorEntity(
            id = "ind-fringe",
            titleFa = "۱. سلامت ریشه‌ها و چله سر و ته",
            titleEn = "Fringe & Warp Health",
            bodyFa = "ریشه‌ها را کشیده و بپیچانید تا از عدم خشکی، پوسیدگی در اثر رطوبت شست‌وشو یا بریدگی مطمئن شوید.",
            bodyEn = "Inspect fringes for tensile strength, dryness or damp rot.",
            displayOrder = 1
        ),
        InspectionIndicatorEntity(
            id = "ind-kilim",
            titleFa = "۲. یکنواختی گلیم‌بافی لبه‌ها",
            titleEn = "Kilim Edge Weave",
            bodyFa = "گلیم‌بافی سر و ته فرش مانع شکافته‌شدن ریشه‌هاست؛ باید سالم و بدون پارگی و سوراخ باشد.",
            bodyEn = "Check protective kilim border weave at both ends.",
            displayOrder = 2
        ),
        InspectionIndicatorEntity(
            id = "ind-moth",
            titleFa = "۳. بررسی آثار بیدخوردگی پنهان",
            titleEn = "Moth Damage Detection",
            bodyFa = "پشت فرش را زیر نور مایل بررسی کنید؛ خالی‌شدن پرزها در اثر لارو بید باعث سستی ساختار گره می‌شود.",
            bodyEn = "Examine back under angled light for wool fiber decay caused by moths.",
            displayOrder = 3
        ),
        InspectionIndicatorEntity(
            id = "ind-repair",
            titleFa = "۴. شناسایی رفوکاری و ترمیم‌های پنهان",
            titleEn = "Restoration & Reweaving",
            bodyFa = "لمس پشت فرش با انگشتان؛ قسمت‌های رفو شده زبرتر، سفت‌تر یا با رنگ نخ‌های ناهمگون هستند.",
            bodyEn = "Detect rewoven areas, patch stiffness or mismatched weft threads.",
            displayOrder = 4
        ),
        InspectionIndicatorEntity(
            id = "ind-frame",
            titleFa = "۵. کجی قاب و عدم تقارن حاشیه‌ها",
            titleEn = "Frame & Border Symmetry",
            bodyFa = "پهنای حاشیه را در ۴ طرف اندازه بگیرید؛ اختلاف بیش از ۲ سانتی‌متر نشانگر نقص دارکشی است.",
            bodyEn = "Measure outer border width along all 4 edges to detect frame deviation.",
            displayOrder = 5
        ),
        InspectionIndicatorEntity(
            id = "ind-touranj",
            titleFa = "۶. هم‌محوری ترنج میانی",
            titleEn = "Medallion Centering",
            bodyFa = "ترنج مرکزی باید دقیقاً در تقاطع قطرهای هندسی فرش قرار گرفته باشد و به یک طرف متمایل نباشد.",
            bodyEn = "Verify central medallion aligns strictly with geometric center.",
            displayOrder = 6
        ),
        InspectionIndicatorEntity(
            id = "ind-straight",
            titleFa = "۷. مستطیل بودن کامل و عدم سره‌گی",
            titleEn = "Rectangular Form & No Skew",
            bodyFa = "طول دو لبه راست و چپ و عرض دو لبه بالا و پایین باید هم‌اندازه باشند و فرش لوزی نشده باشد.",
            bodyEn = "Ensure diagonal measurements match and carpet lies completely flat.",
            displayOrder = 7
        ),
        InspectionIndicatorEntity(
            id = "ind-pattern",
            titleFa = "۸. گل شکسته و تغییر فرم نقشه",
            titleEn = "Broken Motif Variance",
            bodyFa = "بررسی تقارن اسلیمی‌ها و گل‌ها در لچک‌ها؛ افتادن موتیف در حین بافت به ارزش هنری اثر آسیب می‌زند.",
            bodyEn = "Check symmetry of curvilinear patterns and ensure no missing motifs.",
            displayOrder = 8
        ),
        InspectionIndicatorEntity(
            id = "ind-dye",
            titleFa = "۹. رنگ طبیعی گیاهی در برابر شیمیایی",
            titleEn = "Natural Botanical Dye",
            bodyFa = "استفاده از روناس، پوست گردو و نیل؛ مالیدن پارچه سفید مرطوب روی پرز نباید باعث پس‌دادن رنگ شود.",
            bodyEn = "Dye rub test with damp cloth to verify plant-based pigment stability.",
            displayOrder = 9
        ),
        InspectionIndicatorEntity(
            id = "ind-wool",
            titleFa = "۱۰. پشم مرغوب دست‌ریس بهاره",
            titleEn = "Live Spring Handspun Wool",
            bodyFa = "پشم بهاره چربی طبیعی (لانولین) دارد و در لمس نرم و لغزنده است، نه خشک و کدر مثل پشم دباغی.",
            bodyEn = "Live sheared wool retains lanolin oil and resilience vs dead acid wool.",
            displayOrder = 10
        ),
        InspectionIndicatorEntity(
            id = "ind-silk",
            titleFa = "۱۱. ابریشم خالص طبیعی در برابر ویسکوز",
            titleEn = "Pure Mulberry Silk vs Viscose",
            bodyFa = "ابریشم طبیعی زیر نور جلای متموج دارد، با دست مالش داده شود گرم می‌شود و پرز نمی‌دهد.",
            bodyEn = "Test pure filament silk luster and heat generation upon palm rubbing.",
            displayOrder = 11
        ),
        InspectionIndicatorEntity(
            id = "ind-density",
            titleFa = "۱۲. تراکم واقعی بافت (رج‌شمار در ۷ سانت)",
            titleEn = "Actual Knot Density Count",
            bodyFa = "با خط‌کش در ۷ سانتی‌متر پشت فرش گره‌ها را بشمارید تا تراکم اعلامی اثبات گردد.",
            bodyEn = "Count physical knots in 7 cm grid on carpet reverse.",
            displayOrder = 12
        ),
        InspectionIndicatorEntity(
            id = "ind-wear",
            titleFa = "۱۳. سایش یکنواخت پرز و خواب قالی",
            titleEn = "Uniform Pile Wear",
            bodyFa = "دست کشیدن در جهت خواب پرز؛ نباید نقاطی کوبیده، طاس یا نازک‌تر از بقیه سطح فرش باشند.",
            bodyEn = "Check for localized high-traffic balding or uneven pile thinning.",
            displayOrder = 13
        ),
        InspectionIndicatorEntity(
            id = "ind-sun",
            titleFa = "۱۴. بورشدگی و آفتاب‌خوردگی موضعی",
            titleEn = "Sunlight Bleaching / Fade",
            bodyFa = "مقایسه رنگ نقاط زیر مبل یا ریشه با مرکز؛ نباید تفاوت فاحش ناشی از تابش نور خورشید وجود داشته باشد.",
            bodyEn = "Compare unexposed edge tones with surface to detect UV fading.",
            displayOrder = 14
        ),
        InspectionIndicatorEntity(
            id = "ind-color-run",
            titleFa = "۱۵. رنگ‌دویی و پخش‌شدگی رنگ",
            titleEn = "Color Bleed / Run",
            bodyFa = "نفوذ رنگ لاکی یا سرمه‌ای به پرزهای کرم و سفید در اثر شستشوی نادرست با آب گرم یا مواد شوینده اسیدی.",
            bodyEn = "Inspect pale areas for color bleeding from adjacent deep hues.",
            displayOrder = 15
        ),
        InspectionIndicatorEntity(
            id = "ind-touchup",
            titleFa = "۱۶. رنگ‌ریزی و ماژیک‌کشی ترمیمی",
            titleEn = "Ink Touch-Up / Paint Coverup",
            bodyFa = "استفاده متقلبانه از جوهر، واکس یا ماژیک برای پوشاندن لکه‌های ساییدگی و سفیدک‌زدگی پشت و روی قالی.",
            bodyEn = "Detect temporary ink painting used to conceal threadbare areas.",
            displayOrder = 16
        )
    )

    val rugs = listOf(
        // 6 Handmade
        RugEntity(
            id = "rug-1",
            sellerId = "seller-salari",
            title = "شاهکار ابریشم تبریز طرح سالاری (۷۰ رج اصل)",
            description = "بافته‌شده با چله ابریشم طبیعی و رنگرزی گیاهی خمره‌ای؛ دارای ۷۰ رج متراکم در ۷ سانتی‌متر و ترنج زرین شمسه.",
            category = "HANDMADE_RUG",
            region = "تبریز",
            era = "معاصر (۱۳۹۸)",
            price = 850_000_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-2",
            sellerId = "seller-seirafian",
            title = "قالیچه نفیس اصفهان طرح لچک‌ترنج شاه‌عباسی",
            description = "اثر اصیل کارگاه صیرفیان اصفهان با چله ابریشم و پرز کرک مرینوس اعلا. الهام از نقوش کاشی‌کاری‌های مسجد شیخ لطف‌الله.",
            category = "HANDMADE_RUG",
            region = "اصفهان",
            era = "معاصر (۱۴۰۰)",
            price = 620_000_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-3",
            sellerId = "seller-erami",
            title = "قالیچه تمام ابریشم قم طرح شکارگاه زرین",
            description = "۱۰۰٪ ابریشم خالص قم، ریزبافت ۸۰ رج با پرز بسیار لطیف و انعکاس نور هفت‌رنگ زیر زوایای مختلف.",
            category = "HANDMADE_RUG",
            region = "قم",
            era = "معاصر (۱۴۰۱)",
            price = 980_000_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-4",
            sellerId = "seller-qashqai",
            title = "قالیچه عشایری قشقایی ذهنی‌باف (پشم دست‌ریس گیاهی)",
            description = "بافت خودجوش عشایر دامنه‌های زاگرس بدون نقشه؛ رنگرزی سنتی با روناس و پوست گردو روی دار افقی.",
            category = "HANDMADE_RUG",
            region = "شیراز",
            era = "معاصر (۱۳۹۵)",
            price = 145_000_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-5",
            sellerId = "seller-seirafian",
            title = "قالی نایین ۶لا گل‌ابریشم طرح حبیبیان",
            description = "ترکیب رنگ کویری آرامشبخش عاجی و سرمه‌ای با رج‌شمار ۶۵ و شیرازه دوخت فابریک کارگاه نایین.",
            category = "HANDMADE_RUG",
            region = "نایین",
            era = "معاصر (۱۳۹۷)",
            price = 390_000_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-6",
            sellerId = "seller-salari",
            title = "قالی هریس آذربایجان طرح تاجمیر (ضخیم و بادوام)",
            description = "معروف به استحکام و رنگرزی روناسی غنی. نقشه هندسی شکسته با پشم بومی آذربایجان و دوام چند نسل.",
            category = "HANDMADE_RUG",
            region = "هریس",
            era = "پهلوی دوم",
            price = 280_000_000L,
            isDemo = true
        ),

        // 3 Machine
        RugEntity(
            id = "rug-7",
            sellerId = "seller-salari",
            title = "فرش ماشینی ۱۵۰۰ شانه تراکم ۴۵۰۰ کاشان طرح افشان",
            description = "بافته‌شده با الیاف اکریلیک هیت‌ست آلمان؛ بدون پرزدهی با پالت ۱۰ رنگ کلاسیک و وضوح مشابه فرش دستباف.",
            category = "MACHINE_RUG",
            region = "کاشان",
            era = "تولید جدید (۱۴۰۲)",
            price = 28_500_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-8",
            sellerId = "seller-seirafian",
            title = "فرش ماشینی ۱۲۰۰ شانه گل‌برجسته مشهد طرح خشتی",
            description = "بافت برجسته با نخ نمره ۴۲ ترک، زمینه فیلی روشن و تار و پود پنبه خالص برای زیرانداز پذیرایی.",
            category = "MACHINE_RUG",
            region = "مشهد",
            era = "تولید جدید (۱۴۰۲)",
            price = 22_000_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-9",
            sellerId = "seller-qashqai",
            title = "گبه ماشینی طرح سنتی ایلاتی ۷۰۰ شانه",
            description = "الهام‌گرفته از طرح‌های خشتی و حیوانی بختیاری و قشقایی با تراکم ۲۵۵۰؛ مناسب نشیمن و دکوراسیون سنتی مدرن.",
            category = "MACHINE_RUG",
            region = "یزد",
            era = "تولید جدید (۱۴۰۳)",
            price = 14_000_000L,
            isDemo = true
        ),

        // 3 Services
        RugEntity(
            id = "rug-10",
            sellerId = "seller-salari",
            title = "خدمات قالیشویی سنتی و غبارگیری تخصصی قالی دستباف",
            description = "شست‌وشوی اصولی با آب چاه شیرین و مواد شوینده خنثی؛ بدون کاربرد کلر، با آبگیری سنتی در هوای آزاد بدون شکستگی چله.",
            category = "SERVICES",
            region = "تهران و تبریز",
            era = "ارائه خدمات مستمر",
            price = 1_800_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-11",
            sellerId = "seller-seirafian",
            title = "خدمات رفوگری خبره، مرمت بیدخوردگی و چرم‌دوزی لبه‌ها",
            description = "توسط استادکاران قدیمی بازار اصفهان؛ بازسازی ریشه‌ها، پودکشی تاروپود و دوخت چرم طبیعی پشت شیرازه برای جلوگیری از لول‌شدن.",
            category = "SERVICES",
            region = "اصفهان و کاشان",
            era = "ارائه خدمات مستمر",
            price = 2_500_000L,
            isDemo = true
        ),
        RugEntity(
            id = "rug-12",
            sellerId = "seller-erami",
            title = "اعزام کارشناس خبره جهت بازرسی حضوری و صدور شناسنامه",
            description = "اعزام استادکار جهت ارزیابی حضوری سلامت بافت، اندازه‌گیری با متر و ذره‌بین، سنجش رج و ثبت چک‌لیست ۱۶ شاخصه.",
            category = "SERVICES",
            region = "سراسر کشور",
            era = "ارائه خدمات مستمر",
            price = 3_000_000L,
            isDemo = true
        )
    )

    val sampleComments = listOf(
        CommentEntity(
            id = "c-1",
            rugId = "rug-1",
            authorId = "user-self",
            text = "رنگرزی روناس این تخته بسیار چشم‌نواز است. رج‌شمار را شخصاً بررسی کردم و کاملاً با بافت تبریز مطابقت دارد.",
            isDemo = true
        ),
        CommentEntity(
            id = "c-2",
            rugId = "rug-2",
            authorId = "user-self",
            text = "کارهای صیرفیان همواره از نظر تقارن ترنج و سلامت دارکشی نمونه هستند.",
            isDemo = true
        )
    )

    val sampleLikes = listOf(
        LikeEntity(userId = "user-self", rugId = "rug-1"),
        LikeEntity(userId = "user-self", rugId = "rug-3")
    )

    val sampleXpEvents = listOf(
        XpEventEntity(
            id = "xp-seed-1",
            userId = "user-self",
            type = "POST_CREATED",
            basePoints = 10,
            multiplier = 1
        ),
        XpEventEntity(
            id = "xp-seed-2",
            userId = "user-self",
            type = "LIKE_RECEIVED",
            basePoints = 2,
            multiplier = 1
        )
    )
}
