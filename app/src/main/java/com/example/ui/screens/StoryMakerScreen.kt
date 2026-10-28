package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CarpetItem
import com.example.model.StoryConfig
import com.example.model.StoryTheme
import com.example.service.StoryBitmapGenerator
import com.example.service.StoryShareHelper
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
import kotlinx.coroutines.launch

@Composable
fun StoryMakerScreen(
    sampleCarpets: List<CarpetItem>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var storyConfig by remember {
        mutableStateOf(
            StoryConfig(
                title = "شاهکار ابریشم تبریز طرح سالاری",
                originCity = "تبریز",
                raj = "۷۰ رج اعلا",
                dimensions = "۲۰۰ × ۳۰۰ سانتی‌متر (۶ متری)",
                priceText = "تماس / قیمت توافقی",
                weaverName = "کارگاه استاد سالاری",
                contactPhone = "۰۹۱۲۳۴۵۶۷۸۹",
                socialHandle = "@Farshbazar_Official",
                theme = StoryTheme.ROYAL_OBSIDIAN
            )
        )
    }

    // Set initial carpet bitmap from sample if not set
    LaunchedEffect(Unit) {
        if (storyConfig.carpetBitmap == null) {
            val defaultBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_carpet_tabriz_1793099831642)
            storyConfig = storyConfig.copy(carpetBitmap = defaultBitmap)
        }
    }

    var isExporting by remember { mutableStateOf(false) }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            storyConfig = storyConfig.copy(carpetBitmap = bitmap)
        }
    }

    // Gallery Picker Launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.isMutableRequired = true
                    }
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                }
                storyConfig = storyConfig.copy(carpetBitmap = bitmap)
            } catch (e: Exception) {
                // Handled gracefully
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("story_maker_screen"),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Top Header
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(FarshGoldContainer)
                                .border(1.dp, FarshGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Style,
                                contentDescription = null,
                                tint = FarshGold,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "استوری‌ساز رایگان فرشبازار",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = FarshTextPrimary
                            )
                            Text(
                                text = "FREE 9:16 STORY MAKER FOR WEAVERS",
                                fontFamily = CinzelFontFamily,
                                fontSize = 8.sp,
                                letterSpacing = 1.sp,
                                color = FarshGoldWarm
                            )
                        }
                    }

                    // Back Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(FarshCardSurface)
                            .border(1.dp, FarshBorder, RoundedCornerShape(8.dp))
                            .clickable { onBack() }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "بازگشت",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 11.sp,
                            color = FarshTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Free for Weavers Banner
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(FarshCardSurface)
                        .border(1.dp, FarshGold.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VolunteerActivism,
                        contentDescription = null,
                        tint = FarshGoldWarm,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ابزار ۱۰۰٪ رایگان ویژه بافندگان و تجار قالی: بدون تبلیغات یا هزینهٔ پنهان. مناسب انتشار مستقیم در استوری اینستاگرام و تلگرام.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        lineHeight = 18.sp,
                        color = FarshTextPrimary
                    )
                }
            }
        }

        // Live 9:16 Story Preview Stage
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "پیش‌نمایش زنده در نسبت ابعاد استوری (۹:۱۶):",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextSecondary,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 9:16 Scaled Card Container
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.72f)
                        .aspectRatio(9f / 16f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, FarshGold, RoundedCornerShape(16.dp))
                        .background(
                            when (storyConfig.theme) {
                                StoryTheme.ROYAL_OBSIDIAN -> FarshObsidian
                                StoryTheme.PARCHMENT_CLASSIC -> Color(0xFFF7F4EB)
                                StoryTheme.MADDER_RUBY -> FarshMadderRuby
                            }
                        )
                        .padding(12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Header
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "بازار فرش ایران و جهان",
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = if (storyConfig.theme == StoryTheme.PARCHMENT_CLASSIC) Color(0xFF6B5320) else FarshGoldWarm
                            )
                            Text(
                                text = "دیجیتال مارکت تخصصی قالی دستباف",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 8.sp,
                                color = if (storyConfig.theme == StoryTheme.PARCHMENT_CLASSIC) Color(0xFF5C5446) else FarshTextSecondary
                            )
                        }

                        // Carpet Image Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, FarshGold, RoundedCornerShape(8.dp))
                                .background(FarshCardSurface)
                        ) {
                            if (storyConfig.carpetBitmap != null) {
                                Image(
                                    bitmap = storyConfig.carpetBitmap!!.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            // Badges over preview
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(FarshObsidian.copy(alpha = 0.8f))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = storyConfig.raj,
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 8.sp,
                                        color = FarshGoldWarm
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(FarshObsidian.copy(alpha = 0.8f))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = storyConfig.originCity,
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 8.sp,
                                        color = FarshGoldWarm
                                    )
                                }
                            }
                        }

                        // Details
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = storyConfig.title,
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = if (storyConfig.theme == StoryTheme.PARCHMENT_CLASSIC) Color(0xFF1A1815) else FarshTextPrimary,
                                maxLines = 1
                            )
                            Text(
                                text = "${storyConfig.weaverName} • ${storyConfig.dimensions}",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 9.sp,
                                color = if (storyConfig.theme == StoryTheme.PARCHMENT_CLASSIC) Color(0xFF6B5320) else FarshGoldWarm,
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Price Pill
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(FarshGold.copy(alpha = 0.2f))
                                    .border(0.5.dp, FarshGold, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = storyConfig.priceText,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (storyConfig.theme == StoryTheme.PARCHMENT_CLASSIC) Color(0xFF8B6B23) else FarshGoldWarm
                                )
                            }
                        }

                        // Footer contacts in story preview
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(FarshVelvetSurface.copy(alpha = 0.8f))
                                .padding(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "تماس: ${storyConfig.contactPhone}",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 8.sp,
                                color = FarshTextPrimary
                            )
                            Text(
                                text = storyConfig.socialHandle,
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 7.sp,
                                color = FarshGoldWarm
                            )
                        }
                    }
                }
            }
        }

        // Image Selection Controls
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "انتخاب یا تعویض عکس فرش برای استوری:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { cameraLauncher.launch() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = FarshCardSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FarshBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("عکاسی دوربین", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = FarshTextPrimary)
                    }

                    Button(
                        onClick = {
                            photoPickerLauncher.launch(
                                androidx.activity.result.PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = FarshCardSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FarshBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("انتخاب از گالری", fontFamily = VazirmatnFontFamily, fontSize = 11.sp, color = FarshTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Select from existing sample carpets
                Text(
                    text = "یا انتخاب از نمونه‌های گالری موجود:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    color = FarshTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(sampleCarpets) { carpet ->
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.dp, FarshBorder, RoundedCornerShape(6.dp))
                                .clickable {
                                    val b = BitmapFactory.decodeResource(context.resources, carpet.imageResId)
                                    storyConfig = storyConfig.copy(
                                        carpetBitmap = b,
                                        title = carpet.titleFa,
                                        originCity = carpet.originCityFa,
                                        raj = "${carpet.knotDensityRaj} رج اعلا",
                                        dimensions = carpet.dimensions,
                                        weaverName = carpet.weaverMaster
                                    )
                                }
                        ) {
                            Image(
                                painter = painterResource(id = carpet.imageResId),
                                contentDescription = carpet.titleFa,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }

        // Theme Selector
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "انتخاب قالب رنگ و تم بصری استوری:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    StoryTheme.values().forEach { theme ->
                        FilterChip(
                            selected = storyConfig.theme == theme,
                            onClick = { storyConfig = storyConfig.copy(theme = theme) },
                            label = {
                                Text(
                                    text = theme.titleFa,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 10.sp
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

        // Customization Input Form
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "مشخصات و متون درج شونده در استوری:",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = FarshTextPrimary
                )

                StoryInputField("عنوان قالی:", storyConfig.title) {
                    storyConfig = storyConfig.copy(title = it)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        StoryInputField("شهر خاستگاه:", storyConfig.originCity) {
                            storyConfig = storyConfig.copy(originCity = it)
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        StoryInputField("تراکم رج:", storyConfig.raj) {
                            storyConfig = storyConfig.copy(raj = it)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        StoryInputField("ابعاد قالی:", storyConfig.dimensions) {
                            storyConfig = storyConfig.copy(dimensions = it)
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        StoryInputField("قیمت درج شونده:", storyConfig.priceText) {
                            storyConfig = storyConfig.copy(priceText = it)
                        }
                    }
                }

                StoryInputField("کارگاه یا استاد بافنده:", storyConfig.weaverName) {
                    storyConfig = storyConfig.copy(weaverName = it)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        StoryInputField("تلفن تماس:", storyConfig.contactPhone) {
                            storyConfig = storyConfig.copy(contactPhone = it)
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        StoryInputField("پیج یا کانال تلگرام:", storyConfig.socialHandle) {
                            storyConfig = storyConfig.copy(socialHandle = it)
                        }
                    }
                }
            }
        }

        // Export and Share Action Buttons
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Main CTA: Generate and Open Android Native Share Sheet
                Button(
                    onClick = {
                        isExporting = true
                        coroutineScope.launch {
                            try {
                                val generatedBitmap = StoryBitmapGenerator.generateStoryBitmap(
                                    context = context,
                                    config = storyConfig,
                                    targetWidth = 1080,
                                    targetHeight = 1920
                                )
                                val cachedFile = StoryShareHelper.saveBitmapToCache(context, generatedBitmap)
                                StoryShareHelper.shareStoryImage(context, cachedFile, storyConfig.title)
                                Toast.makeText(context, "استوری ۱۰۸۰×۱۹۲۰ آماده و منوی اشتراک‌گذاری باز شد.", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "خطا در تولید یا اشتراک‌گذاری: ${e.message}", Toast.LENGTH_LONG).show()
                            } finally {
                                isExporting = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("export_and_share_story_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !isExporting
                ) {
                    if (isExporting) {
                        CircularProgressIndicator(
                            color = FarshObsidian,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "در حال ترسیم و پردازش استوری ۱۰۸۰×۱۹۲۰...",
                            fontFamily = VazirmatnFontFamily,
                            color = FarshObsidian,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = FarshObsidian,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "تولید و اشتراک‌گذاری در اینستاگرام و تلگرام",
                            fontFamily = VazirmatnFontFamily,
                            color = FarshObsidian,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                // Download / Save to Cache Button
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val generatedBitmap = StoryBitmapGenerator.generateStoryBitmap(
                                    context = context,
                                    config = storyConfig,
                                    targetWidth = 1080,
                                    targetHeight = 1920
                                )
                                val file = StoryShareHelper.saveBitmapToCache(context, generatedBitmap)
                                Toast.makeText(context, "تصویر باکیفیت استوری در حافظه ذخیره شد: ${file.name}", Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "خطا در ذخیره تصویر: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("save_story_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshCardElevated),
                    border = androidx.compose.foundation.BorderStroke(1.dp, FarshBorder),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                        tint = FarshGoldWarm,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ذخیره تصویر نهایی در حافظه دستگاه",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshTextPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StoryInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontFamily = VazirmatnFontFamily,
            fontSize = 10.sp,
            color = FarshTextSecondary
        )
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
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
            singleLine = true
        )
    }
}
