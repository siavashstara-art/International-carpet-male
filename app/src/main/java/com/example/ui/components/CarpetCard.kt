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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CarpetItem
import com.example.model.Currency
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.FarshBorder
import com.example.ui.theme.FarshBorderSubtle
import com.example.ui.theme.FarshCardSurface
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshMadderRuby
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshTextTertiary
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun CarpetCard(
    carpet: CarpetItem,
    currentCurrency: Currency,
    onCarpetClick: (CarpetItem) -> Unit,
    onToggleFavorite: (String) -> Unit,
    formatPrice: (CarpetItem, Currency) -> String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(0.7.dp, FarshBorder, RoundedCornerShape(14.dp))
            .clickable { onCarpetClick(carpet) }
            .testTag("carpet_card_${carpet.id}"),
        colors = CardDefaults.cardColors(
            containerColor = FarshCardSurface
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Carpet Image with Aspect Ratio & Overlays
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.22f)
            ) {
                Image(
                    painter = painterResource(id = carpet.imageResId),
                    contentDescription = carpet.titleFa,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Subtle gradient at bottom of image
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    FarshCardSurface.copy(alpha = 0.85f)
                                ),
                                startY = 100f
                            )
                        )
                )

                // Top badges: Raj count + Favorite button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Density Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(FarshObsidian.copy(alpha = 0.8f))
                            .border(0.6.dp, FarshGold.copy(alpha = 0.8f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${carpet.knotDensityRaj} رج",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                    }

                    // Favorite Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FarshObsidian.copy(alpha = 0.7f))
                            .clickable { onToggleFavorite(carpet.id) }
                            .testTag("favorite_button_${carpet.id}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (carpet.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "نشان‌کردن",
                            tint = if (carpet.isFavorite) FarshMadderRuby else FarshTextPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Origin City & Material tag pinned at bottom left of image
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = carpet.originCityFa,
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarshGoldWarm
                    )
                    Text(
                        text = " • ",
                        color = FarshTextTertiary,
                        fontSize = 11.sp
                    )
                    Text(
                        text = carpet.dimensions,
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 10.sp,
                        color = FarshTextSecondary
                    )
                }
            }

            // Info Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Title
                Text(
                    text = carpet.titleFa,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = FarshTextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Weaver and Verified Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = carpet.weaverMaster,
                        style = MaterialTheme.typography.bodySmall,
                        color = FarshTextSecondary,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (carpet.merchantVerified) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "اصالت تایید شده",
                            tint = FarshGold,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price and Style Pattern
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatPrice(carpet, currentCurrency),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = FarshGoldWarm
                    )

                    // Subtle Certificate ID tag
                    Text(
                        text = carpet.certificateId.take(10),
                        fontFamily = CinzelFontFamily,
                        fontSize = 9.sp,
                        color = FarshTextTertiary
                    )
                }
            }
        }
    }
}
