package vip.cdms.orecompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal

@Composable
infix fun <T> ProvidableCompositionLocal<T>.edit(block: @Composable T.() -> T) = provides(block(current))
