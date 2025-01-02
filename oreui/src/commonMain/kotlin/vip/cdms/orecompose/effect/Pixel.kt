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

/**
 * Dimension value that is used to ensure that
 * the relative size of each place is strictly consistent.
 *
 * Just like the real pixel means, but it can be scaled.
 */
@Immutable
@JvmInline
value class Pixel(val value: Float) : Comparable<Pixel> {
    //
    // Maybe it should be a single fixed multiple instead of
    // a multiple of [LocalPixelSize] which can only be got in [Composable] scope
    // (just like the contagiousness of coroutines & js async)?
    //
    // The [Density] is already a modifiable scale value,
    // is it necessary to add one more factor that is more difficult to use?
    //
    // If we don't use [LocalPixelSize], it's easier to
    // use in a draw function that only gets [Density] without additional wrapping.
    //
    // But now there is still a chance to modify it thanks to the excellent program decoupling design!
    // Save that for later when more related issues arise.
    //
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
