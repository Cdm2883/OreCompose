package vip.cdms.orecompose.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize
import vip.cdms.orecompose.style.OreColors
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

typealias FlattedPixels = List<Color>

data class PixelIcon(
    val width: Int,
    val height: Int,
    val pixels: FlattedPixels
) {
    fun index(x: Int, y: Int) = y * width + x
    operator fun get(x: Int, y: Int) = pixels[index(x, y)]
    
    fun padding(left: Int, top: Int, right: Int, bottom: Int, color: Color = Color.Transparent): PixelIcon {
        val newWidth = max(0, width + left + right)
        val newHeight = max(0, height + top + bottom)
        val newPixels = MutableList(newHeight * newWidth) { color }

        val startX = max(0, -left)
        val startY = max(0, -top)
        val endX = minOf(width, newWidth + startX)
        val endY = minOf(height, newHeight + startY)
        for (y in startY until endY)
            for (x in startX until endX) {
                val oldIndex = y * width + x
                val newIndex = (y + top) * newWidth + (x + left)
                newPixels[newIndex] = pixels[oldIndex]
            }

        return PixelIcon(newWidth, newHeight, newPixels)
    }
    fun padding(horizontal: Int, vertical: Int, color: Color = Color.Transparent) = padding(horizontal, vertical, horizontal, vertical, color)
    fun padding(all: Int, color: Color = Color.Transparent) = padding(all, all, color)
    
    fun rebuild(block: Builder.() -> Unit): PixelIcon {
        val builder = Builder()
        
        builder.width = width
        builder.height = height
        
        builder.pixels = MutableList(height) { MutableList(width) { Color.Transparent } }
        for (y in 0 until height)
            for (x in 0 until width)
                builder.pixels[y][x] = get(x, y)
        
        builder.block()
        return builder.build()
    }
    
    class Builder {
        var width = -1
        var height = -1
        var size: Int
            get() = max(width, height)
            set(value) = value.let { width = it; height = it }

        var pixels: MutableList<MutableList<Color>> = mutableListOf()
        val W = OreColors.White
        val B = OreColors.Black
        
        operator fun Color.times(repeat: Int) = MutableList(repeat) { this }
        operator fun Color.plus(color: Color) = mutableListOf(this, color)
        operator fun Color.plus(colors: MutableList<Color>) = colors.apply { add(0, this@plus) }
        operator fun Color.unaryPlus() = mutableListOf(Color.Transparent, this)
        operator fun MutableList<Color>.unaryPlus() = apply { add(0, Color.Transparent) }
        operator fun MutableList<Color>.plus(color: Int) = apply { add(color.argb) }
        operator fun MutableList<Color>.plus(color: Long) = apply { add(color.argb) }
        
        fun clear() {
            size = -1
            pixels.clear()
        }
        
        infix fun line(colors: List<Color>) {
            width = max(width, colors.size)
            val line = colors + List(max(0, width - colors.size)) { Color.Transparent }
            pixels.add(line.toMutableList())
        }
        infix fun push(colors: List<Color>) {
            colors.chunked(width).forEach { pixels.add(it.toMutableList()) }
            height = max(height, pixels.size)
        }

        fun build() = PixelIcon(width, height, pixels.flatten().run { this + List((width - size % width) % width) { Color.Transparent } })
    }
}

fun buildPixelIcon(block: PixelIcon.Builder.() -> Unit): PixelIcon {
    val builder = PixelIcon.Builder()
    builder.block()
    return builder.build()
}
fun buildPixelIcon(width: Int, height: Int = -1, block: PixelIcon.Builder.() -> FlattedPixels): PixelIcon {
    val builder = PixelIcon.Builder()
    builder.width = width
    builder.height = height
    builder push builder.block()
    return builder.build()
}
fun buildPixelIcon(size: Int, block: PixelIcon.Builder.() -> FlattedPixels) = buildPixelIcon(size, size, block)



class PixelPainter(
    val width: Int,
    val height: Int,
    val pixels: FlattedPixels,
) : Painter() {
    override val intrinsicSize = IntSize(width, height).toSize()
    fun DrawScope.drawPixels(ps: Float, offset: Offset) {
        val paint = newPixelPaint()
        val block = Size.square(ps)
        for (y in 0 until height)
            for (x in 0 until width) {
                val color = pixels[y * width + x]
                val pos = offset.offset(x * ps, y * ps)
                paint.color = color
                drawContext.canvas.drawRect(Rect(pos, block), paint)
            }
    }
    override fun DrawScope.onDraw() {
        val ps = ceil(min(size.width, size.height) / min(width, height))
        val offset = Offset(
            size.width / 2 - width * ps / 2,
            size.height / 2 - height * ps / 2,
        )
        drawPixels(ps, offset)
    }
}
val PixelIcon.painter get() = PixelPainter(width, height, pixels)
