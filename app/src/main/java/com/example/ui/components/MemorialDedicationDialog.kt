package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.FarshCardSurface
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldContainer
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshMadderRuby
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshParchment
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshVelvetSurface
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun MemorialDedicationDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, FarshGold, RoundedCornerShape(20.dp))
                .background(FarshObsidian)
                .testTag("memorial_dedication_dialog"),
            color = FarshObsidian
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top close & crest
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "بستن",
                            tint = FarshTextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Heading
                Text(
                    text = "به یاد و نام پدر",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = FarshGoldWarm,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "مرحوم حاج حسین آقای علیمیری",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "از تاجران خوشنام فرش ایران و جهان",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FarshGold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Elegant Gold Filigree Divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(1.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    FarshGold,
                                    Color.Transparent
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dedication Plaque Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(FarshVelvetSurface)
                        .border(1.dp, FarshGold.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        .padding(18.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "« این مجموعه به یاد و احترام مردی بنا می‌شود که نامش با هنر اصیل فرش و دنیای تجارت پیوند خورده است؛ با آرزوی آنکه بازار فرش ایران و جهان، پلی میان اصالت و نوآوری، فرهنگ و تجارت باشد و یاد ایشان را در مسیر پاسداشت هنر قالی ایرانی زنده نگه دارد. »",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 13.sp,
                            lineHeight = 24.sp,
                            color = FarshParchment,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "یادشان گرامی و نامشان ماندگار",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Historical Heritage Note
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(FarshCardSurface)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = FarshMadderRuby,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "فرشبازار، تداوم‌بخش راه بزرگان و پیشکسوتان تجارت قالی دستباف در پهنهٔ گیتی.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "ورود به فرشبازار با ادای احترام",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshObsidian,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
