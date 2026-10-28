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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
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
import com.example.model.CarpetInquiry
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
import com.example.ui.theme.FarshParchment
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshVelvetSurface
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun SavedAndAtelierScreen(
    favoriteCarpets: List<CarpetItem>,
    inquiries: List<CarpetInquiry>,
    currentCurrency: Currency,
    onCarpetClick: (CarpetItem) -> Unit,
    onToggleFavorite: (String) -> Unit,
    formatPrice: (CarpetItem, Currency) -> String,
    onOpenChatForCarpet: (CarpetItem) -> Unit,
    allCarpets: List<CarpetItem>,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableIntStateOf(0) } // 0: Favorites, 1: Chats/Inquiries, 2: Profile & Atelier

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("saved_and_atelier_screen"),
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
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Diamond,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "آتلیه و برگزیده‌های من",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = FarshTextPrimary
                        )
                        Text(
                            text = "MY ATELIER & SAVED MASTERPIECES",
                            fontFamily = CinzelFontFamily,
                            fontSize = 8.sp,
                            letterSpacing = 1.sp,
                            color = FarshGoldWarm
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Sub-tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("نشان‌شده‌ها (${favoriteCarpets.size})", "پیام‌ها و مذاکرات (${inquiries.size})", "شناسنامه و هویت").forEachIndexed { index, title ->
                        FilterChip(
                            selected = selectedSubTab == index,
                            onClick = { selectedSubTab = index },
                            label = {
                                Text(
                                    text = title,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 11.sp,
                                    fontWeight = if (selectedSubTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshGold,
                                selectedLabelColor = FarshObsidian,
                                containerColor = FarshCardSurface,
                                labelColor = FarshTextPrimary
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // SubTab 0: Favorite Carpets
        if (selectedSubTab == 0) {
            if (favoriteCarpets.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(FarshCardSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = FarshGold,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "هنوز اثری را نشان نکرده‌اید",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = FarshTextPrimary,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "با لمس آیکون قلب روی هر قالیچه، آن را به این بخش بیفزایید.",
                                fontFamily = VazirmatnFontFamily,
                                color = FarshTextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
                items(favoriteCarpets) { carpet ->
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

        // SubTab 1: Inquiries & Conversations
        if (selectedSubTab == 1) {
            if (inquiries.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "گفتگویی وجود ندارد",
                            fontFamily = VazirmatnFontFamily,
                            color = FarshTextSecondary
                        )
                    }
                }
            } else {
                items(inquiries) { inq ->
                    val carpet = allCarpets.find { it.id == inq.carpetId }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, FarshBorder, RoundedCornerShape(12.dp))
                            .clickable {
                                if (carpet != null) onOpenChatForCarpet(carpet)
                            },
                        colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Chat,
                                        contentDescription = null,
                                        tint = FarshGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = inq.merchantName,
                                        fontFamily = VazirmatnFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = FarshTextPrimary
                                    )
                                }
                                Text(
                                    text = inq.timestampFormatted,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 10.sp,
                                    color = FarshTextSecondary
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "درباره: ${inq.carpetTitle}",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                color = FarshGoldWarm
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = inq.messageText,
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 12.sp,
                                color = FarshTextSecondary,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }

        // SubTab 2: Profile & Collector Identity
        if (selectedSubTab == 2) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Profile Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, FarshGold.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
                        colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(FarshGoldContainer)
                                        .border(1.5.dp, FarshGold, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = FarshGoldWarm,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "مجموعه‌دار و خریدار احراز هویت شده",
                                            fontFamily = VazirmatnFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
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
                                    Text(
                                        text = "سطح اعتبار: VIP طلایی بین‌المللی",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 11.sp,
                                        color = FarshGoldWarm
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = FarshBorder, thickness = 0.5.dp)
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "مزایای حساب کاربری در فرشبازار:",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = FarshGoldWarm
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "• دسترسی به شناسنامه‌های رمزنگاری شده رسمی\n• امکان پیشنهاد قیمت مستقیم به اساتید و بافندگان\n• اولویت در پیش‌خرید شاهکارهای سفارشی روی دار\n• بیمه حمل تخصصی و ترخیص گمرکی بین‌المللی",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                lineHeight = 20.sp,
                                color = FarshTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Memorial Dedication Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, FarshGold.copy(alpha = 0.6f), RoundedCornerShape(14.dp)),
                        colors = CardDefaults.cardColors(containerColor = FarshVelvetSurface)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "به یاد و نام پدر",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 12.sp,
                                color = FarshGoldWarm,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "مرحوم حاج حسین آقای علیمیری",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = FarshTextPrimary
                            )
                            Text(
                                text = "از تاجران خوشنام فرش ایران و جهان",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                color = FarshGold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "« این مجموعه به یاد و احترام مردی بنا می‌شود که نامش با هنر اصیل فرش و دنیای تجارت پیوند خورده است؛ با آرزوی آنکه بازار فرش ایران و جهان، پلی میان اصالت و نوآوری، فرهنگ و تجارت باشد و یاد ایشان را در مسیر پاسداشت هنر قالی ایرانی زنده نگه دارد. »",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                lineHeight = 19.sp,
                                color = FarshParchment,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "یادشان گرامی و نامشان ماندگار",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = FarshGoldWarm
                            )
                        }
                    }
                }
            }
        }
    }
}
