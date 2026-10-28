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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.CarpetItem
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
fun RoomPreviewModal(
    carpet: CarpetItem,
    onDismiss: () -> Unit
) {
    var selectedRoomType by remember { mutableIntStateOf(0) } // 0: Modern Penthouse, 1: Classic Palace, 2: Minimalist Loft
    var selectedFloor by remember { mutableIntStateOf(0) } // 0: Marble, 1: Walnut Wood, 2: Neutral Slate

    val roomNames = listOf("پنت‌هاوس معاصر", "عمارت کلاسیک", "سالن مینیمال")
    val floorNames = listOf("سنگ مرمر کلکته", "پارکت چوب گردو", "گرانیت خنثی")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, FarshGold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .background(FarshObsidian)
                .testTag("room_preview_modal"),
            color = FarshObsidian
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
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
                            text = "شبیه‌ساز و پیش‌نمایش در سالن لوکس",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                        Text(
                            text = "ROOM PREVIEW & INTERIOR HARMONY",
                            fontFamily = CinzelFontFamily,
                            fontSize = 8.sp,
                            letterSpacing = 1.sp,
                            color = FarshTextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ViewInAr,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Simulated Room Stage
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.28f)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, FarshBorder, RoundedCornerShape(14.dp))
                ) {
                    // Floor Background based on selection
                    val floorGradient = when (selectedFloor) {
                        0 -> Brush.verticalGradient(listOf(Color(0xFFE8E5DD), Color(0xFFD6D1C4))) // White/Beige Marble
                        1 -> Brush.verticalGradient(listOf(Color(0xFF382317), Color(0xFF24150D))) // Walnut
                        else -> Brush.verticalGradient(listOf(Color(0xFF2C2B30), Color(0xFF1B1A1E))) // Slate
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(floorGradient)
                    )

                    // Wall / Horizon gradient (perspective room simulation)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF16151B),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    // The Carpet placed on the floor with subtle perspective shadow
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(0.68f)
                            .aspectRatio(1.35f)
                            .shadow(24.dp, RoundedCornerShape(4.dp))
                            .border(1.dp, FarshGold.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp))
                    ) {
                        Image(
                            painter = painterResource(id = carpet.imageResId),
                            contentDescription = carpet.titleFa,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Luxury Room Environment Label Overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(FarshObsidian.copy(alpha = 0.85f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Chair,
                                contentDescription = null,
                                tint = FarshGoldWarm,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${roomNames[selectedRoomType]} • ${floorNames[selectedFloor]}",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 10.sp,
                                color = FarshTextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Room Type Selector
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "انتخاب سبک فضا و دکوراسیون:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        roomNames.forEachIndexed { index, name ->
                            FilterChip(
                                selected = selectedRoomType == index,
                                onClick = { selectedRoomType = index },
                                label = {
                                    Text(
                                        text = name,
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 11.sp
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

                Spacer(modifier = Modifier.height(10.dp))

                // Floor Finish Selector
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "جنس و رنگ کف‌پوش سالن:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        floorNames.forEachIndexed { index, name ->
                            FilterChip(
                                selected = selectedFloor == index,
                                onClick = { selectedFloor = index },
                                label = {
                                    Text(
                                        text = name,
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 11.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FarshGoldWarm,
                                    selectedLabelColor = FarshObsidian,
                                    containerColor = FarshCardSurface,
                                    labelColor = FarshTextPrimary
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Dimensions harmony note
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(FarshVelvetSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "ابعاد این اثر ${carpet.dimensions} می‌باشد که برای ایجاد نقطه کانونی (Focal Point) در نشیمن و زیر میز پذیرایی لوکس ایده‌آل است.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "تایید و بازگشت به اثر",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshObsidian,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
