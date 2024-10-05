package vip.cdms.orecompose.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import vip.cdms.orecompose.utils.warpRect

@Composable
@ExperimentalPanoramaApi
actual fun PanoramaViewer(
    equirectangular: ImageBitmap,
    modifier: Modifier,
    mask: Modifier?
) {
    var boundingBox by remember { mutableStateOf(Rect.Zero) }

    DisposableEffect(equirectangular, boundingBox) {
        onDispose {
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
