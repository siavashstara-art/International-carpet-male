package ir.farshbazaar.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import ir.farshbazaar.core.ui.theme.*
import kotlin.math.abs

@Composable
fun RugPlaceholder(
    seed: Int,
    modifier: Modifier = Modifier
) {
    val seedVal = abs(seed)
    val basePalette = when (seedVal % 3) {
        0 -> Triple(FarshRed, FarshLapis, FarshGold)
        1 -> Triple(FarshLapis, FarshRed, FarshGoldWarm)
        else -> Triple(FarshWalnut, FarshRed, FarshGold)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(FarshVelvet)
            .border(1.dp, FarshBorderGold, RoundedCornerShape(12.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Background field
            drawRect(
                color = basePalette.first,
                size = size
            )

            // Outer margin / fringe
            val fringeHeight = 12f
            for (x in 0..width.toInt() step 12) {
                drawLine(
                    color = FarshParchment.copy(alpha = 0.6f),
                    start = Offset(x.toFloat(), 0f),
                    end = Offset(x.toFloat(), fringeHeight),
                    strokeWidth = 2f
                )
                drawLine(
                    color = FarshParchment.copy(alpha = 0.6f),
                    start = Offset(x.toFloat(), height - fringeHeight),
                    end = Offset(x.toFloat(), height),
                    strokeWidth = 2f
                )
            }

            // Outer border
            val borderInset = 20f
            drawRect(
                color = basePalette.second,
                topLeft = Offset(borderInset, borderInset),
                size = Size(width - (borderInset * 2), height - (borderInset * 2))
            )

            // Inner main field
            val fieldInset = 42f
            drawRect(
                color = basePalette.first,
                topLeft = Offset(fieldInset, fieldInset),
                size = Size(width - (fieldInset * 2), height - (fieldInset * 2))
            )

            // Gold framing border stroke
            drawRect(
                color = basePalette.third,
                topLeft = Offset(fieldInset, fieldInset),
                size = Size(width - (fieldInset * 2), height - (fieldInset * 2)),
                style = Stroke(width = 3f)
            )

            // Four corners (Lachak / لچک)
            val cornerSize = 40f
            val cornerGold = basePalette.third.copy(alpha = 0.4f)
            drawCircle(cornerGold, radius = cornerSize, center = Offset(fieldInset, fieldInset))
            drawCircle(cornerGold, radius = cornerSize, center = Offset(width - fieldInset, fieldInset))
            drawCircle(cornerGold, radius = cornerSize, center = Offset(fieldInset, height - fieldInset))
            drawCircle(cornerGold, radius = cornerSize, center = Offset(width - fieldInset, height - fieldInset))

            // Central Medallion (Toranj / ترنج مرکزی)
            val center = Offset(width / 2f, height / 2f)
            val toranjRadius = (height.coerceAtMost(width) * 0.22f)

            // Outer medallion star
            drawCircle(
                color = basePalette.second,
                radius = toranjRadius,
                center = center
            )
            drawCircle(
                color = basePalette.third,
                radius = toranjRadius,
                center = center,
                style = Stroke(width = 4f)
            )
            // Inner medallion core
            drawCircle(
                color = basePalette.first,
                radius = toranjRadius * 0.55f,
                center = center
            )
            drawCircle(
                color = FarshParchment,
                radius = toranjRadius * 0.25f,
                center = center
            )
        }
    }
}
