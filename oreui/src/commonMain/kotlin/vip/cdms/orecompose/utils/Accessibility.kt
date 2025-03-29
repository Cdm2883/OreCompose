package vip.cdms.orecompose.utils

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.zIndex
import vip.cdms.orecompose.effect.LocalOutlineWidth
import vip.cdms.orecompose.style.ZIndices
import vip.cdms.orecompose.effect.accInnerOutlinesWidth
import vip.cdms.orecompose.effect.outline
import vip.cdms.orecompose.style.OreColors

val LocalAccessibilityIndicatorColor = staticCompositionLocalOf<Color?> { OreColors.PureWhite }

fun Modifier.accessibilityIndicator(interactionSource: InteractionSource) = accInnerOutlinesWidth().let { padding ->
    // WARNING: using `composed` will cause all the padding nodes after this node
    // calculated a wrong padding (ignore this). Because foreach modifier chain in this case
    // will be a ComposedModifier rather than OutlineElement. We cannot get the element inside
    // before it actually drawing.
    //
    // But for accessibility indicator, maybe it's not a big problem...
    // Because the indicator usually be the last element of modifiers.
    composed {
        val color = LocalAccessibilityIndicatorColor.current ?: return@composed this

        var isTab by remember { mutableStateOf(false) }
        val focused = interactionSource.collectIsFocusedAsState().value
        val hovered = interactionSource.collectIsHoveredAsState().value
        remember(hovered) { isTab = false }

        onKeyEvent {
            isTab = it.key == Key.Tab
            false
        }.run {
            if (isTab && focused && !hovered) zIndex(ZIndices.ACCESSIBILITY_INDICATOR)
                .outline(color, LocalOutlineWidth.current.toDp(), padding)
            else this
        }
    }
}
