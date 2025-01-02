package vip.cdms.orecompose.style

import androidx.compose.runtime.Composable
import orecompose.oreui.generated.resources.Minecraft
import orecompose.oreui.generated.resources.Res
import vip.cdms.orecompose.utils.PreloadResources
import vip.cdms.orecompose.utils.ResourcePreloader
import vip.cdms.orecompose.utils.ResourceUrl.Companion.resourceUrl
import vip.cdms.orecompose.utils.ResourceUrl.Companion.ttf

@Composable
internal actual fun PlatformOreTheme(content: @Composable () -> Unit) {
    val packaging = "orecompose.oreui.generated.resources"
    PreloadResources(
        Res.font.resourceUrl(packaging) { ::Minecraft.ttf },
    ) {
        ResourcePreloader {
            content()
        }
    }
}
