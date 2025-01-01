package vip.cdms.orecompose.layout.panorama

import androidx.compose.runtime.Composable
import vip.cdms.orecompose.style.OreModule

actual object OrePanoramaModule : OreModule {
    @Composable
    actual override operator fun invoke(content: @Composable () -> Unit) {
        content()
    }
}
