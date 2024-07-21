package vip.cdms.orecompose.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

private val Int.pxSp @Composable get() = with(LocalDensity.current) { px.toSp() }
object OreTexts {
    val Content @Composable get() = 9.pxSp
    val Summary @Composable get() = 8.pxSp
}
