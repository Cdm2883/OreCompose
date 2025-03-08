package vip.cdms.orecompose.layout.panorama

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.RuntimeShaderBuilder
import vip.cdms.orecompose.utils.Platform
import vip.cdms.orecompose.utils.RuntimePlatform
import kotlin.math.PI
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Panorama implement with Skiko.
 *
 * @see Panorama
 */
@Composable
fun PanoramaSkiko(
    equirectangular: ImageBitmap,
    modifier: Modifier,
    rotating: Boolean,
    period: Duration = if (RuntimePlatform is Platform.Web) 100.milliseconds else 1.milliseconds,
    yawDelta: Float = if (RuntimePlatform is Platform.Web) .001f else .0003f,
//    fpsCounter: FPSCounter? = null,
) {
//    val skiaImage = remember(equirectangular) { equirectangular.toAwtImage().toImage() }  // JVM ONLY
    val skiaImage = remember(equirectangular) { /*Image.makeFromBitmap(*/equirectangular.asSkiaBitmap()/*)*/ }
    val imageShader = remember(skiaImage) { skiaImage.makeShader() }

    val effect = remember { RuntimeEffect.makeForShader(Sksl) }
    val shaderBuilder = remember { RuntimeShaderBuilder(effect) }

    val paint = remember { Paint()/*.apply { isAntiAlias = true }*/ }

    var yaw by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(rotating) {
        withContext(Dispatchers.Unconfined) {
            while (rotating && isActive) {
                yaw += yawDelta
                delay(period)
            }
        }
    }

    Canvas(modifier = modifier) {
        drawIntoCanvas { canvas ->
            val skiaCanvas = canvas.nativeCanvas
            val viewportSize = this@Canvas.size

            shaderBuilder.uniform("iResolution", viewportSize.width, viewportSize.height)
            shaderBuilder.child("panorama", imageShader)
            shaderBuilder.uniform("panoramaSize", skiaImage.width.toFloat(), skiaImage.height.toFloat())
            shaderBuilder.uniform("yaw", yaw)
            shaderBuilder.uniform("pitch", 0f)
            shaderBuilder.uniform("fov", (130 * PI / 180).toFloat())

            paint.shader = shaderBuilder.makeShader()
            skiaCanvas.drawRect(Rect.makeXYWH(0f, 0f, viewportSize.width, viewportSize.height), paint)

//            fpsCounter?.tick()
//            if (fpsCounter != null) ParagraphBuilder(
//                ParagraphStyle(),
//                FontCollection().setDefaultFontManager(FontMgr.default)
//            )
//                .pushStyle(TextStyle().setColor(Color.RED).setFontSize(24f))
//                .addText("FPS: ${fpsCounter.average}")
//                .popStyle()
//                .build()
//                .layout(Float.POSITIVE_INFINITY)
//                .paint(skiaCanvas, 0f, 0f)
        }
    }
}
