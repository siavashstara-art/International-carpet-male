package ir.farshbazaar.feature.knotguide

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.core.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RugKnotGuideScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "راهنمای رجشمار قالی",
                        color = FarshGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "بازگشت",
                            tint = FarshGold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FarshVelvet
                )
            )
        },
        containerColor = FarshInk
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Top Warning Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = FarshGold.copy(alpha = 0.12f)),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshGold))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = null,
                        tint = FarshGold,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "هشدار اصالت و عدم امکان سنجش ماشینی",
                            color = FarshGoldWarm,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "هیچ دوربین یا نرم‌افزاری قادر به تشخیص معتبر رج‌شمار و گره از روی عکس نیست. زاویه نور، ادیت عکس و خطای فوکوس باعث ادعاهای کاذب می‌شود. سنجش واقعی تراکم، منحصراً با دست، خط‌کش و مشاهده مستقیم در پشت قالی انجام می‌شود.",
                            color = FarshTextPrimary,
                            fontSize = 12.sp,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // 2. What is Raj?
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = FarshSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "«رج» چیست؟ (مقیاس اصیل سنجش تراکم)",
                        color = FarshGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "در سنت کهن قالی‌بافی ایران (به‌ویژه مکتب تبریز)، واحد سنجش تراکم گره بر مبنای ۷ سانتی‌متر (معادل ۱ گره ذرعی) تعریف می‌شود.\n\nبه بیان ساده: «تعداد گره‌های بافته‌شده در طول یا عرض ۷ سانتی‌متر از پشت فرش را رج‌شمار می‌گویند.»\nبرای مثال، فرش ۵۰ رج یعنی در هر ۷ سانتی‌متر از پشت آن، دقیقاً ۵۰ گره بافته شده است.",
                        color = FarshTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            // 3. Step-by-Step Manual Counting Guide
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = FarshSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "آموزش گام‌به‌گام شمارش دستی گره‌ها",
                        color = FarshGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(12.dp))

                    StepItem(
                        number = "۱",
                        title = "پشت و رو کردن فرش",
                        desc = "فرش را به پشت برگردانید و در محیطی با نور مناسب (ترجیحاً نور طبیعی یا چراغ قوه سفید مایل) بر روی زمین صاف بگسترانید."
                    )
                    StepItem(
                        number = "۲",
                        title = "انطباق خط‌کش ۷ سانتی‌متری",
                        desc = "یک خط‌کش فلزی دقیق بردارید و صفر آن را روی لبه یکی از ردیف‌های برجسته گره بگذارید تا نشانهٔ ۷ سانتی‌متر."
                    )
                    StepItem(
                        number = "۳",
                        title = "شمارش دانه‌های گره با ذره‌بین",
                        desc = "با نوک سوزن یا ذره‌بین، دانه‌های گره را در امتداد این ۷ سانتی‌متر یکی‌یکی بشمارید. هر گره به صورت یک دانه دو لنگه یا جفت در پشت فرش دیده می‌شود."
                    )
                    StepItem(
                        number = "۴",
                        title = "تکرار در ۳ نقطه مختلف",
                        desc = "شمارش را در حاشیه بالا، مرکز ترنج و لبه پایین تکرار کنید. در بافت اصیل و دستباف استادکار، تراکم در سراسر قالی یکنواخت است."
                    )
                }
            }

            // 4. Raj Density Classification Table
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = FarshSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "جدول دسته‌بندی رج‌شمار (راهنمای خریدار)",
                        color = FarshGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(10.dp))

                    RajTableRow("زیر ۳۰ رج", "درشت‌بافت (گبه عشایری، بافت لری و پشم ضخیم روستایی)")
                    RajTableRow("۳۵ تا ۴۵ رج", "بافت متوسط و مستحکم شهری (هریس، بیجار، کاشان و سنندج)")
                    RajTableRow("۵۰ تا ۶۰ رج", "ریزبافت و گل‌ابریشم اعلا (نایین ۶لا، اصفهان، تبریز)")
                    RajTableRow("۶۵ تا ۸۰ رج", "فوق‌ریز و تمام ابریشم (شاهکارهای ارمی قم، صیرفیان، سالاری ۷۰ رج)")
                    RajTableRow("بالای ۸۰ رج", "آثار موزه‌ای و کلکسیونی استثنایی (ابریشم ناب و تراکم میلیونی)")
                }
            }

            // 5. Knot Types: Turkish vs Persian
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = FarshSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Balance, contentDescription = null, tint = FarshGold)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "شناخت آناتومی گره: ترکی در برابر فارسی",
                            color = FarshGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "• گره ترکی (متقارن): نخ پرز به دور هر دو تار چله پیچیده و قفل دوبل می‌شود. با قلاب بافته شده و بیشترین دوام و تقارن هندسی را دارد (تبریز، هریس، قشقایی، بیجار).\n\n• گره فارسی (نامتقارن): نخ پرز با سرانگشتان دست تنها به دور یک تار پیچیده و از پشت تار دیگر رد می‌شود. این شیوه برای اجرای خطوط منحنی، نقوش ختایی و چهره‌بافی به کار می‌رود (اصفهان، نایین، کاشان، کرمان، مشهد).",
                        color = FarshTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 22.sp
                    )
                }
            }

            // 6. Required Tools
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = FarshSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Handyman, contentDescription = null, tint = FarshGold)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "ابزار لازم برای آزمون دستی",
                            color = FarshGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    listOf(
                        "خط‌کش فلزی با درجات میلی‌متری دقیق",
                        "لوپ یا ذره‌بین مخصوص شمارش نساجی (بزرگ‌نمایی ۱۰ برابر)",
                        "نور چراغ قوه موضعی مایل (زاویه ۴۵ درجه نسبت به بافت)",
                        "سوزن نوک‌گرد برای شمارش بدون آسیب به تار و پود"
                    ).forEach { tool ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = FarshGold, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(tool, color = FarshTextSecondary, fontSize = 12.sp)
                        }
                    }
                }
            }

            // Back button
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("متوجه شدم؛ بازگشت", color = FarshInk, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StepItem(number: String, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(FarshGold.copy(alpha = 0.18f))
                .border(1.dp, FarshGold, RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(number, color = FarshGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = FarshTextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Spacer(Modifier.height(3.dp))
            Text(desc, color = FarshTextSecondary, fontSize = 11.sp, lineHeight = 19.sp)
        }
    }
}

@Composable
private fun RajTableRow(rajRange: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(FarshInk)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            rajRange,
            color = FarshGold,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.width(90.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            description,
            color = FarshTextSecondary,
            fontSize = 11.sp,
            modifier = Modifier.weight(1f)
        )
    }
}
