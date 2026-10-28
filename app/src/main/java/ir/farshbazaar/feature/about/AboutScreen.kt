package ir.farshbazaar.feature.about

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.core.ui.theme.*
import ir.farshbazaar.domain.Principles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "درباره و مرام‌نامهٔ فرشبازار",
                        color = FarshGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mother Principle / Founder Quote
            item {
                Spacer(Modifier.height(4.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshRed.copy(alpha = 0.2f)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshGold))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "نقل قول مادر و عهد بنیانگذار:",
                            color = FarshGoldWarm,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = Principles.MOTHER_PRINCIPLE_FA,
                            color = FarshGold,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            // Memorial Dedication to Haj Hossein Ali-Miri
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshSurfaceElevated),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshGoldDark))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(26.dp))
                                .background(FarshRed.copy(alpha = 0.3f))
                                .border(1.dp, FarshGold, RoundedCornerShape(26.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = FarshGold,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "به یاد و نام پدر",
                            color = FarshGoldWarm,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "مرحوم حاج حسین آقای علی‌میری",
                            color = FarshTextPrimary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "از تاجران خوش‌نام و پیشکسوت صنف فرش ایران و جهان",
                            color = FarshGold,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(14.dp))
                        HorizontalDivider(color = FarshBorderGold, thickness = 1.dp)
                        Spacer(Modifier.height(14.dp))
                        Text(
                            text = "این بستر به یاد و احترام مردی بنا می‌شود که نامش با هنر اصیل قالی و امانت‌داری در تجارت پیوند خورده است. با آرزوی آنکه فرشبازار پلی میان اصالت و شفافیت باشد و نام ایشان را در پاسداشت دسترنج بافنده ایرانی زنده نگه دارد.\n\nیادشان گرامی و نامشان ماندگار.",
                            color = FarshTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Board of Trustees Placeholder (جای‌خالی هیئت امنا)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshSurface),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = FarshGold)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "جای‌خالی هیئت امنا و کارشناسان عالی‌رتبه",
                                color = FarshTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "برای تضمین استقلال داوری و رعایت ۱۴ اصل اخلاقی، هیئت امنایی مستقل متشکل از پیشکسوتان شناخته‌شده بازار تبریز، اصفهان، کاشان و قم در حال گزینش و معرفی است تا نظارت عالیه بر گزارش‌های بازرسی را بر عهده گیرد.",
                            color = FarshTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 20.sp
                        )
                        Spacer(Modifier.height(12.dp))

                        // Placeholder slots
                        listOf(
                            "کرسی نمایندگی صنف و بازار تاریخی تبریز (در حال استعلام)",
                            "کرسی داوری علمی و هنری مکتب اصفهان و نایین (در حال استعلام)",
                            "کرسی نظارت بر استاندارد رنگرزی طبیعی و گیاهی (در حال استعلام)"
                        ).forEach { seat ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(FarshInk)
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.PersonOutline, contentDescription = null, tint = FarshTextSecondary, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(seat, color = FarshTextSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("بازگشت", color = FarshInk, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}
