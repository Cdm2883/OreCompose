package vip.cdms.orecompose.utils

import androidx.compose.runtime.ProvidableCompositionLocal

inline infix fun <reified T> ProvidableCompositionLocal<Array<T>?>.providesMore(items: Array<T>) =
//    this providesComputed { (currentValue ?: emptyArray()) + items }
    this providesComputed { currentValue?.let { it + items } ?: items }

inline infix fun <reified T> ProvidableCompositionLocal<Array<T>>.providesMore(items: Array<T>) =
    this providesComputed { currentValue + items }
