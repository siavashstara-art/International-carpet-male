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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CarpetItem
import com.example.model.Currency
import com.example.ui.components.CarpetCard
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
fun MarketplaceScreen(
    carpets: List<CarpetItem>,
    currentCurrency: Currency,
    onCarpetClick: (CarpetItem) -> Unit,
    onToggleFavorite: (String) -> Unit,
    formatPrice: (CarpetItem, Currency) -> String,
    onAddListingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("marketplace_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Merchant & Direct Trading Header Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                FarshGoldContainer,
                                FarshCardSurface
                            )
                        )
                    )
                    .border(1.dp, FarshGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(FarshGold),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Handshake,
                                    contentDescription = null,
                                    tint = FarshObsidian,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "تالار معاملات مستقیم فرش",
                                    fontFamily = VazirmatnFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = FarshTextPrimary
                                )
                                Text(
                                    text = "ارتباط مستقیم خریدار با استادکار و بافنده",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp,
                                    color = FarshGoldWarm
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "فرشبازار بستر اختصاصی عرضه قالیچه‌ها و شاهکارهای اصیل دستباف به بازارهای جهانی است. بدون واسطه، با صدور شناسنامه دیجیتال و تضمین اصالت گره و رنگ.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        lineHeight = 18.sp,
                        color = FarshTextSecondary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onAddListingClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("marketplace_add_listing_cta"),
                        colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = FarshObsidian,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "عرضه و ثبت آگهی اثر جدید (بافنده / تاجر)",
                            fontFamily = VazirmatnFontFamily,
                            color = FarshObsidian,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Trust & Guarantee Badges Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GuaranteePill(
                    icon = Icons.Default.Verified,
                    title = "تاییدیه اصالت",
                    subtitle = "کارشناسی الیاف",
                    modifier = Modifier.weight(1f)
                )
                GuaranteePill(
                    icon = Icons.Default.Security,
                    title = "بیمه حمل و نقل",
                    subtitle = "تحویل جهانی امن",
                    modifier = Modifier.weight(1f)
                )
                GuaranteePill(
                    icon = Icons.Default.Storefront,
                    title = "حجره‌های کهن",
                    subtitle = "بازارهای تاریخی",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Section Title: Available Listings
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "آگهی‌های فعال در بازار (${carpets.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )

                Text(
                    text = "فروش مستقیم بدون کمیسیون",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    color = FarshGoldWarm
                )
            }
        }

        // Carpet listings in marketplace
        items(carpets) { carpet ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                CarpetCard(
                    carpet = carpet,
                    currentCurrency = currentCurrency,
                    onCarpetClick = onCarpetClick,
                    onToggleFavorite = onToggleFavorite,
                    formatPrice = formatPrice
                )
            }
        }
    }
}

@Composable
private fun GuaranteePill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, FarshBorder, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = FarshGold,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
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
