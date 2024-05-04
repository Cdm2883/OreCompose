package vip.cdms.orecompose.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import vip.cdms.orecompose.effect.*
import vip.cdms.orecompose.style.LocalPixelSize
import vip.cdms.orecompose.style.OreColors
import vip.cdms.orecompose.style.px
import vip.cdms.orecompose.utils.argb

object ToggleDefaults {
    val Modifier get() = androidx.compose.ui.Modifier.sound()
    val TrackModifier get() = androidx.compose.ui.Modifier.outline()
    val ThumbModifier get() = androidx.compose.ui.Modifier.outline()
    val AnimationSpec get() = spring<Dp>(dampingRatio = 0.6f, stiffness = 2000f)  // 没法像安卓BounceInterpolator那样好看
}

private data class TogglePalette(
    val trackOnBackground: Color = 0xff3c8527.argb,
    val trackOnMark: Color = 0xffffffff.argb,
    val trackOnBorderTop: Color = 0xff639d52.argb,
    val trackOnBorderBottom: Color = 0xff4f913c.argb,
    val trackOnBorderJunction: Color = 0xff72a763.argb,

    val trackOffBackground: Color = 0xff8c8d90.argb,
    val trackOffMark: Color = 0xff242425.argb,
    val trackOffBorderTop: Color = 0xffa3a4a6.argb,
    val trackOffBorderBottom: Color = 0xff97989b.argb,
    val trackOffBorderJunction: Color = 0xffacadaf.argb,

    val thumbBackground: Color = 0xffd0d1d4.argb,
    val thumbBackgroundHover: Color = 0xffb1b2b5.argb,
    val thumbShadow: Color = 0xff58585a.argb,
    val thumbBorderTop: Color = 0xffeff0f0.argb,
    val thumbBorderBottom: Color = 0xffe0e0e1.argb,
    val thumbBorderJunction: Color = 0xfff9f9f9.argb,

    val outline: Color? = null
)
private val TogglePaletteDefault = TogglePalette()
private val TogglePaletteDisabled = TogglePalette(
    trackOnBackground = 0xffd0d1d4.argb,
    trackOnMark = 0xff6d6d6d.argb,
    trackOnBorderTop = 0xffd0d1d4.argb,
    trackOnBorderBottom = 0xffd0d1d4.argb,
    trackOnBorderJunction = 0xffd0d1d4.argb,

    trackOffBackground = 0xffd0d1d4.argb,
    trackOffMark = 0xff6d6d6d.argb,
    trackOffBorderTop = 0xffd0d1d4.argb,
    trackOffBorderBottom = 0xffd0d1d4.argb,
    trackOffBorderJunction = 0xffd0d1d4.argb,

    thumbBackground = 0xffd0d1d4.argb,
    thumbBackgroundHover = 0xffd0d1d4.argb,
    thumbShadow = 0xffb1b2b5.argb,
    thumbBorderTop = 0xffd0d1d4.argb,
    thumbBorderBottom = 0xffd0d1d4.argb,
    thumbBorderJunction = 0xffd0d1d4.argb,
    
    outline = OreColors.OutlineDisabled
)

@Composable
fun Toggle(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = ToggleDefaults.Modifier,
    modifierTrack: Modifier = ToggleDefaults.TrackModifier,
    modifierThumb: Modifier = ToggleDefaults.ThumbModifier,
    animationSpec: AnimationSpec<Dp> = ToggleDefaults.AnimationSpec,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()
    
    val palette = if (enabled) TogglePaletteDefault else TogglePaletteDisabled
    val ps = with(LocalDensity.current) { LocalPixelSize.current.toPx() }
    val offset by animateDpAsState(if (checked) 14.px else 0.px, animationSpec)
    
    CompositionLocalProvider(
        LocalSoundEffect provides LocalSounds.current.click,
        
        LocalOutlineColor provides (palette.outline ?: LocalOutlineColor.current),
    ) {
        Box(
            Modifier
                .width(28.px)
                .height(14.px)
                .then(
                    if (onCheckedChange != null) Modifier.toggleable(
                        value = checked,
                        onValueChange = onCheckedChange,
                        enabled = enabled,
                        role = Role.Switch,
                        interactionSource = interactionSource,
                        indication = null
                    ) else Modifier
                ) then modifier,
            Alignment.BottomStart
        ) {
            // track
            Spacer(
                Modifier
                    .width(28.px)
                    .height(12.px)
                    .background(Color.Green)
                    .drawBehind {
                        drawRect(palette.trackOnBackground)
                        drawRect(palette.trackOnBorderTop, size = size.copy(height = ps))
                        drawRect(palette.trackOnBorderTop, size = size.copy(width = ps))
                        drawRect(palette.trackOnBorderBottom, Offset.Zero.copy(y = size.height - ps), size.copy(height = ps))
                        drawRect(palette.trackOnBorderJunction, Offset.Zero.copy(y = size.height - ps), Size(ps, ps))
                        
                        drawRect(palette.trackOnMark, Offset(6 * ps, 3 * ps), Size(ps, 6 * ps))
                        
                        drawRect(palette.trackOffBackground, Offset.Zero.copy(x = size.width / 2), size.copy(width = size.width / 2))
                        drawRect(palette.trackOffBorderTop, Offset.Zero.copy(x = size.width / 2), Size(size.width / 2, ps))
                        drawRect(palette.trackOffBorderBottom, Offset(size.width / 2, size.height - ps), Size(size.width / 2, ps))
                        drawRect(palette.trackOffBorderBottom, Offset.Zero.copy(x = size.width - ps), size.copy(width = ps))
                        drawRect(palette.trackOffBorderJunction, Offset.Zero.copy(x = size.width - ps), Size(ps, ps))
                        
                        drawRect(palette.trackOffMark, Offset(size.width - 9 * ps, 3 * ps), Size(4 * ps, ps))
                        drawRect(palette.trackOffMark, Offset(size.width - 9 * ps, 8 * ps), Size(4 * ps, ps))
                        drawRect(palette.trackOffMark, Offset(size.width - 10 * ps, 4 * ps), Size(ps, 4 * ps))
                        drawRect(palette.trackOffMark, Offset(size.width - 5 * ps, 4 * ps), Size(ps, 4 * ps))
                    }
                    then modifierTrack
            )
            
            // thumb
            Spacer(
                Modifier
                    .width(14.px)
                    .height(14.px)
                    .offset(x = offset)
                    .background(Color.Blue)
                    .drawBehind {
                        drawRect(if (hovered || pressed) palette.thumbBackgroundHover else palette.thumbBackground)
                        
                        drawRect(palette.thumbBorderTop, size = size.copy(height = ps))
                        drawRect(palette.thumbBorderTop, size = size.copy(width = ps))
                        
                        drawRect(palette.thumbBorderBottom, Offset.Zero.copy(y = size.height - 3 * ps), size.copy(height = ps))
                        drawRect(palette.thumbBorderBottom, Offset.Zero.copy(x = size.width - ps), size.copy(width = ps))
                        
                        drawRect(palette.thumbBorderJunction, Offset.Zero.copy(y = size.height - 3 * ps), Size(ps, ps))
                        drawRect(palette.thumbBorderJunction, Offset.Zero.copy(x = size.width - ps), Size(ps, ps))
                        
                        drawRect(palette.thumbShadow, Offset.Zero.copy(y = size.height - 2 * ps), size.copy(height = 2 * ps))
                    }
                    then modifierThumb
            )
        }
    }
}
