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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PostAdd
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.BusinessServiceCategory
import com.example.model.CarpetFabricType
import com.example.model.ListingItem
import com.example.model.ListingStatus
import com.example.model.ListingType
import com.example.model.MachineCarpetSpecs
import com.example.model.QuotaUsage
import com.example.model.UserAccount
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
import java.util.UUID

@Composable
fun CreateClassifiedAdDialog(
    user: UserAccount,
    quotaUsage: QuotaUsage,
    onDismiss: () -> Unit,
    onSubmitListing: (ListingItem) -> Boolean
) {
    var listingTypeIndex by remember { mutableIntStateOf(0) } // 0: Buy Request, 1: Sale Ad, 2: Business Promotion
    var fabricTypeIndex by remember { mutableIntStateOf(0) } // 0: Handmade, 1: Machine-made
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("تهران") }
    var dimensions by remember { mutableStateOf("۶ متری (۲۰۰ × ۳۰۰)") }
    var priceText by remember { mutableStateOf("توافقی") }
    var machineReeds by remember { mutableIntStateOf(1200) }
    var machineDensity by remember { mutableIntStateOf(3600) }
    var machineBrand by remember { mutableStateOf("صنایع فرش کاشان") }

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
                .testTag("create_classified_dialog"),
            color = FarshObsidian
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Header
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
                            text = "ثبت آگهی و درخواست در فرشبازار",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshGoldWarm
                        )
                        Text(
                            text = "CREATE CLASSIFIED AD OR BUY REQUEST",
                            fontFamily = CinzelFontFamily,
                            fontSize = 8.sp,
                            letterSpacing = 1.sp,
                            color = FarshTextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(FarshGoldContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PostAdd,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Select Ad Category: Buy Request vs Sale Ad vs Business Promotion
                Text(
                    text = "نوع آگهی مورد نظر را انتخاب فرمایید:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    color = FarshTextSecondary,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val categories = listOf("درخواست خرید", "آگهی فروش", "تبلیغات خدمات")
                    categories.forEachIndexed { idx, name ->
                        FilterChip(
                            selected = listingTypeIndex == idx,
                            onClick = { listingTypeIndex = idx },
                            label = { Text(name, fontFamily = VazirmatnFontFamily, fontSize = 10.sp) },
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

                Spacer(modifier = Modifier.height(10.dp))

                // If Sale or Buy: Choose Fabric Type (Handmade vs Machine-made)
                if (listingTypeIndex != 2) {
                    Text(
                        text = "نوع بافت قالی:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        color = FarshTextSecondary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val craftCategories = listOf(
                            "قالی دستباف اصیل",
                            "فرش ماشینی مدرن",
                            "تابلوفرش و پرده‌فرش",
                            "گلیم، جاجیم و زیلو",
                            "ورنی و بافته‌های تخت",
                            "سایر دستبافته‌های سنتی"
                        )
                        itemsIndexed(craftCategories) { idx, catName ->
                            FilterChip(
                                selected = fabricTypeIndex == idx,
                                onClick = { fabricTypeIndex = idx },
                                label = { Text(catName, fontFamily = VazirmatnFontFamily, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FarshBorder,
                                    selectedLabelColor = FarshGoldWarm,
                                    containerColor = FarshCardSurface,
                                    labelColor = FarshTextPrimary
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Input Fields
                AdInputRow(
                    label = if (listingTypeIndex == 0) "عنوان نیاز / قالی درخواستی:" else "عنوان آگهی:",
                    value = title,
                    onValueChange = { title = it },
                    placeholder = if (listingTypeIndex == 0) "مثال: خریدار قالیچه ابریشم قم ۶ متری" else "مثال: قالیچه ۷۰ رج تبریز طرح سالاری"
                )

                Spacer(modifier = Modifier.height(8.dp))

                AdInputRow(
                    label = "توضیحات و شرایط:",
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "مشخصات فنی، شرایط تسویه، بودجه یا وضعیت سلامت فرش...",
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        AdInputRow("شهر / استان:", city, { city = it }, "مثال: تبریز، تهران")
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        AdInputRow("ابعاد مورد نظر:", dimensions, { dimensions = it }, "مثال: ۶ متری، زرع و نیم")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                AdInputRow(
                    label = if (listingTypeIndex == 0) "بودجه پیشنهادی (تومان):" else "قیمت فروش (تومان):",
                    value = priceText,
                    onValueChange = { priceText = it },
                    placeholder = "مثال: ۴۵۰,۰۰۰,۰۰۰ تومان یا توافقی"
                )

                // Machine-made rug technical specs if applicable
                if (listingTypeIndex == 1 && fabricTypeIndex == 1) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "مشخصات شانه و تراکم فرش ماشینی:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarshGoldWarm,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(700 to 2550, 1200 to 3600, 1500 to 4500).forEach { (reeds, density) ->
                            FilterChip(
                                selected = machineReeds == reeds,
                                onClick = {
                                    machineReeds = reeds
                                    machineDensity = density
                                },
                                label = { Text("$reeds شانه / $density", fontFamily = VazirmatnFontFamily, fontSize = 9.sp) },
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

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Button
                Button(
                    onClick = {
                        val finalTitle = title.ifBlank {
                            if (listingTypeIndex == 0) "درخواست خرید قالی در $city" else "آگهی فروش فرش در $city"
                        }
                        val finalType = when (listingTypeIndex) {
                            0 -> ListingType.BUY_REQUEST
                            1 -> ListingType.SELL_LISTING
                            else -> ListingType.BUSINESS_PROMOTION
                        }
                        val fabric = when {
                            listingTypeIndex == 2 -> CarpetFabricType.SERVICES_ONLY
                            fabricTypeIndex == 0 -> CarpetFabricType.HANDMADE
                            else -> CarpetFabricType.MACHINE_MADE
                        }

                        val machineSpecs = if (fabric == CarpetFabricType.MACHINE_MADE) {
                            MachineCarpetSpecs(machineReeds, machineDensity, "۱۰۰٪ اکریلیک هیت‌ست", machineBrand)
                        } else null

                        val newAd = ListingItem(
                            id = "ad_${UUID.randomUUID().toString().take(8)}",
                            ownerUserId = user.id,
                            ownerName = user.fullName,
                            ownerPhoneMasked = user.maskedMobileNumber,
                            ownerVerificationLevel = user.verificationLevel,
                            listingType = finalType,
                            fabricType = fabric,
                            title = finalTitle,
                            description = description.ifBlank { "ثبت شده در بازار فرش ایران و جهان (دیجیتال مارکت)" },
                            province = city,
                            city = city,
                            dimensions = dimensions,
                            priceText = priceText,
                            status = ListingStatus.PUBLISHED,
                            machineSpecs = machineSpecs
                        )
                        val success = onSubmitListing(newAd)
                        if (success) {
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_classified_ad_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "ثبت و انتشار آگهی در بازار فرش ایران و جهان",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshObsidian,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun AdInputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontFamily = VazirmatnFontFamily,
            fontSize = 11.sp,
            color = FarshTextSecondary
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    color = FarshTextSecondary.copy(alpha = 0.5f)
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
            singleLine = singleLine
        )
    }
}
