package vip.cdms.orecompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx() = with(LocalDensity.current) { toPx() }

fun Size.Companion.square(width: Float) = Size(width, width)
fun Size.offset(width: Float = 0f, height: Float = 0f) = copy(this.width + width, this.height + height)

fun Offset.Companion.zero(x: Float = 0f, y: Float = 0f) = Offset(x, y)
