package vip.cdms.orecompose.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import orecompose.oreui_panorama.generated.resources.Res
import orecompose.oreui_panorama.generated.resources.panorama
import org.jetbrains.compose.resources.imageResource
import vip.cdms.orecompose.utils.*
import kotlin.math.*

@RequiresOptIn("This API is experimental and is likely to change in the future.")
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalPanoramaApi

val PanoramaCache = mutableMapOf<String, ImageBitmap>()

/**
 * ```python
 * import py360convert
 * import cv2
 *
 * resource_packs = "<PATH_TO_YOURS>/textures/ui/"
 * out_path = "<PATH_TO_YOURS>/panorama.png"
 * quality_para = 0
 * target_size_w = 3000
 *
 * cube_dice0 = cv2.imread(f"{resource_packs}panorama_0.png")
 * cube_dice1 = cv2.imread(f"{resource_packs}panorama_1.png")
 * cube_dice2 = cv2.imread(f"{resource_packs}panorama_2.png")
 * cube_dice3 = cv2.imread(f"{resource_packs}panorama_3.png")
 * cube_dice4 = cv2.imread(f"{resource_packs}panorama_4.png")
 * cube_dice5 = cv2.imread(f"{resource_packs}panorama_5.png")
 *
 * target_size_w = int(target_size_w)
 * target_size_h = int(target_size_w // 2)
 * quality_map = {
 *     0: 40,
 *     1: 60,
 *     2: 90,
 *     3: 100
 * }
 * quality_save = quality_map.get(quality_para, 70)
 * cube_dice1 = cv2.flip(cube_dice1, 1)
 * cube_dice2 = cv2.flip(cube_dice2, 1)
 * cube_dice4 = cv2.flip(cube_dice4, 0)
 *
 * res = py360convert.c2e(
 *     [cube_dice0, cube_dice1, cube_dice2, cube_dice3, cube_dice4, cube_dice5],
 *     target_size_h, target_size_w, cube_format='list'
 * )
 * cv2.imwrite(out_path, res, [int(cv2.IMWRITE_JPEG_QUALITY), quality_save])
 * print("Panorama image saved at: " + out_path)
 * ```
 */
@ExperimentalPanoramaApi
@Composable
expect fun PanoramaViewer(
    equirectangular: ImageBitmap = imageResource(Res.drawable.panorama),
    modifier: Modifier = Modifier.fillMaxSize().background(0xff161616.argb),
    mask: Modifier? = Modifier.fillMaxSize().background(0x01000000.argb),
)

@ExperimentalPanoramaApi  // TODO 不同平台使用不同方案以优化性能 (web -> three.js ...)
@Composable
fun PanoramaViewer0(
    equirectangular: ImageBitmap = imageResource(Res.drawable.panorama),
    yaw: Float = 0f,
    pitch: Float = 0f,
    fov: Float = 90f / 180f * PI.toFloat(),
    width: Int = 1500,
    height: Int = 750,
    cache: MutableMap<String, ImageBitmap> = PanoramaCache,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    modifier: Modifier = Modifier.fillMaxSize().background(0xff161616.argb),
    mask: Modifier? = Modifier.fillMaxSize().background(0x01000000.argb),
) {
    var perspectiveImage by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(equirectangular, yaw, pitch, fov, width, height) {
        withContext(Dispatchers.Default) {
            perspectiveImage = viewPanorama(equirectangular, yaw, pitch, fov, width, height, cache)
        }
    }
    Box(modifier) {
        perspectiveImage?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                filterQuality = filterQuality
            )
        }
        mask?.let { Spacer(it) }
    }
}
@ExperimentalPanoramaApi
fun viewPanorama(
    equirectangular: ImageBitmap,
    yaw: Float,
    pitch: Float,
    fov: Float,
    width: Int,
    height: Int,
    cache: MutableMap<String, ImageBitmap>,
): ImageBitmap {
    val hash = "${equirectangular.hashCode()},${yaw},${pitch},${fov},${width},${height}"
    if (cache.contains(hash)) return cache[hash]!!

    val equirectangularPixelMap = equirectangular.toPixelMap()
    val perspectiveBitmap = ImageBitmap(width, height)
    val canvas = Canvas(perspectiveBitmap)
    val paint = newPixelPaint()

    val halfWidth = width / 2
    val halfHeight = height / 2
    val focalLength = halfWidth / tan(fov / 2)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val dx = (x - halfWidth).toFloat()
            val dy = (y - halfHeight).toFloat()

            val theta = atan2(dx, focalLength) + yaw
            val phi = atan(dy / sqrt(dx * dx + focalLength * focalLength)) + pitch

            val u = (theta / (2 * PI)).toFloat()
            val v = (phi / PI + 0.5).toFloat()

            val srcX = (u * equirectangular.width).toInt().coerceIn(0, equirectangular.width - 1)
            val srcY = (v * equirectangular.height).toInt().coerceIn(0, equirectangular.height - 1)

            val color = equirectangularPixelMap[srcX, srcY]

            paint.color = color
            canvas.drawPixel(x.toFloat(), y.toFloat(), paint)
        }
    }

    canvas.save()
    cache[hash] = perspectiveBitmap
    return perspectiveBitmap
}

/**
 *      .____.
 *      |top |
 * .____|____|____.____.
 * |left|frt |rght|back|
 * |____|____|____|____|
 *      |bot |
 *      |____|
 */
@ExperimentalPanoramaApi
fun convertCubemapToEquirectangular(
    front: ImageBitmap,
    back: ImageBitmap,
    left: ImageBitmap,
    right: ImageBitmap,
    top: ImageBitmap,
    bottom: ImageBitmap,
    width: Int,
    height: Int
): ImageBitmap {
    val output = ImageBitmap(width, height)
    val canvas = Canvas(output)
    val paint = newPixelPaint()

    val frontData = front.toPixelMap()
    val backData = back.toPixelMap()
    val leftData = left.toPixelMap()
    val rightData = right.toPixelMap()
    val topData = top.toPixelMap()
    val bottomData = bottom.toPixelMap()

    fun sampleCubemapFace(data: PixelMap, faceSize: Int, u: Float, v: Float): Color {
        val x = ((u + 1) / 2 * (faceSize - 1)).toInt().coerceIn(0, faceSize - 1)
        val y = ((v + 1) / 2 * (faceSize - 1)).toInt().coerceIn(0, faceSize - 1)
        return data[x, y]
    }

    fun equirectangularToCubemap(
        theta: Float,
        phi: Float,
        faceSize: Int
    ): Color {
        val x = cos(phi) * cos(theta)
        val y = sin(phi)
        val z = cos(phi) * sin(theta)

        return when {
            abs(x) >= abs(y) && abs(x) >= abs(z) -> if (x > 0) sampleCubemapFace(rightData, faceSize, -z / x, y / x)
            else sampleCubemapFace(leftData, faceSize, z / x, y / x)
            abs(y) >= abs(x) && abs(y) >= abs(z) -> if (y > 0) sampleCubemapFace(topData, faceSize, x / y, -z / y)
            else sampleCubemapFace(bottomData, faceSize, x / y, z / y)
            else -> if (z > 0) sampleCubemapFace(frontData, faceSize, x / z, y / z)
            else sampleCubemapFace(backData, faceSize, -x / z, y / z)
        }
    }

    for (y in 0 until height) {
        val phi = PI * (y.toFloat() / height - 0.5f)
        for (x in 0 until width) {
            val theta = 2.0 * PI * (x.toFloat() / width - 0.5f)
            val color = equirectangularToCubemap(theta.toFloat(), phi.toFloat(), front.width)
            paint.color = color
            canvas.drawPixel(x.toFloat(), y.toFloat(), paint)
        }
    }

    canvas.save()
    return output
}
@ExperimentalPanoramaApi
fun convertMcOrderPanoramaToEquirectangular(
    panorama0: ImageBitmap,
    panorama1: ImageBitmap,
    panorama2: ImageBitmap,
    panorama3: ImageBitmap,
    panorama4: ImageBitmap,
    panorama5: ImageBitmap,
    width: Int,
    height: Int
) = convertCubemapToEquirectangular(
    front  = panorama1.flip(FlipCode.Horizontal),
    back   = panorama3.flip(FlipCode.Vertical),
    left   = panorama2.flip(FlipCode.Vertical),
    right  = panorama0.flip(FlipCode.Horizontal),
    top    = panorama5.flip(FlipCode.Horizontal),  // offset wrong
    bottom = panorama4.flip(FlipCode.Horizontal),  // offset wrong
    width,
    height
)
