package vip.cdms.orecompose.utils

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PointMode

fun pixelPaint() = Paint().apply {
    isAntiAlias = false
}

@Suppress("NOTHING_TO_INLINE")
inline fun Canvas.drawPoint(x: Float, y: Float, color: Color, paint: Paint = pixelPaint()) {
    paint.color = color
    drawRawPoints(PointMode.Points, floatArrayOf(x, y), paint)
}
