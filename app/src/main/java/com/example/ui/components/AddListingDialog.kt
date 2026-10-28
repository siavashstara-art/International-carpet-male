package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.FarshBorder
import com.example.ui.theme.FarshCardSurface
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldContainer
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun AddListingDialog(
    onDismiss: () -> Unit,
    onSubmit: (
        titleFa: String,
        originCityFa: String,
        stylePatternFa: String,
        dimensions: String,
        knotDensityRaj: Int,
        materialFa: String,
        weaverMaster: String,
        priceToman: Long,
        storyNarrativeFa: String
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("تبریز") }
    var pattern by remember { mutableStateOf("لچک و ترنج شاه‌عباسی") }
    var dimensions by remember { mutableStateOf("۲۰۰ × ۳۰۰ سانتی‌متر (۶ متری)") }
    var rajDensity by remember { mutableIntStateOf(60) }
    var material by remember { mutableStateOf("چله ابریشم، پرز کرک و گل‌ابریشم") }
    var weaver by remember { mutableStateOf("کارگاه استاد نوری") }
    var priceText by remember { mutableStateOf("450000000") }
    var narrative by remember { mutableStateOf("قالی دستباف اصیل با رنگرزی سنتی روناس و گردو، بافت بسیار متراکم و چشم‌نواز.") }

    val cities = listOf("تبریز", "اصفهان", "قم", "کاشان", "نایین", "شیراز")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, FarshGold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .background(FarshObsidian)
                .testTag("add_listing_dialog"),
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
                            text = "عرضه و ثبت اثر در فرشبازار",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                        Text(
                            text = "LIST HANDMADE MASTERPIECE",
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
                            imageVector = Icons.Default.AddBusiness,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title Input
                ListingInputField(
                    label = "عنوان اثر و قالی:",
                    value = title,
                    onValueChange = { title = it },
                    placeholder = "مثال: قالیچه ۶۰ رج لچک ترنج اعلا"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // City Selector
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "شهر و خاستگاه بافت:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        cities.take(3).forEach { c ->
                            FilterChip(
                                selected = city == c,
                                onClick = { city = c },
                                label = { Text(c, fontFamily = VazirmatnFontFamily, fontSize = 11.sp) },
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
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        cities.drop(3).forEach { c ->
                            FilterChip(
                                selected = city == c,
                                onClick = { city = c },
                                label = { Text(c, fontFamily = VazirmatnFontFamily, fontSize = 11.sp) },
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

                // Knot Density (Raj)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "رج‌شمار بافت (تراکم گره): $rajDensity رج",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshGoldWarm,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(40, 50, 60, 70, 80).forEach { r ->
                            FilterChip(
                                selected = rajDensity == r,
                                onClick = { rajDensity = r },
                                label = { Text("$r رج", fontFamily = VazirmatnFontFamily, fontSize = 11.sp) },
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

                // Dimensions & Pattern
                ListingInputField(
                    label = "ابعاد قالی:",
                    value = dimensions,
                    onValueChange = { dimensions = it },
                    placeholder = "مثال: ۲۰۰ × ۳۰۰ سانتی‌متر (۶ متری)"
                )

                Spacer(modifier = Modifier.height(10.dp))

                ListingInputField(
                    label = "طرح و نقشه:",
                    value = pattern,
                    onValueChange = { pattern = it },
                    placeholder = "مثال: لچک ترنج، شکارگاه، افشان"
                )

                Spacer(modifier = Modifier.height(10.dp))

                ListingInputField(
                    label = "چله، تار و پود و پرز:",
                    value = material,
                    onValueChange = { material = it },
                    placeholder = "مثال: چله ابریشم، پرز کرک مرینوس"
                )

                Spacer(modifier = Modifier.height(10.dp))

                ListingInputField(
                    label = "نام کارگاه / استاد بافنده:",
                    value = weaver,
                    onValueChange = { weaver = it },
                    placeholder = "مثال: کارگاه استاد سالاری"
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Price in Toman
                ListingInputField(
                    label = "قیمت پیشنهادی (تومان):",
                    value = priceText,
                    onValueChange = { priceText = it },
                    placeholder = "مثال: 500000000",
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(10.dp))

                ListingInputField(
                    label = "روایت و داستان اثر:",
                    value = narrative,
                    onValueChange = { narrative = it },
                    placeholder = "توضیح تاریخچه، رنگرزی و ارزش هنری اثر...",
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Submit Button
                Button(
                    onClick = {
                        val finalTitle = title.ifBlank { "قالیچه نفیس $city طرح $pattern" }
                        val priceNum = priceText.toLongOrNull() ?: 350_000_000L
                        onSubmit(
                            finalTitle,
                            city,
                            pattern,
                            dimensions,
                            rajDensity,
                            material,
                            weaver,
                            priceNum,
                            narrative
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_listing_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "ثبت نهایی و انتشار در بازار جهانی",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshObsidian,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ListingInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontFamily = VazirmatnFontFamily,
            fontSize = 11.sp,
            color = FarshTextSecondary
        )
        Spacer(modifier = Modifier.height(3.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    color = FarshTextSecondary.copy(alpha = 0.6f)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FarshGold,
                unfocusedBorderColor = FarshBorder,
                focusedTextColor = FarshTextPrimary,
                unfocusedTextColor = FarshTextPrimary,
                focusedContainerColor = FarshCardSurface,
                unfocusedContainerColor = FarshCardSurface
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}
