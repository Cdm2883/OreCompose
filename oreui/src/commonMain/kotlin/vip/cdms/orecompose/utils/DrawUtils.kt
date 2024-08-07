package vip.cdms.orecompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toPx() = with(LocalDensity.current) { toPx() }
@Composable
fun Int.toDp() = with(LocalDensity.current) { toDp() }
@Composable
fun Float.toDp() = with(LocalDensity.current) { toDp() }
@Composable
fun Dp.toSp() = with(LocalDensity.current) { toSp() }
@Composable
fun TextUnit.toDp() = with(LocalDensity.current) { toDp() }

fun Size.Companion.square(width: Float) = Size(width, width)
fun Size.offset(width: Float = 0f, height: Float = 0f) = copy(this.width + width, this.height + height)

fun Offset.Companion.zero(x: Float = 0f, y: Float = 0f) = Offset(x, y)
fun Offset.offset(x: Float = 0f, y: Float = 0f) = copy(this.x + x, this.y + y)

fun newPixelPaint() = Paint().apply { isAntiAlias = false }
fun Canvas.drawPixel(x: Float, y: Float, paint: Paint) {
    val offset = Offset(x, y)
    val size = Size.square(1f)
    val rect = Rect(offset, size)
    drawRect(rect, paint)
}
