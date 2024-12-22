package vip.cdms.orecompose.utils

import androidx.compose.ui.graphics.Color

val Int.argb
    inline get() = Color(this)
val Long.argb
    inline get() = Color(this)

val Int.rgb
    inline get() = (0xFF000000 or this.toLong()).argb
val Long.rgb
    inline get() = (0xFF000000L or this).argb
