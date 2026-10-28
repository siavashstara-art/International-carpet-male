package com.example.ui.components

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Currency
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.FarshBorder
import com.example.ui.theme.FarshCardSurface
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldContainer
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshVelvetSurface
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun LuxuryTopBar(
    currentCurrency: Currency,
    onCurrencyToggle: () -> Unit,
    onSearchToggle: () -> Unit,
    onAddListingClick: () -> Unit,
    onMemorialClick: () -> Unit,
    onRajCounterClick: () -> Unit,
    onStoryMakerClick: () -> Unit,
    onAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        FarshObsidian,
                        FarshVelvetSurface.copy(alpha = 0.95f)
                    )
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Column {
            // Main Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Identity (Right - RTL)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer)
                            .border(1.dp, FarshGold, CircleShape)
                            .clickable { onAccountClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ب",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm,
                            fontSize = 17.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "بازار فرش ایران و جهان",
                            fontFamily = VazirmatnFontFamily,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = FarshTextPrimary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "دیجیتال مارکت تخصصی قالی دستباف و ماشینی",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 9.sp,
                            color = FarshGoldWarm
                        )
                    }
                }

                // Action controls (Left)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Memorial Button: "به یاد پدر"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(FarshGoldContainer)
                            .border(0.8.dp, FarshGold, RoundedCornerShape(16.dp))
                            .clickable { onMemorialClick() }
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("memorial_tribute_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Stars,
                                contentDescription = "به یاد پدر",
                                tint = FarshGoldWarm,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "به یاد پدر",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = FarshGoldWarm
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Account profile button
                    IconButton(
                        onClick = onAccountClick,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FarshCardSurface)
                            .border(0.5.dp, FarshBorder, CircleShape)
                            .testTag("account_profile_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "شناسنامه کاربری و معرفان",
                            tint = FarshGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Currency Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(FarshBorder)
                            .border(0.8.dp, FarshGold.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                            .clickable { onCurrencyToggle() }
                            .padding(horizontal = 7.dp, vertical = 4.dp)
                            .testTag("currency_toggle_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${currentCurrency.symbolFa} ⟳",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Medium,
                            color = FarshGoldWarm
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Add Listing
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(FarshGold.copy(alpha = 0.15f))
                            .border(1.dp, FarshGold.copy(alpha = 0.6f), CircleShape)
                            .clickable { onAddListingClick() }
                            .padding(5.dp)
                            .testTag("add_listing_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "ثبت آگهی",
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Sub-Tool Bar: Quick Access to Smart Tools
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Smart Raj Counter Tool Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(FarshCardSurface)
                        .border(0.5.dp, FarshBorder, RoundedCornerShape(8.dp))
                        .clickable { onRajCounterClick() }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                        .testTag("smart_raj_tool_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Straighten,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "رج‌شمار هوشمند",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FarshTextPrimary
                        )
                    }
                }

                // Free Story Maker Tool Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(FarshCardSurface)
                        .border(0.5.dp, FarshBorder, RoundedCornerShape(8.dp))
                        .clickable { onStoryMakerClick() }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                        .testTag("story_maker_tool_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Style,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "استوری‌ساز رایگان",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FarshTextPrimary
                        )
                    }
                }
            }
        }
    }
}
