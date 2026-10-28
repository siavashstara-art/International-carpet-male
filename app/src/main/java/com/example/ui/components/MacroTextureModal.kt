package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.ui.theme.FarshMadderRuby
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshVelvetSurface
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun MacroTextureModal(
    carpet: CarpetItem,
    onDismiss: () -> Unit
) {
    var zoomLevel by remember { mutableIntStateOf(2) } // 1x, 2x, 4x
    var isObliqueLight by remember { mutableStateOf(false) }

    val knotsPerSquareMeter = carpet.knotDensityRaj * carpet.knotDensityRaj * 200

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
                .testTag("macro_texture_modal"),
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
                            text = "ذره‌بین و گره‌سنج تخصصی بافت",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                        Text(
                            text = "MACRO WEAVE & KNOT INSPECTION",
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
                            imageVector = Icons.Default.ZoomIn,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // High-Resolution Macro View Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.2f)
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, FarshBorder, RoundedCornerShape(14.dp))
                        .background(FarshCardSurface)
                ) {
                    val scaleFactor = when (zoomLevel) {
                        1 -> 1.0f
                        2 -> 2.2f
                        else -> 4.5f
                    }

                    Image(
                        painter = painterResource(id = carpet.imageResId),
                        contentDescription = "نمای ماکرو بافت فرش",
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(scaleFactor),
                        contentScale = ContentScale.Crop
                    )

                    // Overlay grid simulating knot counting ruler
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(0.5.dp, FarshGold.copy(alpha = 0.3f))
                    )

                    // Oblique Light Sheen Effect
                    if (isObliqueLight) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(alpha = 0.12f))
                        )
                    }

                    // Floating Zoom Badge on top of image
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(FarshObsidian.copy(alpha = 0.85f))
                            .border(0.5.dp, FarshGold, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "بزرگ‌نمایی: ${if (zoomLevel == 1) "۱× عادی" else if (zoomLevel == 2) "۲.۵× پرز و ابریشم" else "۵× گره چله"}",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Zoom controls & Light toggles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "میزان درشت‌نمایی:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 12.sp,
                        color = FarshTextSecondary
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(1 to "۱×", 2 to "۲.۵×", 3 to "۵×").forEach { (level, label) ->
                            FilterChip(
                                selected = zoomLevel == level,
                                onClick = { zoomLevel = level },
                                label = {
                                    Text(
                                        text = label,
                                        fontFamily = CinzelFontFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
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
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sheen & Lighting Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(FarshVelvetSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LightMode,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "شبیه‌ساز نورپردازی گالری (درخشش ابریشم)",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            color = FarshTextPrimary
                        )
                    }

                    FilterChip(
                        selected = isObliqueLight,
                        onClick = { isObliqueLight = !isObliqueLight },
                        label = {
                            Text(
                                text = if (isObliqueLight) "روشن" else "خاموش",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FarshGoldWarm,
                            selectedLabelColor = FarshObsidian
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = FarshBorder, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                // Knot Calculations Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(FarshCardSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "محاسبات فنی بافت دستباف:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarshGoldWarm
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "• رج‌شمار قالی: ${carpet.knotDensityRaj} رج (در هر ۷ سانتی‌متر طول چله)",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary
                    )
                    Text(
                        text = "• تخمین تراکم گره: حدوداً ${knotsPerSquareMeter.formatThousands()} گره در هر متر مربع",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary
                    )
                    Text(
                        text = "• ساختار گره: ${carpet.knotTypeFa}",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary
                    )
                    Text(
                        text = "• چله‌کشی و پود: ${carpet.materialFa}",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary
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
                        text = "بازگشت به گالری اثر",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshObsidian,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

private fun Int.formatThousands(): String {
    return java.text.NumberFormat.getNumberInstance(java.util.Locale("fa", "IR")).format(this)
}
