package vip.cdms.orecompose.layout.panorama

import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.imageResource
import vip.cdms.orecompose.utils.Platform
import vip.cdms.orecompose.utils.RuntimePlatform
import vip.cdms.orecompose.utils.drawPoint
import vip.cdms.orecompose.utils.pixelPaint
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds

/**
 * Panorama fallback with CPU calculation directly.
 *
 * @see Panorama
 */
@Composable
fun PanoramaSoft(
    equirectangular: ImageBitmap = imageResource(PanoramaDefaults.Equirectangular),
    modifier: Modifier = PanoramaDefaults.Modifier,
    rotating: Boolean = true,
    period: Duration = if (RuntimePlatform is Platform.Web) 100.milliseconds else 1.nanoseconds,
    width: Int = 1500,  // 50
    height: Int = 750,  // 25
) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }

    val origin = remember { 35f * PI.toFloat() / 180 } // magic number
    var direction by remember { mutableIntStateOf(1) }
    var yaw by remember { mutableFloatStateOf(origin) }
    val deg1 = remember { PI.toFloat() / 180 / 5 }
    val deg360 = remember { 2 * PI.toFloat() - origin }

    LaunchedEffect(equirectangular) {
        val pixels = equirectangular.toPixelMap()
        val paint = pixelPaint()
        withContext(Dispatchers.Unconfined) {
            do {
                val bitmap = ImageBitmap(width, height)
                val canvas = Canvas(bitmap)
                canvas.viewPanoramaSoft(
                    pixels,
                    paint,
                    yaw,
                    pitch = 0f,
                    fov = 90f * PI.toFloat() / 180f,
                    width,
                    height,
                )
                canvas.save()
                image = bitmap

                val next = yaw + direction * deg1
                if (next > deg360 || next < origin) direction *= -1
                else yaw = next
                delay(period)
            } while (rotating && isActive)
        }
    }

    if (image != null) Image(
        bitmap = image!!,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

fun Canvas.viewPanoramaSoft(
    equirectangular: PixelMap,
    paint: Paint,
    yaw: Float,
    pitch: Float,
    fov: Float,
    width: Int,
    height: Int,
) {
    val halfWidth = width / 2
    val halfHeight = height / 2
    val focalLength = halfWidth / fastTan(fov / 2)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val dx = x - halfWidth.toFloat()
            val dy = y - halfHeight.toFloat()

            val theta = fastAtan2(dx, focalLength) + yaw
            val phi = fastAtan(dy / sqrt(dx * dx + focalLength * focalLength)) + pitch

            val u = theta / (2 * PI)
            val v = phi / PI + 0.5

            val srcX = (u * equirectangular.width).toInt().coerceIn(0, equirectangular.width - 1)
            val srcY = (v * equirectangular.height).toInt().coerceIn(0, equirectangular.height - 1)
            val color = equirectangular[srcX, srcY]

            drawPoint(x.toFloat(), y.toFloat(), color, paint)
        }
    }
}
