package com.example.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.example.R
import com.example.model.StoryConfig
import com.example.model.StoryTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object StoryBitmapGenerator {

    suspend fun generateStoryBitmap(
        context: Context,
        config: StoryConfig,
        targetWidth: Int = 1080,
        targetHeight: Int = 1920
    ): Bitmap = withContext(Dispatchers.Default) {
        val output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        // Colors based on theme
        val (bgTop, bgBottom, goldColor, textPrimary, textSecondary) = when (config.theme) {
            StoryTheme.ROYAL_OBSIDIAN -> Quintuple(
                0xFF0D0C10.toInt(),
                0xFF1A1922.toInt(),
                0xFFD4AF37.toInt(),
                0xFFF9F7F2.toInt(),
                0xFFB5B0A2.toInt()
            )
            StoryTheme.PARCHMENT_CLASSIC -> Quintuple(
                0xFFFAF8F2.toInt(),
                0xFFE8E2D2.toInt(),
                0xFF8B6B23.toInt(),
                0xFF1A1815.toInt(),
                0xFF5C5446.toInt()
            )
            StoryTheme.MADDER_RUBY -> Quintuple(
                0xFF4A0D15.toInt(),
                0xFF200508.toInt(),
                0xFFE5C158.toInt(),
                0xFFFAF7F2.toInt(),
                0xFFD9B9A6.toInt()
            )
        }

        // 1. Background Gradient
        val bgPaint = Paint().apply {
            shader = LinearGradient(
                0f, 0f, 0f, targetHeight.toFloat(),
                bgTop, bgBottom, Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, targetWidth.toFloat(), targetHeight.toFloat(), bgPaint)

        // 2. Ornate Border Frame
        val borderPaint = Paint().apply {
            color = goldColor
            style = Paint.Style.STROKE
            strokeWidth = 6f
            isAntiAlias = true
        }
        val innerBorderPaint = Paint().apply {
            color = goldColor
            alpha = 130
            style = Paint.Style.STROKE
            strokeWidth = 2f
            isAntiAlias = true
        }
        canvas.drawRoundRect(RectF(40f, 40f, targetWidth - 40f, targetHeight - 40f), 32f, 32f, borderPaint)
        canvas.drawRoundRect(RectF(56f, 56f, targetWidth - 56f, targetHeight - 56f), 24f, 24f, innerBorderPaint)

        // Load Vazirmatn font if available
        val typefaceVazir: Typeface = try {
            ResourcesCompat.getFont(context, R.font.vazirmatn) ?: Typeface.DEFAULT_BOLD
        } catch (e: Exception) {
            Typeface.DEFAULT_BOLD
        }

        // 3. Header: Brand Tagline
        val headerPaint = Paint().apply {
            color = goldColor
            typeface = typefaceVazir
            textSize = 34f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("بازار فرش ایران و جهان (دیجیتال مارکت)", targetWidth / 2f, 130f, headerPaint)

        val subHeaderPaint = Paint().apply {
            color = textSecondary
            typeface = typefaceVazir
            textSize = 24f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("اصالت قالی دستباف • گالری اختصاصی بافندگان", targetWidth / 2f, 175f, subHeaderPaint)

        // 4. Carpet Photo Framing (Center Area)
        val photoTop = 220f
        val photoHeight = 880f
        val photoRect = RectF(80f, photoTop, targetWidth - 80f, photoTop + photoHeight)

        // Background shadow for photo frame
        val photoFramePaint = Paint().apply {
            color = 0xFF14131A.toInt()
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawRoundRect(photoRect, 20f, 20f, photoFramePaint)

        // Draw the Carpet Bitmap
        config.carpetBitmap?.let { src ->
            val srcRect = Rect(0, 0, src.width, src.height)
            canvas.save()
            // Clip to rounded rect
            val path = android.graphics.Path().apply {
                addRoundRect(photoRect, 20f, 20f, android.graphics.Path.Direction.CW)
            }
            canvas.clipPath(path)
            canvas.drawBitmap(src, srcRect, photoRect, Paint(Paint.FILTER_BITMAP_FLAG))
            canvas.restore()
        }

        // Border around photo
        val photoOutline = Paint().apply {
            color = goldColor
            style = Paint.Style.STROKE
            strokeWidth = 4f
            isAntiAlias = true
        }
        canvas.drawRoundRect(photoRect, 20f, 20f, photoOutline)

        // 5. Badges Overlay over Photo (Raj Density & Origin City)
        val badgeBg = Paint().apply {
            color = 0xDD0D0C10.toInt()
            style = Paint.Style.FILL
        }
        val badgeBorder = Paint().apply {
            color = goldColor
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        val badgeText = Paint().apply {
            color = goldColor
            typeface = typefaceVazir
            textSize = 30f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        val badgeLeftRect = RectF(110f, photoTop + 30f, 340f, photoTop + 95f)
        canvas.drawRoundRect(badgeLeftRect, 14f, 14f, badgeBg)
        canvas.drawRoundRect(badgeLeftRect, 14f, 14f, badgeBorder)
        canvas.drawText(config.raj, badgeLeftRect.centerX(), badgeLeftRect.centerY() + 10f, badgeText)

        val badgeRightRect = RectF(targetWidth - 340f, photoTop + 30f, targetWidth - 110f, photoTop + 95f)
        canvas.drawRoundRect(badgeRightRect, 14f, 14f, badgeBg)
        canvas.drawRoundRect(badgeRightRect, 14f, 14f, badgeBorder)
        canvas.drawText(config.originCity, badgeRightRect.centerX(), badgeRightRect.centerY() + 10f, badgeText)

        // 6. Carpet Information Card Below Photo
        var currentY = photoTop + photoHeight + 80f

        // Title
        val titlePaint = Paint().apply {
            color = textPrimary
            typeface = typefaceVazir
            textSize = 46f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText(config.title, targetWidth / 2f, currentY, titlePaint)

        currentY += 60f

        // Workshop & Dimensions
        val infoPaint = Paint().apply {
            color = goldColor
            typeface = typefaceVazir
            textSize = 32f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("${config.weaverName} • ${config.dimensions}", targetWidth / 2f, currentY, infoPaint)

        currentY += 80f

        // 7. Price Banner
        val priceBox = RectF(120f, currentY, targetWidth - 120f, currentY + 110f)
        val priceBgPaint = Paint().apply {
            color = 0x44D4AF37.toInt()
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(priceBox, 18f, 18f, priceBgPaint)
        canvas.drawRoundRect(priceBox, 18f, 18f, badgeBorder)

        val priceTextPaint = Paint().apply {
            color = goldColor
            typeface = typefaceVazir
            textSize = 38f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText(config.priceText, targetWidth / 2f, priceBox.centerY() + 12f, priceTextPaint)

        currentY += 160f

        // 8. Footer: Contact & Social Info
        val contactBox = RectF(80f, currentY, targetWidth - 80f, currentY + 160f)
        val contactBg = Paint().apply {
            color = 0xFF181720.toInt()
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(contactBox, 20f, 20f, contactBg)
        canvas.drawRoundRect(contactBox, 20f, 20f, innerBorderPaint)

        val contactPaint = Paint().apply {
            color = textPrimary
            typeface = typefaceVazir
            textSize = 32f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("تماس و سفارش: ${config.contactPhone}", targetWidth / 2f, currentY + 65f, contactPaint)

        val socialPaint = Paint().apply {
            color = textSecondary
            typeface = typefaceVazir
            textSize = 26f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("کانال و اینستاگرام: ${config.socialHandle}", targetWidth / 2f, currentY + 120f, socialPaint)

        // Free Tool Watermark at very bottom
        val footerPaint = Paint().apply {
            color = textSecondary
            alpha = 150
            typeface = typefaceVazir
            textSize = 20f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("تولید شده با استوری‌ساز رایگان فرشبازار • ویژه بافندگان و تجار", targetWidth / 2f, targetHeight - 70f, footerPaint)

        output
    }
}

private data class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)
