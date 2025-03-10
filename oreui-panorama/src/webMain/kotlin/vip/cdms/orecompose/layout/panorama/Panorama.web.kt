package vip.cdms.orecompose.layout.panorama

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun Panorama(equirectangular: ImageBitmap, modifier: Modifier, rotating: Boolean) {
    PanoramaSkiko(equirectangular, modifier, rotating)
}
