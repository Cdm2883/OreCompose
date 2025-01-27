package vip.cdms.orecompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import kotlin.jvm.JvmName

@Composable
inline infix fun <reified T : ProvidableCompositionLocal<R>, R> T.providesCompute(block: @Composable T.() -> R) =
    provides(block(this))  // providesComputed do not allow access value itself :(

@JvmName("providesNullableMore")
@Composable
inline infix fun <reified T> ProvidableCompositionLocal<Array<T>?>.providesMore(items: Array<T>) =
//    providesCompute { (current ?: emptyArray()) + items }
    providesCompute { current?.let { it + items } ?: items }

@Composable
inline infix fun <reified T> ProvidableCompositionLocal<Array<T>>.providesMore(items: Array<T>) =
    providesCompute { current + items }
