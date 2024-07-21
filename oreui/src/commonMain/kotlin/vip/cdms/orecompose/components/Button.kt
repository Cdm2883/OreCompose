package vip.cdms.orecompose.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import vip.cdms.orecompose.effect.*
import vip.cdms.orecompose.style.*
import vip.cdms.orecompose.utils.*
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
    val disable: ButtonStyle = OreButtonStyles.Disable
)

fun ButtonStyle.band() = ButtonStyles(this, this, this, this, this)

object ButtonDefaults {
    val Modifier get() = androidx.compose.ui.Modifier.outline().sound()
    val Styles = OreButtonStyles.Common
    val Size @Composable get() = OreButtonSizes.Common
    val ContentPadding @Composable get() = OreButtonPaddings.Common
}

@Composable
fun Button(
    onClick: () -> Unit = {},
    modifier: Modifier = ButtonDefaults.Modifier,
    enabled: Boolean = true,
    activated: Boolean = false,
    pressable: Boolean = true,
    styles: ButtonStyles = ButtonDefaults.Styles,
    rangeSize: DpRangeSize? = ButtonDefaults.Size,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit = {}
) {
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    
    val stylePressed = pressable && (pressed || activated)
    val ps = LocalPixelSize.current.toPx()
    val style = when {
        !enabled  -> styles.disable
        activated -> styles.active
        pressed   -> styles.press
        hovered   -> styles.hover
        else      -> styles.normal
    }
    
    val drawBackground = fun DrawScope.() {
        val paddingBottom = if (stylePressed) 0f else 2 * ps
        drawRect(style.background)
        drawRect(style.borderTop, size = size.copy(height = ps))
        drawRect(style.borderTop, size = size.copy(width = ps).offset(height = -paddingBottom))
        drawRect(style.borderBottom, Offset.zero(y = size.height - ps - paddingBottom), size.copy(height = ps))
        drawRect(style.borderBottom, Offset.zero(x = size.width - ps), size.copy(width = ps).offset(height = -paddingBottom))
        drawRect(style.borderJunction, Offset.zero(y = size.height - ps - paddingBottom), Size.square(ps))
        drawRect(style.borderJunction, Offset.zero(x = size.width - ps), Size.square(ps))
        if (!stylePressed) drawRect(style.shadow!!, Offset.zero(y = size.height - paddingBottom), size.copy(height = paddingBottom))
        if (activated) min(size.width * .3f, 32 * ps).let { dashWidth ->
            drawRect(style.activeDash!!, Offset(size.width / 2 - dashWidth / 2, size.height - ps), Size(dashWidth, ps))
        }
    }
    
    CompositionLocalProvider(
        LocalSoundEffect provides (style.sound ?: { LocalSoundEffect.current }).invoke(),
        
        LocalTextStyle edit { copy(fontSize = OreTexts.Content).run { style.textStyle(this) } },
        LocalContentColor provides (style.text ?: LocalContentColor.current),
        LocalOutlineColor provides (style.outline ?: LocalOutlineColor.current),
    ) {
        Box(
                Modifier
                    .then(rangeSize)
                    .padding(top = if (stylePressed) 2.px else 0.px)
                    .then(modifier)
                    .drawBehind(drawBackground)
                    .clickable(
                        interactionSource,
                        indication = null,
                        enabled = enabled,
                        onClick = onClick
                    )
                    .padding(contentPadding),
            Alignment.Center
        ) {
            Row(
                Modifier.padding(bottom = if (!stylePressed) 2.px else 0.px),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content
            )
        }
    }
}

val MonotoneIndication by lazy {
    object : Indication {
        @Composable
        override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
            val hovered by interactionSource.collectIsHoveredAsState()
            val pressed by interactionSource.collectIsPressedAsState()
            return object : IndicationInstance {
                override fun ContentDrawScope.drawIndication() {
                    if (pressed) {
                        drawRect(0x11000000.argb)
                    } else if (hovered) {
                        drawRect(0x77ffffff.argb)
                    }
                    drawContent()
                }
            }
        }
    }
}
