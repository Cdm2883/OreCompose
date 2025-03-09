package vip.cdms.orecompose.layout.panorama

import androidx.compose.runtime.Composable
import vip.cdms.orecompose.style.OreModule

/**
 * A common [OreModule] for preloading default panorama image.
 *
 * Attach this module if you use default panorama image
 * could resolve some problem like panorama disappearing.
 * (Only works for Web)
 */
expect object OrePanoramaModule : OreModule {
    @Composable
    override operator fun invoke(content: @Composable () -> Unit)
}
