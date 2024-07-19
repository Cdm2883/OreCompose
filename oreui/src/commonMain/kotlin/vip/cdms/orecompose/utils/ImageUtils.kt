package vip.cdms.orecompose.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap

fun mergeHorizontally(vararg bitmaps: ImageBitmap): ImageBitmap {
    require(bitmaps.isNotEmpty())
    if (bitmaps.size == 1) return bitmaps[0]
    
    val totalWidth = bitmaps.sumOf { it.width }
    val maxHeight = bitmaps.maxOf { it.height }
    val newBitmap = ImageBitmap(
        colorSpace = bitmaps[0].colorSpace,
        config = bitmaps[0].config,
        hasAlpha = bitmaps[0].hasAlpha,
        width = totalWidth,
        height = maxHeight
    )
    val newCanvas = Canvas(newBitmap)
    val newPaint = newPixelPaint()

    var currentOffsetX = 0
    for (bitmap in bitmaps) {
        val offset = Offset(currentOffsetX.toFloat(), 0f)
        newCanvas.drawImage(bitmap, offset, newPaint)
        currentOffsetX += bitmap.width
    }

    newCanvas.save()
    return newBitmap
}

enum class FlipCode { Vertical, Horizontal, Both }
fun ImageBitmap.flip(code: FlipCode): ImageBitmap {
    val pixels = toPixelMap()
    val output = ImageBitmap(width, height, config, hasAlpha, colorSpace)
    val canvas = Canvas(output)
    val paint = newPixelPaint()
    
    for (y in 0 until height) {
        for (x in 0 until width) {
            val color = pixels[x, y]
            paint.color = color
            when (code) {
                FlipCode.Vertical -> {
                    canvas.drawPixel(x.toFloat(), (height - y - 1).toFloat(), paint)
                }
                FlipCode.Horizontal -> {
                    canvas.drawPixel((width - x - 1).toFloat(), y.toFloat(), paint)
                }
                FlipCode.Both -> {
                    canvas.drawPixel((width - x - 1).toFloat(), (height - y - 1).toFloat(), paint)
                }
            }
        }
    }
    
    canvas.save()
    return output
}

fun ImageBitmap.shiftX(shiftX: Int): ImageBitmap {
    if (shiftX == 0) return this
    val output = ImageBitmap(width, height, config, hasAlpha, colorSpace)
    val canvas = Canvas(output)
    val paint = newPixelPaint()
    
    val shift = shiftX.toFloat()
    canvas.drawImage(
        this,
        Offset(shift, 0f),
        paint
    )
    canvas.drawImage(
        this,
        Offset(shift - width, 0f),
        paint
    )
    
    canvas.save()
    return output
}
