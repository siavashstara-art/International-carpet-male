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
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.CarpetItem
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.FarshBorder
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldContainer
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshMadderRuby
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshParchment
import com.example.ui.theme.FarshParchmentDark
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshVelvetSurface
import com.example.ui.theme.FarshWalnut
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun CertificateDialog(
    carpet: CarpetItem,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, FarshGold, RoundedCornerShape(16.dp))
                .background(FarshParchment)
                .testTag("certificate_dialog"),
            color = FarshParchment
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with Close Button
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
                            contentDescription = "بستن شناسنامه",
                            tint = FarshWalnut
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "شناسنامه رسمی اصالت قالی دستباف",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshWalnut
                        )
                        Text(
                            text = "CERTIFICATE OF AUTHENTICITY",
                            fontFamily = CinzelFontFamily,
                            fontSize = 9.sp,
                            letterSpacing = 1.sp,
                            color = FarshGoldWarm,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Gold Seal Emblem
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Certificate Serial Number Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(FarshParchmentDark)
                        .border(1.dp, FarshGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "شماره ثبت ملی اثر:",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            color = FarshWalnut
                        )
                        Text(
                            text = carpet.certificateId,
                            fontFamily = CinzelFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshMadderRuby
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title of carpet
                Text(
                    text = carpet.titleFa,
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshObsidian,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = FarshGold.copy(alpha = 0.3f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                // Technical Attribute Matrix
                CertificateSpecRow("خاستگاه و مکتب بافت:", "${carpet.originCityFa} (دارنده نشان جهانی)")
                CertificateSpecRow("طرح و نقشه اصیل:", carpet.stylePatternFa)
                CertificateSpecRow("ابعاد دقیق:", carpet.dimensions)
                CertificateSpecRow("تراکم رج‌شمار:", "${carpet.knotDensityRaj} رج در هر ۷ سانتی‌متر")
                CertificateSpecRow("سبک گره‌زنی:", carpet.knotTypeFa)
                CertificateSpecRow("جنس چله و تار:", carpet.materialFa)
                CertificateSpecRow("نوع رنگرزی:", carpet.dyeingTypeFa)
                CertificateSpecRow("پودگذاری:", carpet.weftCountFa)
                CertificateSpecRow("امضای استادکار / کارگاه:", carpet.weaverMaster)

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = FarshGold.copy(alpha = 0.3f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                // Official Guarantee statement
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.7f))
                        .border(0.5.dp, FarshGold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "این سند اصالت تایید می‌کند که کلیه متریال، ابریشم طبیعی و رنگرزی این قالیچه تحت نظارت کارشناسان خبره بررسی شده و بافت آن صددرصد دستباف و بدون تقلب صنعتی می‌باشد.",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            lineHeight = 18.sp,
                            color = FarshWalnut
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshWalnut),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "بستن و بازگشت به اثر",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshParchment,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun CertificateSpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = VazirmatnFontFamily,
            fontSize = 11.sp,
            color = FarshWalnut.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            fontFamily = VazirmatnFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = FarshObsidian,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}
