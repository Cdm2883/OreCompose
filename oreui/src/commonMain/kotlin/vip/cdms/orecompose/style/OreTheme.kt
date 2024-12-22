package vip.cdms.orecompose.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import vip.cdms.orecompose.components.LocalLabel
import vip.cdms.orecompose.utils.LocalFontsFallback

@Composable
fun OreTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalFontsFallback provides arrayOf(MinecraftAsciiFont.Fallback),
        LocalLabel.AutoFontsFallbackEnabled provides true,
    ) {
        MaterialTheme(
            content = content
        )
    }
}
