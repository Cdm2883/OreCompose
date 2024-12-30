package vip.cdms.orecompose.layout.panorama

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import orecompose.oreui_panorama.generated.resources.Res
import orecompose.oreui_panorama.generated.resources.panorama_default
import org.jetbrains.compose.resources.imageResource
import vip.cdms.orecompose.utils.argb

object PanoramaDefaults {
    val Equirectangular = Res.drawable.panorama_default
    val Modifier = androidx.compose.ui.Modifier
        .fillMaxSize()
        .foreground(0x30000000.argb)
}

fun Modifier.foreground(color: Color) = drawWithContent {
    drawContent()
    drawRect(color)
}

@Composable
expect fun Panorama(
    equirectangular: ImageBitmap = imageResource(PanoramaDefaults.Equirectangular),
    modifier: Modifier = PanoramaDefaults.Modifier,
)
