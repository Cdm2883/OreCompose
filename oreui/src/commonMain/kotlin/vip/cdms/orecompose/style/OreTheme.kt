package vip.cdms.orecompose.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.Dp
import vip.cdms.orecompose.components.LocalLabel
import vip.cdms.orecompose.effect.LocalPixelSize
import vip.cdms.orecompose.utils.ComposeNester
import vip.cdms.orecompose.utils.LocalFontsFallback
import vip.cdms.orecompose.utils.providesMore

typealias OreModule = ComposeNester

@Composable
fun OreTheme(
    pixelSize: Dp? = null,
    fallbackMcFonts: Boolean = true,
    modules: Array<OreModule>? = null,
    content: @Composable () -> Unit
) = OreModule.Apply(modules, content) {
    val locals = buildList {
        if (pixelSize != null) this += LocalPixelSize provides pixelSize
        if (fallbackMcFonts) {
            this += LocalFontsFallback providesMore arrayOf(McFonts.Ascii.Fallback)
            this += LocalLabel.AutoFontsFallbackEnabled provides true
        }
    }

    CompositionLocalProvider(*locals.toTypedArray()) {
        PlatformOreTheme { MaterialTheme(content = it) }
    }
}

@Composable
internal expect fun PlatformOreTheme(content: @Composable () -> Unit)
