package vip.cdms.orecompose.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.unit.Dp
import vip.cdms.orecompose.components.LocalLabel
import vip.cdms.orecompose.effect.LocalPixelSize
import vip.cdms.orecompose.utils.ComposeNester
import vip.cdms.orecompose.utils.LocalFontsFallback

typealias OreModule = ComposeNester

@Composable
fun OreTheme(
    pixelSize: Dp? = null,
    fallbackMcFonts: Boolean = true,
    modules: Array<OreModule>? = null,
    content: @Composable () -> Unit
) {
    OreModule.Apply(modules, OreModule.wrap {
        CompositionLocalProvider(*mutableListOf<ProvidedValue<*>>().apply {
            if (pixelSize != null) this += LocalPixelSize provides pixelSize
            if (fallbackMcFonts) this += arrayOf(
                LocalFontsFallback provides arrayOf(McFonts.Ascii.Fallback),
                LocalLabel.AutoFontsFallbackEnabled provides true,
            )
        }.toTypedArray()) {
            PlatformOreTheme {
                MaterialTheme(
                    content = it
                )
            }
        }
    }, content)
}

@Composable
internal expect fun PlatformOreTheme(content: @Composable () -> Unit)
