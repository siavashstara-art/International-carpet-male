package com.example.ui.components

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshMadderRuby
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun HeroExhibitionCarousel(
    showcaseCarpets: List<CarpetItem>,
    currentCurrency: Currency,
    onCarpetClick: (CarpetItem) -> Unit,
    formatPrice: (CarpetItem, Currency) -> String,
    modifier: Modifier = Modifier
) {
    if (showcaseCarpets.isEmpty()) return

    var currentIndex by remember { mutableIntStateOf(0) }
    val currentCarpet = showcaseCarpets[currentIndex % showcaseCarpets.size]

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(FarshGold)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "تالار شاهکارهای منتخب",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )
            }

            // Pagination Dots & Navigation
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        currentIndex = if (currentIndex > 0) currentIndex - 1 else showcaseCarpets.size - 1
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "اثر قبلی",
                        tint = FarshGoldWarm
                    )
                }

                Text(
                    text = "${currentIndex + 1} / ${showcaseCarpets.size}",
                    fontFamily = CinzelFontFamily,
                    fontSize = 11.sp,
                    color = FarshTextSecondary
                )

                IconButton(
                    onClick = {
                        currentIndex = (currentIndex + 1) % showcaseCarpets.size
                    },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "اثر بعدی",
                        tint = FarshGoldWarm
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Main Showcase Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.35f)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, FarshGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .clickable { onCarpetClick(currentCarpet) }
                .testTag("hero_exhibition_card")
        ) {
            // Carpet Image
            Image(
                painter = painterResource(id = currentCarpet.imageResId),
                contentDescription = currentCarpet.titleFa,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Luxury Gradient Overlay (top-to-bottom and bottom-to-top)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                FarshObsidian.copy(alpha = 0.6f),
                                Color.Transparent,
                                FarshObsidian.copy(alpha = 0.95f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Top Badges
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Museum Masterpiece Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(FarshMadderRuby.copy(alpha = 0.85f))
                        .border(0.5.dp, FarshGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "شاهکار موزه‌ای",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Knot Density Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(FarshObsidian.copy(alpha = 0.85f))
                        .border(1.dp, FarshGold, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${currentCarpet.knotDensityRaj} رج اعلا",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarshGoldWarm
                    )
                }
            }

            // Bottom Information Overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = currentCarpet.originCityFa + " • " + currentCarpet.dimensions,
                    style = MaterialTheme.typography.labelSmall,
                    color = FarshGoldWarm,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = currentCarpet.titleFa,
                    style = MaterialTheme.typography.titleLarge,
                    color = FarshTextPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = currentCarpet.weaverMaster,
                            style = MaterialTheme.typography.bodySmall,
                            color = FarshTextSecondary,
                            fontSize = 11.sp
                        )
                        Text(
                            text = formatPrice(currentCarpet, currentCurrency),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                    }

                    // Discover / View Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(FarshGold)
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = "بررسی جزئیات اثر",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = FarshObsidian,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
