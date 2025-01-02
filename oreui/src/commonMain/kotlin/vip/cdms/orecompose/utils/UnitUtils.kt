@file:Suppress("NOTHING_TO_INLINE")

package vip.cdms.orecompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import vip.cdms.orecompose.effect.Pixel
import vip.cdms.orecompose.effect.Pixel.Companion.toPx

// so confusing :(

private typealias IntDevicePixel = Int
private typealias DevicePixel = Float
private typealias OrePixel = Pixel

@[Stable Composable]
inline fun IntDevicePixel.toDp(): Dp = with(LocalDensity.current) { toDp() }
@[Stable Composable]
inline fun DevicePixel.toDp(): Dp = with(LocalDensity.current) { toDp() }
@[Stable Composable]
inline fun TextUnit.toDp(): Dp = with(LocalDensity.current) { toDp() }

@[Stable Composable]
inline fun IntDevicePixel.toSp(): TextUnit = with(LocalDensity.current) { toSp() }
@[Stable Composable]
inline fun DevicePixel.toSp(): TextUnit = with(LocalDensity.current) { toSp() }
@[Stable Composable]
inline fun Dp.toSp(): TextUnit = with(LocalDensity.current) { toSp() }
@Composable
inline fun OrePixel.toSp(): TextUnit = if (isSpecified) toDp().toSp() else TextUnit.Unspecified

@[Stable Composable]
inline fun TextUnit.toDPx(): DevicePixel = with(LocalDensity.current) { toPx() }
@[Stable Composable]
inline fun Dp.toDPx(): DevicePixel = with(LocalDensity.current) { toPx() }
@Composable
inline fun OrePixel.toDPx(): DevicePixel = toDp().toDPx()

@Composable
inline fun IntDevicePixel.toPx(): OrePixel = toDp().toPx()
@Composable
inline fun DevicePixel.toPx(): OrePixel = toDp().toPx()
@Composable
inline fun TextUnit.toPx(): OrePixel = toDp().toPx()
