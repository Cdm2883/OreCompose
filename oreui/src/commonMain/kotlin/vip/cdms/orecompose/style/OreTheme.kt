package vip.cdms.orecompose.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import vip.cdms.orecompose.components.LocalLabel
import vip.cdms.orecompose.utils.LocalFontsFallback

@Composable
fun OreTheme(
    fallbackMcFont: Boolean = true,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(*mutableListOf<ProvidedValue<*>>().apply {
        if (fallbackMcFont) this += arrayOf(
            LocalFontsFallback provides arrayOf(MinecraftAsciiFont.Fallback),
            LocalLabel.AutoFontsFallbackEnabled provides true,
        )
    }.toTypedArray()) {
        MaterialTheme(
            content = content
        )
    }
}
