package vip.cdms.orecompose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import vip.cdms.orecompose.effect.*
import vip.cdms.orecompose.style.*
import kotlin.math.min

data class ButtonStyle(
    val background: Color,
    val shadow: Color? = null,
    val borderTop: Color,
    val borderBottom: Color,
    val borderJunction: Color,
    val activeDash: Color? = null,
    val outline: Color? = null,
    val text: Color? = OreColors.White,
    val textStyle: TextStyle.() -> TextStyle = { this },
    val sound: (@Composable () -> String?)? = { LocalSounds.current.click },
)
data class ButtonStyles(
    val normal: ButtonStyle,
    val hover: ButtonStyle,
    val press: ButtonStyle = hover,
    val active: ButtonStyle = press,
)

object ButtonDefaults {
    val Modifier get() = androidx.compose.ui.Modifier.outline().sound()
    val Styles = OreButtonStyles.Common
    val StylesDisabled = OreButtonStyles.Disable
    val Size @Composable get() = OreButtonSizes.Common
    val ContentPadding @Composable get() = OreButtonPaddings.Common
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = ButtonDefaults.Modifier,
    enabled: Boolean = true,
    activated: Boolean = false,
    pressable: Boolean = true,
    styles: ButtonStyles = ButtonDefaults.Styles,
    stylesDisabled: ButtonStyle = ButtonDefaults.StylesDisabled,
    rangeSize: DpRangeSize? = ButtonDefaults.Size,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    
    val buttonPressed = pressable && (pressed || activated)
    val pixelSizeDp = LocalPixelSize.current
    val style = when {
        !enabled  -> stylesDisabled
        activated -> styles.active
        pressed   -> styles.press
        hovered   -> styles.hover
        else      -> styles.normal
    }
    
    val drawBackground = fun DrawScope.() {
        val ps = pixelSizeDp.toPx()
        val paddingBottom = if (buttonPressed) 0f else ps * 2
        drawRect(style.background)
        drawRect(style.borderTop, size = size.copy(height = ps))
        drawRect(style.borderTop, size = size.copy(width = ps, height = size.height - paddingBottom))
        drawRect(style.borderBottom, Offset.Zero.copy(y = size.height - ps - paddingBottom), size.copy(height = ps))
        drawRect(style.borderBottom, Offset.Zero.copy(x = size.width - ps), size.copy(width = ps, height = size.height - paddingBottom))
        val block = Size(ps, ps)
        drawRect(style.borderJunction, Offset.Zero.copy(y = size.height - ps - paddingBottom), block)
        drawRect(style.borderJunction, Offset.Zero.copy(x = size.width - ps), block)
        if (!buttonPressed) drawRect(style.shadow!!, Offset.Zero.copy(y = size.height - paddingBottom), size.copy(height = paddingBottom))
        if (activated) {
            val dashWidth = min(size.width * .3f, 32 * ps)
            drawRect(style.activeDash!!, Offset(x = size.width / 2 - dashWidth / 2, y = size.height - ps), Size(width = dashWidth, height = ps))
        }
    }
    
    CompositionLocalProvider(
        LocalSoundEffect provides (style.sound ?: { LocalSoundEffect.current }).invoke(),
        
        LocalTextStyle provides LocalTextStyle.current.copy(
            fontSize = with(LocalDensity.current) { 9.px.toSp() }
        ).run { style.textStyle(this) },
        LocalContentColor provides (style.text ?: LocalContentColor.current),
        LocalOutlineColor provides (style.outline ?: LocalOutlineColor.current),
    ) {
        Box(
                Modifier
                    .then(rangeSize)
                    .padding(top = if (buttonPressed) 2.px else 0.px)
                    .drawBehind(drawBackground)
                    .clickable(
                        interactionSource,
                        indication = null,
                        enabled = enabled,
                        onClick = onClick
                    )
                    .then(modifier)
                    .padding(contentPadding),
            Alignment.Center
        ) {
            Row(
                Modifier.padding(bottom = if (!buttonPressed) 2.px else 0.px),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content
            )
        }
    }
}
