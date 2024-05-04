package vip.cdms.orecompose.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

val DefaultPixelSize = 2.dp
val LocalPixelSize = staticCompositionLocalOf { DefaultPixelSize }

val Int.px
    @Composable
    get() = LocalPixelSize.current * this
val Float.px
    @Composable
    get() = LocalPixelSize.current * this
