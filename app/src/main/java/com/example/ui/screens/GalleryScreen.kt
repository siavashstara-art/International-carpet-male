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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CarpetCategory
import com.example.model.CarpetItem
import com.example.model.Currency
import com.example.ui.components.CarpetCard
import com.example.ui.components.HeroExhibitionCarousel
import com.example.ui.theme.FarshBorder
import com.example.ui.theme.FarshCardSurface
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun GalleryScreen(
    carpets: List<CarpetItem>,
    showcaseCarpets: List<CarpetItem>,
    selectedCategory: CarpetCategory,
    onSelectCategory: (CarpetCategory) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedCity: String?,
    onSelectCity: (String?) -> Unit,
    selectedRaj: Int?,
    onSelectRaj: (Int?) -> Unit,
    currentCurrency: Currency,
    onCarpetClick: (CarpetItem) -> Unit,
    onToggleFavorite: (String) -> Unit,
    formatPrice: (CarpetItem, Currency) -> String,
    modifier: Modifier = Modifier
) {
    val cities = listOf("تبریز", "اصفهان", "قم", "نایین", "کاشان", "شیراز", "بیجار")
    val rajFilters = listOf(null to "همه رج‌ها", 50 to "۵۰+ رج", 65 to "۶۵+ رج اعلا", 75 to "۷۵+ رج موزه‌ای")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("gallery_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Search Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    placeholder = {
                        Text(
                            text = "جستجوی شاهکار، شهر، نقشه، استادکار (مثال: سالاری، ۷۰ رج)...",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            color = FarshTextSecondary
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChange("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "پاک کردن",
                                    tint = FarshTextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("gallery_search_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FarshGold,
                        unfocusedBorderColor = FarshBorder,
                        focusedTextColor = FarshTextPrimary,
                        unfocusedTextColor = FarshTextPrimary,
                        focusedContainerColor = FarshCardSurface,
                        unfocusedContainerColor = FarshCardSurface
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        // Hero Exhibition Carousel (Show only when no search is active)
        if (searchQuery.isBlank()) {
            item {
                HeroExhibitionCarousel(
                    showcaseCarpets = showcaseCarpets,
                    currentCurrency = currentCurrency,
                    onCarpetClick = onCarpetClick,
                    formatPrice = formatPrice
                )
            }
        }

        // Categories Row
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(CarpetCategory.values()) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { onSelectCategory(category) },
                            label = {
                                Text(
                                    text = category.titleFa,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshGold,
                                selectedLabelColor = FarshObsidian,
                                containerColor = FarshCardSurface,
                                labelColor = FarshTextPrimary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = selectedCategory == category,
                                borderColor = FarshBorder,
                                selectedBorderColor = FarshGold
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }
        }

        // Quick City & Raj Filter Chips
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                // Cities row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCity == null,
                            onClick = { onSelectCity(null) },
                            label = {
                                Text(
                                    text = "همه شهرها",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshBorder,
                                selectedLabelColor = FarshGoldWarm,
                                containerColor = Color.Transparent,
                                labelColor = FarshTextSecondary
                            )
                        )
                    }
                    items(cities) { city ->
                        FilterChip(
                            selected = selectedCity == city,
                            onClick = { onSelectCity(if (selectedCity == city) null else city) },
                            label = {
                                Text(
                                    text = city,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshBorder,
                                selectedLabelColor = FarshGoldWarm,
                                containerColor = Color.Transparent,
                                labelColor = FarshTextSecondary
                            )
                        )
                    }
                }

                // Raj Density filter row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    items(rajFilters) { (rajVal, label) ->
                        FilterChip(
                            selected = selectedRaj == rajVal,
                            onClick = { onSelectRaj(rajVal) },
                            label = {
                                Text(
                                    text = label,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 10.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshGoldWarm.copy(alpha = 0.2f),
                                selectedLabelColor = FarshGoldWarm,
                                containerColor = Color.Transparent,
                                labelColor = FarshTextSecondary
                            )
                        )
                    }
                }
            }
        }

        // Section Title: Gallery Items Count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "مجموعه آثار (${carpets.size} تخته قالی)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )

                if (selectedCity != null || selectedRaj != null || selectedCategory != CarpetCategory.ALL) {
                    Text(
                        text = "فیلتر فعال",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshGoldWarm
                    )
                }
            }
        }

        // Carpets List
        if (carpets.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(FarshCardSurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = null,
                                tint = FarshGold,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "اثری با مشخصات انتخابی یافت نشد",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = FarshTextPrimary,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "می‌توانید فیلترهای شهر یا رج‌شمار را تغییر دهید",
                            fontFamily = VazirmatnFontFamily,
                            color = FarshTextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
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
}
