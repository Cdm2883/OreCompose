package vip.cdms.orecompose.layout.panorama

import android.app.ActivityManager
import android.content.Context
import android.opengl.Matrix
import android.widget.FrameLayout
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.zzt.panorama.cg.GLProducerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

@Composable
actual fun Panorama(equirectangular: ImageBitmap, modifier: Modifier, rotating: Boolean) {
    if (
        (LocalContext.current
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo.reqGlEsVersion >= 0x20000
    ) {  // support OpenGLES 2.0
        var view by remember { mutableStateOf<GlPanoramaView?>(null) }
        AndroidView(
            factory = { context ->
                GlPanoramaView(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    onGLThreadReady = {
                        glThread!!.setRenderMode(if (rotating) GLProducerThread.RENDERMODE_CONTINUOUSLY else GLProducerThread.RENDERMODE_WHEN_DIRTY)
//                        renderer.reCenter()
                    }
                    bitmap = equirectangular.asAndroidBitmap()
                }.also { view = it }
            },
            modifier,
        )
        if (rotating && view != null) LaunchedEffect(view) {
            withContext(Dispatchers.Unconfined) {
                while (isActive) {
                    Matrix.rotateM(view!!.rendererBiasMatrix, 0, .1f, 0f, 1f, 0f)
                    delay(30.milliseconds)
                }
            }
        }
    } else PanoramaSoft(equirectangular, modifier, rotating)  // fallback to software rendering
}
