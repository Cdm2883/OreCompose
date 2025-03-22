package vip.cdms.orecompose.utils

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.zIndex
import vip.cdms.orecompose.effect.ZIndices
import vip.cdms.orecompose.effect.outline
import vip.cdms.orecompose.style.OreColors

val LocalAccessibilityIndicatorColor = staticCompositionLocalOf { OreColors.PureWhite }

@Composable
fun Modifier.accessibilityIndicator(interactionSource: InteractionSource) = run {
    var isTab by remember { mutableStateOf(false) }
    val focused = interactionSource.collectIsFocusedAsState().value
    val hovered = interactionSource.collectIsHoveredAsState().value
    remember(hovered) { isTab = false }

    onKeyEvent {
        isTab = it.key == Key.Tab
        false
    }.run {
        if (isTab && focused && !hovered) zIndex(ZIndices.ACCESSIBILITY_INDICATOR)
            .outline(color = LocalAccessibilityIndicatorColor.current, padding = false)
        else this
    }
}
