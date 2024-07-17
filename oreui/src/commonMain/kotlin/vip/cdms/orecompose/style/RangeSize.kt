package vip.cdms.orecompose.style

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.isUnspecified

data class DpRangeSize(
    val minWidth: Dp = Dp.Unspecified,
    val maxWidth: Dp = Dp.Unspecified,
    val minHeight: Dp = Dp.Unspecified,
    val maxHeight: Dp = Dp.Unspecified
) {
    val isUnspecified get() =
        minWidth.isUnspecified && maxWidth.isUnspecified && minHeight.isUnspecified && maxHeight.isUnspecified
    val isSpecified get() = !isUnspecified
    
    fun makeModifier() = Modifier then this
    
    companion object {
        val Unspecified = DpRangeSize()
        fun fixed(width: Dp, height: Dp) = DpRangeSize(width, width, height, height)
        fun fixed(size: DpSize) = fixed(size.width, size.height)
    }
}

infix fun Modifier.then(rangeSize: DpRangeSize?) =
    if (rangeSize == null || rangeSize.isUnspecified) this
    else widthIn(rangeSize.minWidth, rangeSize.maxWidth)
        .heightIn(rangeSize.minHeight, rangeSize.maxHeight)
