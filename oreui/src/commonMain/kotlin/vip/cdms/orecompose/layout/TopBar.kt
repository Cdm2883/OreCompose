package vip.cdms.orecompose.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import vip.cdms.orecompose.components.Label
import vip.cdms.orecompose.components.MonotoneIndication
import vip.cdms.orecompose.style.LocalPixelSize
import vip.cdms.orecompose.style.OreColors
import vip.cdms.orecompose.style.OreTexts
import vip.cdms.orecompose.style.px
import vip.cdms.orecompose.utils.*

val TopBarLargeHeight @Composable get() = 27.px

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navigation: @Composable RowScope.() -> Unit = { Spacer(Modifier.size(0.dp)) },
    actions: (@Composable RowScope.() -> Unit)? = null,
    title: @Composable BoxScope.() -> Unit,
) {
    val ps = LocalPixelSize.current.toPx()
    var boundingBox by remember { mutableStateOf(Rect.Zero) }
    Box(
        modifier
            .fillMaxWidth()
            .height(21.px)
            .background(0xffe6e8eb.argb)
            .padding(bottom = 3.px)
            .drawBehind {
                drawRect(0xffebedef.argb, Offset.zero(y = size.height), size.copy(height = ps))
                drawRect(0xffb1b2b5.argb, Offset.zero(y = size.height + ps), size.copy(height = 2 * ps))
                // 无法画到最顶层
                // drawRect(0x55000000.argb, Offset.zero(y = size.height + 3 * ps), size.copy(height = ps))
            }
            .onGloballyPositioned { coordinates ->
                val position = coordinates.positionInRoot()
                boundingBox = Rect(
                    left = position.x,
                    top = position.y,
                    right = position.x + coordinates.size.width,
                    bottom = position.y + coordinates.size.height
                )
            },
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides 0xff000000.argb,
        ) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navigation()
                if (actions != null) {
                    CompositionLocalProvider(
                        LocalTextStyle edit { copy(fontSize = OreTexts.Summary) }
                    ) {
                        Row(Modifier.fillMaxHeight(), content = actions)
                    }
                } else {
                    Spacer(Modifier.size(0.dp))
                }
            }
            CompositionLocalProvider(
                LocalTextStyle edit { copy(fontSize = 9.05f.px.toSp()) }
            ) {
                title()
            }
        }
    }
    Popup(
        offset = IntOffset(boundingBox.left.toInt(), boundingBox.bottom.toInt() + 3.px.toPx().toInt())
    ) {
        Spacer(Modifier.width(boundingBox.width.toDp()).height(1.px).background(0x55000000.argb))
    }
}

@Composable
fun TopBarNavigationButton(
    painter: Painter,
    contentDescription: String?,
    contentPadding: PaddingValues = PaddingValues(),
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    onClick: () -> Unit,
) {
    Box(
        modifier
            .fillMaxHeight()
            .padding(start = 1.px, top = 1.px, end = 1.px)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = MonotoneIndication,
                enabled = enabled,
                onClick = onClick
            )
            .aspectRatio(1f, true)
            .run { if (enabled) pointerHoverIcon(PointerIcon.Hand) else this }
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .height(OreTexts.Content.toDp())
                .aspectRatio(1f, true),
            tint = if (enabled) tint else 0xff8c8d90.argb,
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun TopBarActionButton(
    painter: Painter,
    contentDescription: String?,
    visibleDescription: Boolean = true,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val ps = LocalPixelSize.current.toPx()
    Row(
        modifier
            .fillMaxHeight()
            .drawBehind {
                drawRect(0xffa1a3a5.argb, size = size.copy(width = ps))
                drawRect(0xffb4b5b7.argb, Offset.zero(y = size.height), Size.square(ps))
            }
            .padding(start = 1.px)
            .drawBehind {
                drawRect(OreColors.White, size = Size(width = ps, height = size.height + ps))
            }
            .padding(start = 1.px, top = 1.px)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = MonotoneIndication,
                enabled = enabled,
                onClick = onClick
            )
            .padding(end = 1.px, bottom = 1.px)
            .run { if (enabled) pointerHoverIcon(PointerIcon.Hand) else this }
            .padding(horizontal = 9.px),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .height(OreTexts.Summary.toDp())
                .aspectRatio(1f, true)
                .scale(1.2f),
        )
        if (visibleDescription && contentDescription != null) {
            Spacer(Modifier.size(3.px))
            Label(contentDescription)
        }
    }
}
