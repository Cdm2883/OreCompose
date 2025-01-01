package vip.cdms.orecompose.layout.panorama

import androidx.compose.runtime.Composable
import vip.cdms.orecompose.style.OreModule

expect object OrePanoramaModule : OreModule {
    @Composable
    override operator fun invoke(content: @Composable () -> Unit)
}
