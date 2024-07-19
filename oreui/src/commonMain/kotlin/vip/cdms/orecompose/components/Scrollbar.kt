package vip.cdms.orecompose.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vip.cdms.orecompose.effect.outline
import vip.cdms.orecompose.style.*
import vip.cdms.orecompose.utils.argb
import vip.cdms.orecompose.utils.offset
import vip.cdms.orecompose.utils.toDp
import vip.cdms.orecompose.utils.toPx

data class ScrollbarPalette(
    val thumb: ButtonStyles = OreButtonStyles.Common.normal.copy(outline = OreColors.Black).band(),
    val thumbShadow: Color = 0x55000000.argb,
    val track: Color = 0xff58585a.argb
)
object ScrollbarDefaults {
    val VerticalModifier @Composable get() = Modifier
        .width(5.px)
        .fillMaxHeight()
        .padding(vertical = 3.px)
        .padding(end = 2.px)
    val Palette = ScrollbarPalette()
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun VerticalScrollbar(
    modifier: Modifier = ScrollbarDefaults.VerticalModifier,
    state: ScrollState,
    palette: ScrollbarPalette = ScrollbarDefaults.Palette
) {
    val ps = LocalPixelSize.current.toPx()
    
    var viewHeight by remember { mutableFloatStateOf(state.viewportSize.toFloat().takeIf { it != 0f } ?: Float.MAX_VALUE) }
    val contentHeight = state.maxValue + viewHeight
    val shouldShow = viewHeight < contentHeight

    val scrollbarHeight = if (shouldShow) (viewHeight * (viewHeight / contentHeight)).coerceAtLeast(10.dp.toPx()) else Float.NaN
    val variableZone = if (shouldShow) viewHeight - scrollbarHeight else Float.NaN
    val scrollbarOffsetY = if (shouldShow) (state.value.toFloat() / state.maxValue) * variableZone else Float.NaN
    
    val draggableState = rememberDraggableState {
        GlobalScope.launch { state.scrollBy(it) }
    }

    Box(modifier) {
        Spacer(
            Modifier
                .width((1.5f).px)
                .fillMaxHeight()
                .align(Alignment.Center)
                .onGloballyPositioned { viewHeight = it.boundsInParent().height }
                .run { if (shouldShow) background(palette.track) else this }
        )
        if (shouldShow) Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(scrollbarHeight.toDp())
                .offset(y = scrollbarOffsetY.toDp())
                .outline()
                .drawBehind {
                    drawRect(palette.thumbShadow, Offset(-ps, size.height + ps), size.offset(width = 2 * ps).copy(height = ps))
                }
                .draggable(
                    draggableState,
                    Orientation.Vertical
                ),
            pressable = false,
            styles = palette.thumb,
            rangeSize = DpRangeSize.Unspecified
        )
    }
}
