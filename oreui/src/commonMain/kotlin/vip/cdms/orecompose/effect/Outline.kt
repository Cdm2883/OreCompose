package vip.cdms.orecompose.effect

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import vip.cdms.orecompose.style.OreColors

val LocalOutlineColor = staticCompositionLocalOf { OreColors.Outline }
val LocalOutlineWidth = staticCompositionLocalOf { 1.px }

@Composable
fun Modifier.outline(
    color: Color = LocalOutlineColor.current,
    width: Px = LocalOutlineWidth.current,
    padding: Boolean = true
) = outline(
    color,
    width.toDp(),
    padding
)

fun Modifier.outline(color: Color, width: Dp, padding: Boolean = true) =
    if (padding) padding(width).outline(color, width, Dp.Unspecified)
    else outline(color, width, accInnerOutlinesWidth())

fun Modifier.outline(color: Color, width: Dp, padding: Dp) =
    if (width.value > 0) this then OutlineElement(color, width, padding) else this

fun Modifier.hasOutlined() = any { it is OutlineElement }

internal fun Modifier.accInnerOutlinesWidth() = foldIn(0.dp) { acc, element ->
    acc + if (element is OutlineElement) element.width else 0.dp
}

private data class OutlineElement(
    val color: Color,
    val width: Dp,
    val padding: Dp,
) : ModifierNodeElement<OutlineModifier>() {
    override fun create() = OutlineModifier(color, width, padding.validPadding())

    override fun update(node: OutlineModifier) {
        node.color = color
        node.width = width
        node.padding = padding.validPadding()
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "outline"
        properties["color"] = color
        properties["width"] = width
        properties["padding"] = padding
    }

    fun Dp.validPadding() = if (isUnspecified) 0.dp else this
}

private class OutlineModifier(
    var color: Color,
    var width: Dp,
    var padding: Dp,
) : Modifier.Node(), DrawModifierNode {
    override fun ContentDrawScope.draw() {
        val px = width.toPx()
        val pd = padding.toPx()
        val pd2 = pd * 2
        drawRect(color, Offset(x = -pd - px, y = -pd - px), Size(width = size.width + pd2 + px, height = px))
        drawRect(color, Offset(x = size.width + pd, y = -pd - px), Size(width = px, height = size.height + pd2 + px))
        drawRect(color, Offset(x = -pd, y = size.height + pd), Size(width = size.width + pd2 + px, height = px))
        drawRect(color, Offset(x = -pd - px, y = -pd), Size(width = px, height = size.height + pd2 + px))
        drawContent()
    }
}
