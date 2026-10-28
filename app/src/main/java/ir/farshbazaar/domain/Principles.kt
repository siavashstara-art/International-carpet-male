package ir.farshbazaar.domain

data class Principle(
    val id: String,
    val titleFa: String,
    val titleEn: String,
    val bodyFa: String,
    val bodyEn: String = ""
)

typealias PrincipleItem = Principle

object Principles {
    const val FOUNDER_QUOTE_FA = "«ما فرش را با چشم و دست می‌شناسیم، نه با عکس. و اگر روزی نتوانستیم حضوری ببینیم، صادقانه می‌گوییم که ندیدیم.»"
    const val MOTHER_PRINCIPLE_FA = FOUNDER_QUOTE_FA

    val ALL: List<Principle> = listOf(
        Principle(
            id = "p1",
            titleFa = "اظهار نظر مشروط بر تصویر (نه گواهی اصالت)",
            titleEn = "Conditional Opinion on Image (Not Certificate of Authenticity)",
            bodyFa = "تصویر صرفاً سرنخی از طرح و ترکیب رنگ است. هیچ تصویری توان نمایش عمق پرز، سلامت چله یا تراکم واقعی گره را ندارد؛ بنابراین هرگز نمی‌توان با عکس گواهی قطعی صادر کرد."
        ),
        Principle(
            id = "p2",
            titleFa = "اقرار به اختلاف نظر کارشناسان",
            bodyFa = "فرش دستباف هنر دست انسان است، نه محصول کارخانه. کارشناسان باسابقه ممکن است در ارزیابی مکتب، ریزبافت یا طراح اختلاف دیدگاه داشته باشند و ما این تنوع رأی را پنهان نمی‌کنیم.",
            titleEn = "Acknowledging Diverse Expert Perspectives"
        ),
        Principle(
            id = "p3",
            titleFa = "قدمت‌سنجی نیازمند کربن ۱۴",
            bodyFa = "تعیین دقیق سن فرش‌های تاریخی نیازمند آزمون‌های پیشرفته آزمایشگاهی و ایزوتوپ کربن است. ادعاهای سلیقه‌ای و بدون مستندات آزمایشگاهی درباره قرن بافت فاقد اعتبار است.",
            titleEn = "Dating Verification Requires Scientific Testing"
        ),
        Principle(
            id = "p4",
            titleFa = "آنچه فقط با لمس و چشم پشت فرش دیده می‌شود",
            bodyFa = "ریشه، سلامت گلیم‌باف سر و ته، آثار کهنه بیدخوردگی، رفوکاری‌های مویرگی و خشکی یا چرب بودن پشم تنها با لمس انگشتان و مشاهده بافت زیرین پشت فرش آشکار می‌شود.",
            titleEn = "Sensory Inspection of Back, Weft & Restoration"
        ),
        Principle(
            id = "p5",
            titleFa = "آنچه با متر و ریسمان سنجیده می‌شود",
            bodyFa = "کجی قاب، سره‌گی، عدم هم‌محوری ترنج نسبت به حاشیه و اختلاف ابعاد در سر و ته قالی نیازمند اندازه‌گیری با متر فلزی و ریسمان است.",
            titleEn = "Geometric Precision via Measuring Tape"
        ),
        Principle(
            id = "p6",
            titleFa = "گل شکسته و تغییر هویت فرش",
            bodyFa = "افتادن نقشه، شکستگی خطوط اسلیمی یا تغییر ناگهانی رنگرز در میانه بافت (رگه‌شدگی) هویت بصری فرش را دگرگون می‌سازد و باید با شفافیت ذکر گردد.",
            titleEn = "Broken Pattern & Motif Variance"
        ),
        Principle(
            id = "p7",
            titleFa = "رنگ طبیعی در برابر مصنوعی",
            bodyFa = "رنگ‌های سنتی گیاهی (روناس، قره‌قروت، نیل، اسپرک و پوست گردو) با مرور زمان جلا می‌گیرند، در حالی که رنگ‌های تند شیمیایی در برابر نور آفتاب می‌سوزند و مات می‌شوند.",
            titleEn = "Natural Botanical Dyes vs Synthetic Pigments"
        ),
        Principle(
            id = "p8",
            titleFa = "پشم مرغوب در برابر نامرغوب",
            bodyFa = "پشم دست‌ریس بهاره چربی طبیعی داشته و لطیف و بادوام است؛ در مقابل پشم دباغی‌شده صنعتی کدر، شکننده و بدون انعطاف است و زود پرز می‌دهد.",
            titleEn = "Live Spring Wool vs Tanned Waste Wool"
        ),
        Principle(
            id = "p9",
            titleFa = "ابریشم طبیعی در برابر مصنوعی",
            bodyFa = "ابریشم اصل از پیله کرم ابریشم استخراج شده، هنگام مالش گرما تولید می‌کند و نسوز است؛ در حالی که ویسکوز و ابریشم‌های مصنوعی پایه نفتی داشته و مقاومت ناچیزی دارند.",
            titleEn = "Pure Mulberry Silk vs Synthetic Rayon"
        ),
        Principle(
            id = "p10",
            titleFa = "تراکم بافت (پوک در برابر پر)",
            bodyFa = "تعداد گره‌های واقعی در مقیاس ۷ سانتی‌متر و استحکام کوبیدگی پودها، تفاوت میان بافت استوار و ماندگار با بافت شل و پوک را رقم می‌زند.",
            titleEn = "Knot Density & Structural Solidity"
        ),
        Principle(
            id = "p11",
            titleFa = "فهرست باز شاخص‌ها",
            bodyFa = "شاخص‌هایی نظیر بورشدگی، آفتاب‌خوردگی، رنگ‌دویی، و رنگ‌ریزی ترمیمی به صورت داده‌محور و پویا پیوسته به‌روزرسانی می‌شوند تا هیچ نقصی ناگفته نماند.",
            titleEn = "Open Data-Driven Indicator Registry"
        ),
        Principle(
            id = "p12",
            titleFa = "جنبهٔ آموزشی، نه فقط سلب مسئولیت",
            bodyFa = "هدف از بیان شاخص‌ها نه فقط سلب مسئولیت سامانه، بلکه توانمندسازی و ارتقای سواد فرشبازی خریداران در هنگام دادوستد است.",
            titleEn = "Education & Empowerment, Not Just Disclaimer"
        ),
        Principle(
            id = "p13",
            titleFa = "صداقت در لحن و نیت",
            bodyFa = "پرهیز از واژگان تبلیغاتی توخالی و بیان صادقانه تمام معایب، رفوها و تاریخچه نگهداری اثر، شرط نخست معامله در فرشبازار است.",
            titleEn = "Integrity in Tone & Sincerity of Intent"
        ),
        Principle(
            id = "p14",
            titleFa = "شناسنامه = اظهار نظر، نه تأیید ۱۰۰٪",
            bodyFa = "برگه شناسنامه نشانگر نظر کارشناسی مستند تا زمان ارزیابی است و به معنای ضمانت قطعی و لایتغیر بازار تلقی نمی‌شود.",
            titleEn = "Identity Sheet as Opinion, Not Absolute Guarantee"
        )
    )
}
