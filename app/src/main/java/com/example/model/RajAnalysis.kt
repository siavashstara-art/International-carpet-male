package com.example.model

import android.graphics.Bitmap

/**
 * Image Quality Assessment for Carpet Weave Analysis.
 * Evaluates illumination, sharpness/focus, alignment angle, and whether the back of the rug is visible.
 */
data class ImageQualityAssessment(
    val isAcceptable: Boolean,
    val resolutionValid: Boolean,
    val width: Int,
    val height: Int,
    val brightnessScore: Float, // 0.0 to 1.0 (too dark < 0.25, washed out > 0.85, ideal 0.35 - 0.75)
    val brightnessStatusFa: String,
    val contrastScore: Float, // 0.0 to 1.0
    val contrastStatusFa: String,
    val sharpnessScore: Float, // based on edge variance
    val sharpnessStatusFa: String,
    val isBackOfRugDetected: Boolean,
    val backOfRugStatusFa: String,
    val diagnosticMessages: List<String>
)

/**
 * Request payload for Raj analysis service.
 */
data class RajAnalysisRequest(
    val imageBitmap: Bitmap,
    val declaredOriginCity: String? = null,
    val referencePhysicalScaleMm: Float? = null // Optional physical ruler/scale in mm
)

/**
 * Result of the Raj estimation service contract.
 * Note: Per business specifications, fake/fabricated raj numbers MUST NOT be displayed
 * when no verified computer vision or AI backend engine is connected.
 */
data class RajAnalysisResult(
    val isSuccess: Boolean,
    val status: AnalysisStatus,
    val measuredRaj: Int? = null, // Only populated if verified calculation algorithm exists
    val unitOfMeasure: String = "رج در هر ۷ سانتی‌متر",
    val estimationMethod: String? = null,
    val confidenceRate: Float? = null, // 0.0 to 1.0
    val knotCountPerSquareMeter: Long? = null,
    val engineDiagnosticMessage: String,
    val executionTimestamp: Long = System.currentTimeMillis()
)

enum class AnalysisStatus {
    IMAGE_EVALUATED_OK,
    IMAGE_QUALITY_DEFICIENT,
    BACKEND_ENGINE_NOT_CONNECTED,
    NETWORK_OR_TIMEOUT_ERROR,
    ANALYSIS_COMPLETED
}

/**
 * Contract for Raj estimation engine (local ML Kit / OpenCV / Remote AI vision service).
 */
interface RajEstimationService {
    suspend fun evaluateImageQuality(bitmap: Bitmap): ImageQualityAssessment
    suspend fun analyzeRaj(request: RajAnalysisRequest): RajAnalysisResult
}
