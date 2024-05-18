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
    
    var height by remember { mutableFloatStateOf(0f) }
    val thumbHeight = height.toDp() * (state.viewportSize.toFloat() / state.maxValue)
    val thumbOffset = (height.toDp() - thumbHeight) * (state.value.toFloat() / state.maxValue)
    
    val draggableState = rememberDraggableState {
        GlobalScope.launch { state.scrollBy(it * 2 * ps) }
    }
    
    Box(modifier) {
        Spacer(
            Modifier
                .width((1.5f).px)
                .fillMaxHeight()
                .align(Alignment.Center)
                .background(palette.track)
                .onGloballyPositioned { height = it.boundsInParent().height }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(thumbHeight)
                .offset(y = thumbOffset)
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
