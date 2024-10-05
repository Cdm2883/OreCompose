package vip.cdms.orecompose.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import kotlinx.browser.document
import org.w3c.dom.HTMLCanvasElement
import vip.cdms.orecompose.external.threejs.cameras.PerspectiveCamera
import vip.cdms.orecompose.external.threejs.geometries.SphereGeometry
import vip.cdms.orecompose.external.threejs.loaders.TextureLoader
import vip.cdms.orecompose.external.threejs.scenes.Scene
import vip.cdms.orecompose.utils.warpRect

@Composable
@ExperimentalPanoramaApi
actual fun PanoramaViewer(
    equirectangular: ImageBitmap,
    modifier: Modifier,
    mask: Modifier?
) {
    var boundingBox by remember { mutableStateOf(Rect.Zero) }
    var canvas by remember { mutableStateOf<HTMLCanvasElement?>(null) }

    equirectangular.asSkiaBitmap()

    DisposableEffect(equirectangular, boundingBox) {
        val camera = PerspectiveCamera(75, boundingBox.width / boundingBox.height.toDouble(), 1, 1100)
        
        val scene = Scene()
        
        val geometry = SphereGeometry(500, 60, 40)
        geometry.scale(-1, 1, 1)

        val texture = TextureLoader().load("")
//        texture.colorSpace = SRGBColorSpace
//        val material = MeshBasicMaterial({ map: texture })
        
        

        canvas = document.createElement("canvas") as HTMLCanvasElement



        onDispose {
            canvas?.remove()
        }
    }

    Box(modifier) {
        Canvas(Modifier
            .fillMaxSize()
            .onGloballyPositioned { boundingBox = it.warpRect { positionInRoot() } }
        ) {
        }
        mask?.let { Spacer(it) }
    }
}
