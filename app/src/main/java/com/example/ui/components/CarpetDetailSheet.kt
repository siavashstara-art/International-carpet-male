package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CarpetItem
import com.example.model.Currency
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
fun CarpetDetailSheet(
    carpet: CarpetItem,
    currentCurrency: Currency,
    formatPrice: (CarpetItem, Currency) -> String,
    onBack: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onOpenCertificate: () -> Unit,
    onOpenMacroTexture: () -> Unit,
    onOpenRoomPreview: () -> Unit,
    onOpenDirectChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("carpet_detail_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 90.dp)
        ) {
            // Hero Image Area with Full Bleed & Controls
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.15f)
            ) {
                Image(
                    painter = painterResource(id = carpet.imageResId),
                    contentDescription = carpet.titleFa,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Atmospheric Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    FarshObsidian.copy(alpha = 0.7f),
                                    Color.Transparent,
                                    FarshObsidian
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                // Top Floating Action Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(FarshObsidian.copy(alpha = 0.75f))
                            .border(1.dp, FarshBorder, CircleShape)
                            .testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "بازگشت",
                            tint = FarshTextPrimary
                        )
                    }

                    Row {
                        IconButton(
                            onClick = { onToggleFavorite(carpet.id) },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(FarshObsidian.copy(alpha = 0.75f))
                                .border(1.dp, FarshBorder, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (carpet.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "نشان‌کردن",
                                tint = if (carpet.isFavorite) FarshMadderRuby else FarshTextPrimary
                            )
                        }
                    }
                }

                // Badges at bottom of image
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${carpet.knotDensityRaj} رج دستباف",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(FarshObsidian.copy(alpha = 0.85f))
                            .border(0.5.dp, FarshBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = carpet.originCityFa,
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            color = FarshTextPrimary
                        )
                    }
                }
            }

            // Main Info Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                Text(
                    text = carpet.titleFa,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = carpet.titleEn,
                    fontFamily = CinzelFontFamily,
                    fontSize = 11.sp,
                    color = FarshGoldWarm,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Price & Workshop Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(FarshCardSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(12.dp))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "ارزش اثر:",
                            style = MaterialTheme.typography.labelSmall,
                            color = FarshTextSecondary
                        )
                        Text(
                            text = formatPrice(carpet, currentCurrency),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "کارگاه بافنده:",
                            style = MaterialTheme.typography.labelSmall,
                            color = FarshTextSecondary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = carpet.weaverMaster,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = FarshTextPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = FarshGold,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 4 Interactive Experience Cards
                Text(
                    text = "ابزارهای کارشناسی و بررسی اثر:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InteractiveActionCard(
                        title = "شناسنامه اصالت",
                        subtitle = "سند هویتی اثر",
                        icon = Icons.Default.Security,
                        onClick = onOpenCertificate,
                        modifier = Modifier.weight(1f)
                    )
                    InteractiveActionCard(
                        title = "ذره‌بین و گره‌سنج",
                        subtitle = "نمای ماکرو بافت",
                        icon = Icons.Default.ZoomIn,
                        onClick = onOpenMacroTexture,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InteractiveActionCard(
                        title = "پیش‌نمایش سالن",
                        subtitle = "شبیه‌ساز دکوراسیون",
                        icon = Icons.Default.ViewInAr,
                        onClick = onOpenRoomPreview,
                        modifier = Modifier.weight(1f)
                    )
                    InteractiveActionCard(
                        title = "گفتگو با بافنده",
                        subtitle = "استعلام و مذاکره",
                        icon = Icons.AutoMirrored.Filled.Chat,
                        onClick = onOpenDirectChat,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Technical Specifications Matrix
                Text(
                    text = "مشخصات فنی و اصالت بافت:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(FarshCardSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    SpecRowDetail("ابعاد:", carpet.dimensions)
                    SpecRowDetail("سبک و نقشه:", carpet.stylePatternFa)
                    SpecRowDetail("تراکم گره (رج):", "${carpet.knotDensityRaj} رج در هر ۷ سانتی‌متر")
                    SpecRowDetail("نوع گره:", carpet.knotTypeFa)
                    SpecRowDetail("جنس چله و پرز:", carpet.materialFa)
                    SpecRowDetail("رنگرزی گیاهی:", carpet.dyeingTypeFa)
                    SpecRowDetail("پودگذاری:", carpet.weftCountFa)
                    SpecRowDetail("کد رهگیری شناسنامه:", carpet.certificateId)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Heritage Story Section
                Text(
                    text = "روایت و داستان شاهکار:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(FarshVelvetSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = carpet.storyNarrativeFa,
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 12.sp,
                        lineHeight = 22.sp,
                        color = FarshTextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Merchant & Workshop Profile
                Text(
                    text = "اطلاعات گالری و فروشنده اثر:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(FarshCardSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(12.dp))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = carpet.merchantName.take(1),
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = carpet.merchantName,
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = FarshTextPrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = FarshGold,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = FarshTextSecondary,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = carpet.merchantLocation,
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                color = FarshTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Pinned Bottom Bar: Direct Purchase & Inquiry CTA
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            FarshObsidian.copy(alpha = 0.8f),
                            FarshObsidian
                        )
                    )
                )
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Inquiry button
                Button(
                    onClick = onOpenDirectChat,
                    modifier = Modifier
                        .weight(0.42f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FarshCardElevated
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, FarshBorder)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        contentDescription = null,
                        tint = FarshGoldWarm,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "گفتگو",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshTextPrimary,
                        fontSize = 12.sp
                    )
                }

                // Buy / Reserve Button
                Button(
                    onClick = onOpenDirectChat,
                    modifier = Modifier
                        .weight(0.58f)
                        .height(48.dp)
                        .testTag("reserve_carpet_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FarshGold
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "هماهنگی معامله مستقیم",
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

@Composable
private fun InteractiveActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(FarshCardSurface)
            .border(1.dp, FarshBorder, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(FarshGoldContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = FarshGold,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                fontFamily = VazirmatnFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = FarshTextPrimary
            )
            Text(
                text = subtitle,
                fontFamily = VazirmatnFontFamily,
                fontSize = 9.sp,
                color = FarshTextSecondary
            )
        }
    }
}

@Composable
private fun SpecRowDetail(label: String, value: String) {
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
            color = FarshTextSecondary
        )
        Text(
            text = value,
            fontFamily = VazirmatnFontFamily,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = FarshTextPrimary
        )
    }
}
