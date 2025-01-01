package vip.cdms.orecompose.layout.panorama

import androidx.compose.runtime.Composable
import orecompose.oreui_panorama.generated.resources.Res
import orecompose.oreui_panorama.generated.resources.panorama_default
import vip.cdms.orecompose.style.OreModule
import vip.cdms.orecompose.utils.PreloadResources
import vip.cdms.orecompose.utils.ResourceUrl.Companion.png
import vip.cdms.orecompose.utils.ResourceUrl.Companion.resourcesUrl

actual object OrePanoramaModule : OreModule {
    @Composable
    actual override operator fun invoke(content: @Composable () -> Unit) {
        val packaging = "orecompose.oreui_panorama.generated.resources"
        PreloadResources(
            Res.drawable.resourcesUrl(packaging) { ::panorama_default.png },
        ) {
            content()
        }
    }
}
