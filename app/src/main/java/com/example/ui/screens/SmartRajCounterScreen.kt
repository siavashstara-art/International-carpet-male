package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AnalysisStatus
import com.example.model.ImageQualityAssessment
import com.example.model.RajAnalysisRequest
import com.example.model.RajAnalysisResult
import com.example.service.RajEstimationServiceImpl
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
fun SmartRajCounterScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val rajService = remember { RajEstimationServiceImpl() }

    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isAnalyzing by remember { mutableStateOf(false) }
    var qualityAssessment by remember { mutableStateOf<ImageQualityAssessment?>(null) }
    var analysisResult by remember { mutableStateOf<RajAnalysisResult?>(null) }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            selectedBitmap = bitmap
            isAnalyzing = true
            analysisResult = null
            coroutineScope.launch {
                val quality = rajService.evaluateImageQuality(bitmap)
                qualityAssessment = quality
                val result = rajService.analyzeRaj(RajAnalysisRequest(imageBitmap = bitmap))
                analysisResult = result
                isAnalyzing = false
            }
        }
    }

    // Photo Picker Launcher
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
                selectedBitmap = bitmap
                isAnalyzing = true
                analysisResult = null
                coroutineScope.launch {
                    val quality = rajService.evaluateImageQuality(bitmap)
                    qualityAssessment = quality
                    val result = rajService.analyzeRaj(RajAnalysisRequest(imageBitmap = bitmap))
                    analysisResult = result
                    isAnalyzing = false
                }
            } catch (e: Exception) {
                // Handled gracefully
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(FarshObsidian)
            .testTag("smart_raj_counter_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
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
                                imageVector = Icons.Default.Straighten,
                                contentDescription = null,
                                tint = FarshGold,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "رج‌شمار هوشمند قالی",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = FarshTextPrimary
                            )
                            Text(
                                text = "SMART WEAVE & KNOT DENSITY ANALYZER",
                                fontFamily = CinzelFontFamily,
                                fontSize = 8.sp,
                                letterSpacing = 1.sp,
                                color = FarshGoldWarm
                            )
                        }
                    }

                    // Back / Done Button
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

                Text(
                    text = "ارزیابی تخصصی کیفیت تصویر و تراکم گره‌های پشت قالی بر پایهٔ ضوابط کارشناسی فرش ایران و جهان.",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 11.sp,
                    lineHeight = 19.sp,
                    color = FarshTextSecondary
                )
            }
        }

        // Educational Photography Instructions Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, FarshGold.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = FarshGoldWarm,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "دستورالعمل تصویربرداری صحیح جهت رج‌شماری:",
                            fontFamily = VazirmatnFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = FarshGoldWarm
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "۱. عکسبرداری حتماً از «پشت قالی» انجام شود؛ زیرا گره‌ها و تار و پود فقط در پشت فرش قابل اندازه‌گیری هستند.\n۲. گوشی را در فاصله ۱۰ تا ۱۵ سانتی‌متری، کاملاً موازی با سطح فرش نگه دارید.\n۳. از نور یکنواخت روز یا چراغ بدون سایه مستقیم استفاده فرمایید.\n۴. جهت مقایسه مقیاس، قرار دادن یک خط‌کش ۷ سانتی‌متری در کنار بافت توصیه می‌شود.",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 11.sp,
                        lineHeight = 19.sp,
                        color = FarshTextSecondary
                    )
                }
            }
        }

        // Camera & Gallery Capture Buttons
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { cameraLauncher.launch() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("raj_camera_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        tint = FarshObsidian,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "عکسبرداری زنده",
                        fontFamily = VazirmatnFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = FarshObsidian,
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = {
                        photoPickerLauncher.launch(
                            androidx.activity.result.PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("raj_gallery_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = FarshCardElevated),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, FarshBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = null,
                        tint = FarshGoldWarm,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "انتخاب از گالری",
                        fontFamily = VazirmatnFontFamily,
                        color = FarshTextPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Image Preview & Analysis Status
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (selectedBitmap != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.3f)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, FarshGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                            .background(FarshCardSurface)
                    ) {
                        Image(
                            bitmap = selectedBitmap!!.asImageBitmap(),
                            contentDescription = "تصویر بارگذاری شده پشت قالی",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        if (isAnalyzing) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(FarshObsidian.copy(alpha = 0.75f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(color = FarshGold)
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "در حال پردازش پیکسل‌ها و سنجش نور و وضوح...",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 12.sp,
                                        color = FarshTextPrimary
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(FarshCardSurface)
                            .border(1.dp, FarshBorder, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Straighten,
                                contentDescription = null,
                                tint = FarshTextSecondary,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "هنوز تصویری از پشت قالی انتخاب نشده است",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 12.sp,
                                color = FarshTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Quality Assessment Breakdown
        qualityAssessment?.let { quality ->
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "نتیجه ارزیابی فنی وضوح تصویر:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarshTextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, FarshBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = FarshCardSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Quality status badge
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "وضعیت کلی تصویر:",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 12.sp,
                                    color = FarshTextSecondary
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (quality.isAcceptable) Icons.Default.CheckCircle else Icons.Default.WarningAmber,
                                        contentDescription = null,
                                        tint = if (quality.isAcceptable) Color(0xFF4CAF50) else FarshMadderRuby,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (quality.isAcceptable) "کیفیت تصویر تایید شد" else "کیفیت ناکافی",
                                        fontFamily = VazirmatnFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (quality.isAcceptable) Color(0xFF4CAF50) else FarshMadderRuby
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(color = FarshBorder, thickness = 0.5.dp)
                            Spacer(modifier = Modifier.height(8.dp))

                            AssessmentMetricRow("روشنایی و نورسنجی:", quality.brightnessStatusFa, quality.brightnessScore)
                            AssessmentMetricRow("کنتراست و تفکیک تارها:", quality.contrastStatusFa, quality.contrastScore)
                            AssessmentMetricRow("وضوح و فوکوس گره‌ها:", quality.sharpnessStatusFa, quality.sharpnessScore)
                            AssessmentMetricRow("ارزیابی بافت پشت قالی:", quality.backOfRugStatusFa, if (quality.isBackOfRugDetected) 1f else 0.2f)

                            if (quality.diagnosticMessages.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                quality.diagnosticMessages.forEach { msg ->
                                    Text(
                                        text = "• $msg",
                                        fontFamily = VazirmatnFontFamily,
                                        fontSize = 11.sp,
                                        color = FarshMadderRuby
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Analysis Result & Contract Disclosure
        analysisResult?.let { res ->
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "وضعیت استخراج رج و تراکم گره:",
                        fontFamily = VazirmatnFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FarshTextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, FarshGold.copy(alpha = 0.6f), RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = FarshVelvetSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Service Contract Transparency Notice
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(FarshGoldContainer)
                                    .padding(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = FarshGoldWarm,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "شفافیت تجاری و مهندسی فرشبازار:",
                                            fontFamily = VazirmatnFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = FarshGoldWarm
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = res.engineDiagnosticMessage,
                                            fontFamily = VazirmatnFontFamily,
                                            fontSize = 11.sp,
                                            lineHeight = 18.sp,
                                            color = FarshTextPrimary
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "واحد استاندارد: ${res.unitOfMeasure}",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                color = FarshTextSecondary
                            )
                            Text(
                                text = "روش تخمین: ${res.estimationMethod ?: "در انتظار اتصال مدل بینایی ماشین"}",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 11.sp,
                                color = FarshTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Privacy Guarantee Card
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(FarshCardSurface)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = FarshGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "حفظ حریم خصوصی: تصاویر اختصاصی کاربر روی سرور عمومی بارگذاری نمی‌شود و صرفاً جهت ارزیابی در حافظه موقت پردازش می‌گردد.",
                    fontFamily = VazirmatnFontFamily,
                    fontSize = 10.sp,
                    color = FarshTextSecondary,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun AssessmentMetricRow(title: String, status: String, score: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontFamily = VazirmatnFontFamily,
                fontSize = 11.sp,
                color = FarshTextSecondary
            )
            Text(
                text = status,
                fontFamily = VazirmatnFontFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = FarshTextPrimary
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        LinearProgressIndicator(
            progress = { score.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = FarshGold,
            trackColor = FarshBorder
        )
    }
}
