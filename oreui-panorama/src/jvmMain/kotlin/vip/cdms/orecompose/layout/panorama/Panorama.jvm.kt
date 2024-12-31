package vip.cdms.orecompose.layout.panorama

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun Panorama(equirectangular: ImageBitmap, modifier: Modifier, rotating: Boolean) {
    PanoramaSoft(equirectangular, modifier, rotating)
    // TODO: use SwingPanel for 3d lib
}
