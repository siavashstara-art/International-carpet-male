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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.CarpetFabricType
import com.example.model.ListingItem
import com.example.model.ListingType
import com.example.model.QuotaUsage
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
fun ClassifiedsScreen(
    listings: List<ListingItem>,
    quotaUsage: QuotaUsage,
    onAddNewListingClick: () -> Unit,
    onContactOwnerClick: (ListingItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilterIndex by remember { mutableIntStateOf(0) } // 0: All, 1: Buy Requests, 2: Sale Ads, 3: Business Promotions
    var selectedFabricType by remember { mutableStateOf<CarpetFabricType?>(null) }
    var selectedCraftCategory by remember { mutableStateOf<String?>(null) }

    val filteredListings = remember(listings, selectedFilterIndex, selectedFabricType, selectedCraftCategory) {
        listings.filter { item ->
            val matchesType = when (selectedFilterIndex) {
                0 -> true
                1 -> item.listingType == ListingType.BUY_REQUEST
                2 -> item.listingType == ListingType.SELL_LISTING
                3 -> item.listingType == ListingType.BUSINESS_PROMOTION
                else -> true
            }
            val matchesFabric = selectedFabricType == null || item.fabricType == selectedFabricType
            val matchesCraft = if (selectedCraftCategory.isNullOrBlank()) {
                true
            } else {
                item.title.contains(selectedCraftCategory!!, ignoreCase = true) ||
                    item.description.contains(selectedCraftCategory!!, ignoreCase = true)
            }
            matchesType && matchesFabric && matchesCraft
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("classifieds_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "بازار آگهی‌ها و خدمات فرش",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = FarshTextPrimary
                        )
                        Text(
                            text = "CLASSIFIEDS, BUY REQUESTS & SERVICES",
                            fontFamily = CinzelFontFamily,
                            fontSize = 8.sp,
                            letterSpacing = 1.sp,
                            color = FarshGoldWarm
                        )
                    }

                    // Add Listing CTA Button
                    Button(
                        onClick = onAddNewListingClick,
                        colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("create_classified_ad_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = FarshObsidian,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ثبت آگهی",
                            fontFamily = VazirmatnFontFamily,
                            color = FarshObsidian,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Quota Policy Transparency Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, FarshGold.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "طرح سهمیهٔ فعال:",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp,
                                    color = FarshTextSecondary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (quotaUsage.policy.isLaunchFreeTierActive) "طرح افتتاحیه رایگان نامحدود" else "سهمیه استاندارد دوره",
                                    fontFamily = VazirmatnFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = FarshGoldWarm
                                )
                            }
                            Text(
                                text = "درخواست خرید: ${quotaUsage.remainingBuyRequests} مجاز • آگهی فروش: ${quotaUsage.remainingSellListings} مجاز • تبلیغات خدمات: ${quotaUsage.remainingBusinessAds} مجاز",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 10.sp,
                                color = FarshTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Section Filter Chips (Buy Requests vs Sale Ads vs Business Ads)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val types = listOf(
                        "همه بخش‌ها (${listings.size})",
                        "درخواست‌های خرید (${listings.count { it.listingType == ListingType.BUY_REQUEST }})",
                        "آگهی‌های فروش (${listings.count { it.listingType == ListingType.SELL_LISTING }})",
                        "تبلیغات خدمات و قالیشویی (${listings.count { it.listingType == ListingType.BUSINESS_PROMOTION }})"
                    )

                    items(types.indices.toList()) { index ->
                        FilterChip(
                            selected = selectedFilterIndex == index,
                            onClick = { selectedFilterIndex = index },
                            label = {
                                Text(
                                    text = types[index],
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp,
                                    fontWeight = if (selectedFilterIndex == index) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshGold,
                                selectedLabelColor = FarshObsidian,
                                containerColor = FarshCardSurface,
                                labelColor = FarshTextPrimary
                            )
                        )
                    }
                }

                // Fabric Sub-Filter: Handmade vs Machine-made
                if (selectedFilterIndex != 3) {
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedFabricType == null,
                                onClick = { selectedFabricType = null },
                                label = { Text("همه بافت‌ها", fontFamily = VazirmatnFontFamily, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FarshBorder,
                                    selectedLabelColor = FarshGoldWarm,
                                    containerColor = Color.Transparent,
                                    labelColor = FarshTextSecondary
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedFabricType == CarpetFabricType.HANDMADE,
                                onClick = { selectedFabricType = CarpetFabricType.HANDMADE },
                                label = { Text("قالی دستباف اصیل", fontFamily = VazirmatnFontFamily, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FarshBorder,
                                    selectedLabelColor = FarshGoldWarm,
                                    containerColor = Color.Transparent,
                                    labelColor = FarshTextSecondary
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedFabricType == CarpetFabricType.MACHINE_MADE,
                                onClick = { selectedFabricType = CarpetFabricType.MACHINE_MADE },
                                label = { Text("فرش ماشینی و کارخانجات", fontFamily = VazirmatnFontFamily, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FarshBorder,
                                    selectedLabelColor = FarshGoldWarm,
                                    containerColor = Color.Transparent,
                                    labelColor = FarshTextSecondary
                                )
                            )
                        }
                    }
                }

                // Craft Categories Filter Row (Comprehensive 15 Woven Crafts)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val craftCategories = listOf(
                        null to "همه دسته‌ها",
                        "گلیم" to "گلیم",
                        "جاجیم" to "جاجیم",
                        "تابلو" to "تابلوفرش و پرده‌فرش",
                        "زیلو" to "زیلو",
                        "ورنی" to "ورنی و بافته تخت",
                        "خورجین" to "خورجین و جوال",
                        "نمکدان" to "نمکدان و کیسه",
                        "سفره" to "سفره‌های سنتی",
                        "جل اسب" to "جل اسب و زین",
                        "پشتی" to "پشتی، پادری و کوسن",
                        "کیف" to "کیف، کفش و اکسسوری",
                        "پوشش" to "پوشش ظروف"
                    )

                    items(craftCategories) { (catKey, catLabel) ->
                        FilterChip(
                            selected = selectedCraftCategory == catKey,
                            onClick = { selectedCraftCategory = catKey },
                            label = { Text(catLabel, fontFamily = VazirmatnFontFamily, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshGoldContainer,
                                selectedLabelColor = FarshGoldWarm,
                                containerColor = FarshCardSurface,
                                labelColor = FarshTextSecondary
                            )
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "آگهی‌های فعال در سامانه (${filteredListings.size}):",
                fontFamily = VazirmatnFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = FarshTextSecondary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Listings List
        items(filteredListings) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                ClassifiedItemCard(
                    item = item,
                    onContactClick = { onContactOwnerClick(item) }
                )
            }
        }
    }
}

@Composable
private fun ClassifiedItemCard(
    item: ListingItem,
    onContactClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, FarshBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Type Badge & Verification Level
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Type badge (Buy Request vs Sell vs Business)
                val (badgeText, badgeBg, badgeTextColor) = when (item.listingType) {
                    ListingType.BUY_REQUEST -> Triple("درخواست خرید قالی", Color(0xFF1E3A8A), Color(0xFF93C5FD))
                    ListingType.SELL_LISTING -> Triple(
                        if (item.fabricType == CarpetFabricType.HANDMADE) "فروش قالی دستباف" else "فروش فرش ماشینی",
                        Color(0xFF064E3B), Color(0xFFA7F3D0)
                    )
                    ListingType.BUSINESS_PROMOTION -> Triple("تبلیغات خدمات و کسب‌وکار", FarshGoldContainer, FarshGoldWarm)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeBg)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = badgeText,
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeTextColor
                    )
                }

                // Owner verification tag
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = Color(item.ownerVerificationLevel.badgeColorHex),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.ownerVerificationLevel.titleFa,
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 10.sp,
                        color = Color(item.ownerVerificationLevel.badgeColorHex)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = item.title,
                fontFamily = VazirmatnFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = FarshTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = item.description,
                fontFamily = VazirmatnFontFamily,
                fontSize = 11.sp,
                lineHeight = 18.sp,
                color = FarshTextSecondary
            )

            // Machine Carpet Specs if available
            item.machineSpecs?.let { specs ->
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(FarshVelvetSurface)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "مشخصات فنی: ${specs.reedsCount} شانه • تراکم ${specs.density} • برند: ${specs.factoryBrandName}",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 10.sp,
                        color = FarshGoldWarm
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = FarshBorder, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Footer: City, Price & Direct Contact
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${item.city} • ${item.dimensions}",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 10.sp,
                        color = FarshTextSecondary
                    )
                    Text(
                        text = item.priceText,
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = FarshGoldWarm
                    )
                }

                Button(
                    onClick = onContactClick,
                    colors = ButtonDefaults.buttonColors(containerColor = FarshCardElevated),
                    border = androidx.compose.foundation.BorderStroke(1.dp, FarshBorder),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = FarshGoldWarm,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "تماس: ${item.ownerPhoneMasked}",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 10.sp,
                        color = FarshTextPrimary
                    )
                }
            }
        }
    }
}
