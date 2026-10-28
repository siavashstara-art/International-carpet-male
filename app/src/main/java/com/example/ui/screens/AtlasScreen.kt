package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CarpetRegion
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.FarshBorder
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
fun AtlasScreen(
    regions: List<CarpetRegion>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("atlas_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "اطلس و دانش‌نامه مکاتب قالی ایران",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = FarshTextPrimary
                        )
                        Text(
                            text = "PERSIAN CARPET ATLAS & HERITAGE",
                            fontFamily = CinzelFontFamily,
                            fontSize = 9.sp,
                            letterSpacing = 1.sp,
                            color = FarshGoldWarm
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "شناخت تفاوت‌های تاریخی، سبک نقوش، هندسه گره‌ها و رازهای رنگرزی گیاهی در شهرهای بافندگی کهن ایران.",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    color = FarshTextSecondary
                )
            }
        }

        // Comparative Knot Knowledge Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(FarshCardSurface)
                    .border(1.dp, FarshGold.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "گره‌شناسی تطبیقی: متقارن vs نامتقارن",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "• گره متقارن ترکی (تبریز، بیجار، قشقایی): خامه فرش به دور هر دو تار چله پیچیده می‌شود و با قلاب کوبیده می‌شود. ویژگی: استحکام فوق‌العاده بالا و پایداری هندسی نقش.\n• گره نامتقارن فارسی (اصفهان، قم، کاشان، نایین): خامه تنها به دور یک تار پیچیده شده و از پشت تار دیگر رد می‌شود. ویژگی: امکان بافت خطوط منحنی بی‌نقص، چرخش‌های اسلیمی و ظرافت میلی‌متری.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        lineHeight = 19.sp,
                        color = FarshTextSecondary
                    )
                }
            }
        }

        // Section Title: Regional Hubs
        item {
            Text(
                text = "مکاتب شاخص بافندگی",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = FarshTextPrimary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        // Regions list
        items(regions) { region ->
            RegionAtlasCard(
                region = region,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun RegionAtlasCard(
    region: CarpetRegion,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, FarshBorder, RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Region Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FarshVelvetSurface)
                            .border(1.dp, FarshGold.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = region.nameFa.take(1),
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "مکتب فرش ${region.nameFa}",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = FarshTextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = region.nameEn,
                                fontFamily = CinzelFontFamily,
                                fontSize = 10.sp,
                                color = FarshGoldWarm
                            )
                        }
                        Text(
                            text = "استان: ${region.provinceFa} • رج معمول: ${region.typicalRaj}",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            color = FarshTextSecondary
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = FarshGoldWarm,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Signature style summary
            Text(
                text = "طرح‌های شاخص: ${region.signatureStyleFa}",
                fontFamily = VazirmatnFontFamily,
                fontSize = 12.sp,
                color = FarshGoldWarm
            )

            // Signature Color Chips
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ColorLens,
                    contentDescription = null,
                    tint = FarshTextSecondary,
                    modifier = Modifier.size(14.dp)
                )
                region.signatureColorsFa.forEach { colorName ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(FarshVelvetSurface)
                            .border(0.5.dp, FarshBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = colorName,
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            color = FarshTextSecondary
                        )
                    }
                }
            }

            // Expanded Historical Details
            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = FarshBorder, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = region.historyDescriptionFa,
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    lineHeight = 19.sp,
                    color = FarshTextPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "سیستم گره‌زنی: ${region.knotSystemFa}",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    color = FarshGoldWarm
                )
            }
        }
    }
}
