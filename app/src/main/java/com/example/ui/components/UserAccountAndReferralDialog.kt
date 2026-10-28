package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SimCard
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.StarUpgradeCalculator
import com.example.model.UserAccount
import com.example.model.VerificationLevel
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.FarshBorder
import com.example.ui.theme.FarshCardElevated
import com.example.ui.theme.FarshCardSurface
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldContainer
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshMadderRuby
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshVelvetSurface
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun UserAccountAndReferralDialog(
    user: UserAccount,
    onDismiss: () -> Unit,
    onVerifyShahkarClick: () -> Unit
) {
    val context = LocalContext.current
    val starBreakdown = remember(user.monthlyAttendanceCount) {
        StarUpgradeCalculator.calculateTiers(user.monthlyAttendanceCount * 7) // Sample attendance calculation
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(20.dp))
                .border(1.5.dp, FarshGold, RoundedCornerShape(20.dp))
                .background(FarshObsidian)
                .testTag("user_account_referral_dialog"),
            color = FarshObsidian
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Header
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

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "شناسنامه کاربری و سامانه معرفان",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                        Text(
                            text = "ACCOUNT IDENTITY & REFERRAL SYSTEM",
                            fontFamily = CinzelFontFamily,
                            fontSize = 8.sp,
                            letterSpacing = 1.sp,
                            color = FarshTextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // User Identity Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, FarshBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = user.fullName,
                                    fontFamily = VazirmatnFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = FarshTextPrimary
                                )
                                Text(
                                    text = user.maskedMobileNumber,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp,
                                    color = FarshTextSecondary
                                )
                            }

                            // Verification Level Badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(user.verificationLevel.badgeColorHex).copy(alpha = 0.2f))
                                    .border(1.dp, Color(user.verificationLevel.badgeColorHex), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = Color(user.verificationLevel.badgeColorHex),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = user.verificationLevel.titleFa,
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(user.verificationLevel.badgeColorHex)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = FarshBorder, thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Shahkar SIM Ownership Status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.SimCard,
                                    contentDescription = null,
                                    tint = FarshGoldWarm,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "استعلام مالکیت سیم‌کارت (سامانه شاهکار):",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp,
                                    color = FarshTextSecondary
                                )
                            }

                            Text(
                                text = if (user.isSimOwnershipVerified) "تأیید شده به نام شخص" else "تأیید نشده (نیاز به استعلام)",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (user.isSimOwnershipVerified) Color(0xFF4CAF50) else FarshMadderRuby
                            )
                        }

                        if (!user.isSimOwnershipVerified) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Button(
                                onClick = onVerifyShahkarClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = FarshCardElevated),
                                border = androidx.compose.foundation.BorderStroke(0.5.dp, FarshGold),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text("ثبت درخواست استعلام مالکیت سیم‌کارت", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = FarshGoldWarm)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Unique Referral Code Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, FarshGold.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = FarshVelvetSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "کد معرف یکتای شما (غیرقابل جعل):",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Code Container with Copy
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(FarshObsidian)
                                .border(1.dp, FarshGold, RoundedCornerShape(8.dp))
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = user.referralCode,
                                fontFamily = CinzelFontFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = FarshTextPrimary,
                                letterSpacing = 2.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            IconButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("کد معرف فرشبازار", user.referralCode))
                                    Toast.makeText(context, "کد معرف کپی شد: ${user.referralCode}", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "کپی کد معرف",
                                    tint = FarshGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "با دعوت از دیگر فعالان صنف فرش، امتیاز XP و ستاره‌های لیگ معرفان دریافت نمایید.",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            color = FarshTextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Gamification: Star Tiers & Surplus Breakdown
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, FarshBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = FarshGoldWarm,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "سطح ستاره‌های وفاداری و حضور ماهانه:",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = FarshGoldWarm
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "• هر ماه حضور واجد شرایط = ۱ ستاره آبی\n• هر ۷ ستاره آبی = ۱ ستاره نقره‌ای\n• هر ۷ ستاره نقره‌ای = ۱ ستاره طلایی\n• هر ۷ ستاره طلایی = ۱ ستاره الماس‌نشان\n• هر ۷ ستاره الماس‌نشان = ۱ ستاره اسطوره برنامه",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            lineHeight = 17.sp,
                            color = FarshTextSecondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = FarshBorder, thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Current Star Status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            StarPill("اسطوره", starBreakdown.mythicStars.toString(), Color(0xFFFFD700))
                            StarPill("الماس", starBreakdown.diamondStars.toString(), Color(0xFF00E5FF))
                            StarPill("طلا", starBreakdown.goldStars.toString(), FarshGold)
                            StarPill("نقره", starBreakdown.silverStars.toString(), Color(0xFFC0C0C0))
                            StarPill("آبی (مازاد)", starBreakdown.blueStars.toString(), Color(0xFF2979FF))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Virtual City of Tavana Allocation
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, FarshBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = FarshVelvetSurface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Landscape,
                                contentDescription = null,
                                tint = FarshGoldWarm,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "سهمیه حق تخصیص زمین در شهر مجازی توانا:",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = FarshGoldWarm
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "سهمیه مصوب برای رتبه‌های اول تا دهم لیگ معرفان:\nرتبه ۱: ۵۰۰ مترمربع • رتبه ۲: ۴۵۰ متر • رتبه ۳: ۴۰۰ متر • رتبه ۴: ۳۸۰ متر • رتبه ۵: ۳۵۰ متر • رتبه ۶: ۳۳۰ متر • رتبه ۷: ۳۰۰ متر • رتبه ۸: ۲۸۰ متر • رتبه ۹: ۲۶۰ متر • رتبه ۱۰: ۲۴۰ مترمربع.",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            lineHeight = 16.sp,
                            color = FarshTextSecondary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "وضعیت: مدل و قرارداد تخصیص در سامانه ثبت شده است. اعطای قطعی سند پس از اتصال رسمی زیرساخت حقوقی شهر مجازی توانا فعال می‌گردد.",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 9.sp,
                            color = FarshGold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "بستن شناسنامه",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshObsidian,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StarPill(title: String, count: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f))
                .border(1.dp, color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontFamily = VazirmatnFontFamily,
            fontSize = 9.sp,
            color = FarshTextSecondary
        )
    }
}
