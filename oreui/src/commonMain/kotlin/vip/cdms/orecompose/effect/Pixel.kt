@file:Suppress("NOTHING_TO_INLINE")

package vip.cdms.orecompose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.times
import kotlin.jvm.JvmInline

val DefaultPixelSize = 2.dp
val LocalPixelSize = staticCompositionLocalOf { DefaultPixelSize }

typealias Px = Pixel

@Immutable
@JvmInline
value class Pixel(val value: Float) : Comparable<Pixel> {
    @Composable inline fun toDp() = if (isSpecified) value * LocalPixelSize.current else Dp.Unspecified

    @Stable inline val isSpecified get() = !value.isNaN()
    @Stable inline val isUnspecified get() = value.isNaN()

    @Stable inline operator fun plus(other: Pixel) = Pixel(value + other.value)
    @Stable inline operator fun minus(other: Pixel) = Pixel(value - other.value)
    @Stable inline operator fun unaryMinus() = Pixel(-value)
    @Stable inline operator fun times(other: Int) = Pixel(value * other)
    @Stable inline operator fun times(other: Float) = Pixel(value * other)
    @Stable inline operator fun div(other: Int) = Pixel(value / other)
    @Stable inline operator fun div(other: Float) = Pixel(value / other)
    @Stable inline operator fun div(other: Pixel) = value / other.value
    @Stable override fun compareTo(other: Pixel) = value.compareTo(other.value)
    @Stable override fun toString() = if (isUnspecified) "Pixel.Unspecified" else "$value.px"

    companion object {
        @Composable inline fun Dp.toPx() = if (isSpecified) Pixel(this / LocalPixelSize.current) else Unspecified

        @Stable val Zero = Pixel(0f)
        @Stable val Unspecified = Pixel(Float.NaN)
    }
}

@Stable inline val Int.px get() = Pixel(toFloat())
@Stable inline val Double.px get() = Pixel(toFloat())
@Stable inline val Float.px get() = Pixel(this)

@Stable inline operator fun Int.times(other: Pixel) = Pixel(this * other.value)
@Stable inline operator fun Float.times(other: Pixel) = Pixel(this * other.value)
@Stable inline operator fun Double.times(other: Pixel) = Pixel(toFloat() * other.value)
