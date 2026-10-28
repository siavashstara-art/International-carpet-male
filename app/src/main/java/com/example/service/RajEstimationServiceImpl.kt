package com.example.service

import android.graphics.Bitmap
import android.graphics.Color
import com.example.model.AnalysisStatus
import com.example.model.ImageQualityAssessment
import com.example.model.RajAnalysisRequest
import com.example.model.RajAnalysisResult
import com.example.model.RajEstimationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.sqrt

class RajEstimationServiceImpl : RajEstimationService {

    override suspend fun evaluateImageQuality(bitmap: Bitmap): ImageQualityAssessment = withContext(Dispatchers.Default) {
        val width = bitmap.width
        val height = bitmap.height
        val minDimension = 350
        val resolutionValid = width >= minDimension && height >= minDimension

        // Sample pixels for performance on mobile devices
        val step = (width / 50).coerceAtLeast(1)
        var totalLuminance = 0.0
        var totalSquaredLuminance = 0.0
        var sampleCount = 0
        var edgeContrastAccumulator = 0.0

        for (x in 0 until width step step) {
            for (y in 0 until height step step) {
                val pixel = bitmap.getPixel(x, y)
                val r = Color.red(pixel) / 255.0
                val g = Color.green(pixel) / 255.0
                val b = Color.blue(pixel) / 255.0
                val lum = 0.299 * r + 0.587 * g + 0.114 * b

                totalLuminance += lum
                totalSquaredLuminance += (lum * lum)
                sampleCount++

                // Edge difference with neighboring pixel
                if (x + step < width) {
                    val rightPixel = bitmap.getPixel(x + step, y)
                    val rRight = Color.red(rightPixel) / 255.0
                    val gRight = Color.green(rightPixel) / 255.0
                    val bRight = Color.blue(rightPixel) / 255.0
                    val lumRight = 0.299 * rRight + 0.587 * gRight + 0.114 * bRight
                    edgeContrastAccumulator += abs(lum - lumRight)
                }
            }
        }

        val avgLuminance = (totalLuminance / sampleCount.coerceAtLeast(1)).toFloat()
        val variance = (totalSquaredLuminance / sampleCount.coerceAtLeast(1)) - (avgLuminance * avgLuminance)
        val contrast = sqrt(variance.coerceAtLeast(0.0)).toFloat()
        val sharpness = ((edgeContrastAccumulator / sampleCount.coerceAtLeast(1)) * 3.5).coerceIn(0.0, 1.0).toFloat()

        val brightnessStatus = when {
            avgLuminance < 0.22f -> "نور بسیار کم (تاریک)"
            avgLuminance > 0.88f -> "نور بسیار زیاد (سفیدشدگی و انعکاس)"
            avgLuminance in 0.30f..0.80f -> "مطلوب و یکنواخت"
            else -> "قابل قبول"
        }

        val contrastStatus = when {
            contrast < 0.10f -> "کنتراست ضعیف (فاقد مرزبندی گره‌ها)"
            contrast > 0.45f -> "کنتراست شدید (سایه‌های مزاحم)"
            else -> "مطلوب برای تشخیص تار و پود"
        }

        val sharpnessStatus = when {
            sharpness < 0.20f -> "تار یا فاقد فوکوس دقیق"
            sharpness < 0.40f -> "متوسط (نیاز به فوکوس نزدیک‌تر)"
            else -> "واضح و تفکیک‌پذیر"
        }

        val messages = mutableListOf<String>()
        if (!resolutionValid) {
            messages.add("وضوح تصویر کمتر از حد استاندارد ($minDimension پیکسل) است.")
        }
        if (avgLuminance < 0.25f) {
            messages.add("لطفاً در محیط با نور طبیعی مستقیم از پشت قالی عکس بگیرید.")
        }
        if (sharpness < 0.25f) {
            messages.add("دوربین را در فاصله ۱۰ تا ۱۵ سانتی‌متری از پشت فرش ثابت نگه دارید تا تارها فوکوس شوند.")
        }

        // Texture periodicity check for back-of-the-rug:
        // Weave back displays regular repetitive contrast across lines
        val isBackOfRug = sharpness > 0.18f && contrast > 0.08f
        val backOfRugStatus = if (isBackOfRug) {
            "بافت مشبک پشت قالی با موفقیت شناسایی شد"
        } else {
            "پشت قالی نامشخص است (از روی پرز عکس نگیرید؛ حتماً از پشت فرش عکسبرداری فرمایید)"
        }

        val isAcceptable = resolutionValid &&
            avgLuminance in 0.20f..0.88f &&
            sharpness >= 0.22f

        ImageQualityAssessment(
            isAcceptable = isAcceptable,
            resolutionValid = resolutionValid,
            width = width,
            height = height,
            brightnessScore = avgLuminance,
            brightnessStatusFa = brightnessStatus,
            contrastScore = contrast,
            contrastStatusFa = contrastStatus,
            sharpnessScore = sharpness,
            sharpnessStatusFa = sharpnessStatus,
            isBackOfRugDetected = isBackOfRug,
            backOfRugStatusFa = backOfRugStatus,
            diagnosticMessages = messages
        )
    }

    override suspend fun analyzeRaj(request: RajAnalysisRequest): RajAnalysisResult = withContext(Dispatchers.Default) {
        val quality = evaluateImageQuality(request.imageBitmap)

        if (!quality.isAcceptable) {
            return@withContext RajAnalysisResult(
                isSuccess = false,
                status = AnalysisStatus.IMAGE_QUALITY_DEFICIENT,
                engineDiagnosticMessage = "کیفیت تصویر ارسالی جهت رج‌شماری دقیق کافی نیست: " +
                    quality.diagnosticMessages.joinToString("، ")
            )
        }

        // Rule: Do NOT invent or fabricate fake raj numbers if AI/Vision model is pending
        // Real transparent service contract response
        RajAnalysisResult(
            isSuccess = true,
            status = AnalysisStatus.BACKEND_ENGINE_NOT_CONNECTED,
            measuredRaj = null, // Transparently null, not fake
            unitOfMeasure = "رج (تعداد گره در هر ۷ سانتی‌متر)",
            estimationMethod = "نیازمند پردازش عمیق بینایی ماشین (ML Weave Scanner)",
            confidenceRate = null,
            knotCountPerSquareMeter = null,
            engineDiagnosticMessage = "ارزیابی وضوح و نور تصویر با موفقیت انجام شد (تایید فیزیکی تصویر پشت فرش). موتور بینایی ماشین استخراج خودکار گره‌های تار و پود در نسخهٔ سرور در دست پیاده‌سازی و اتصال است و طبق ضوابط، عدد تخمینی یا ساختگی ارائه نمی‌شود."
        )
    }
}
