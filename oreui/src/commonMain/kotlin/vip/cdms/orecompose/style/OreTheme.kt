package vip.cdms.orecompose.style

import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.Dp
import vip.cdms.orecompose.effect.LocalSounds
import vip.cdms.orecompose.effect.Sounds
import vip.cdms.orecompose.utils.LocalMcFontProcessor
import vip.cdms.orecompose.utils.McFontProcessor

@Composable
fun OreTheme(
    mcFontProcessor: McFontProcessor? = LocalMcFontProcessor.current,
    sounds: Sounds = LocalSounds.current,
    pixelSize: Dp = LocalPixelSize.current,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalMcFontProcessor provides mcFontProcessor,
        LocalSounds provides sounds,
        LocalPixelSize provides pixelSize,
        
        LocalContentColor provides OreColors.White,
        
        content = content
    )
}
