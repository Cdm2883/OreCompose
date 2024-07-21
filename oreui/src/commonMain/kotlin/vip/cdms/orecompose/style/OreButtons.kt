package vip.cdms.orecompose.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import vip.cdms.orecompose.components.ButtonStyle
import vip.cdms.orecompose.components.ButtonStyles
import vip.cdms.orecompose.effect.LocalSounds
import vip.cdms.orecompose.utils.argb

object OreButtonStyles {
    val Disable = ButtonStyle(
        background = 0xffd0d1d4.argb,
        shadow = 0xffb1b2b5.argb,
        borderTop = 0xffd0d1d4.argb,
        borderBottom = 0xffd0d1d4.argb,
        borderJunction = 0xffd0d1d4.argb,
        activeDash = 0xffd0d1d4.argb,
        outline = OreColors.OutlineDisabled,
        text = 0xff4a4b4c.argb,
    )
    val Common = ButtonStyles(
        normal = ButtonStyle(
            background = 0xffd0d1d4.argb,
            shadow = 0xff58585a.argb,
            borderTop = 0xffffffff.argb,
            borderBottom = 0xffe3e3e5.argb,
            borderJunction = 0xfff4f4f5.argb,
            text = 0xff202022.argb,
        ),
        hover = ButtonStyle(
            background = 0xffb1b2b5.argb,
            shadow = 0xff58585a.argb,
            borderTop = 0xffffffff.argb,
            borderBottom = 0xffe3e3e5.argb,
            borderJunction = 0xfff4f4f5.argb,
            text = 0xff202022.argb,
        ),
        active = ButtonStyle(
            background = 0xff3c8527.argb,
            borderTop = 0xff639d52.argb,
            borderBottom = 0xff4f913c.argb,
            borderJunction = 0xff72a763.argb,
            activeDash = OreColors.White,
        ),
    )
    val Gray = ButtonStyles(
        normal = ButtonStyle(
            background = 0xff48494a.argb,
            shadow = 0xff313233.argb,
            borderTop = 0xff6d6d6e.argb,
            borderBottom = 0xff5a5b5c.argb,
            borderJunction = 0xff7b7b7c.argb,
        ),
        hover = ButtonStyle(
            background = 0xff58585a.argb,
            shadow = 0xff313233.argb,
            borderTop = 0xff79797b.argb,
            borderBottom = 0xff68686a.argb,
            borderJunction = 0xff868688.argb,
        ),
        press = ButtonStyle(
            background = 0xff313233.argb,
            borderTop = 0xff5a5b5c.argb,
            borderBottom = 0xff454647.argb,
            borderJunction = 0xff6a6b6c.argb,
            activeDash = OreColors.White,
        ),
    )
    val Green = ButtonStyles(
        normal = ButtonStyle(
            background = 0xff3c8527.argb,
            shadow = 0xff1d4d13.argb,
            borderTop = 0xff639d52.argb,
            borderBottom = 0xff4f913c.argb,
            borderJunction = 0xff72a763.argb,
        ),
        hover = ButtonStyle(
            background = 0xff2a641c.argb,
            shadow = 0xff1d4d13.argb,
            borderTop = 0xff7fa277.argb,
            borderBottom = 0xff699260.argb,
            borderJunction = 0xffa5bea0.argb,
        ),
        press = ButtonStyle(
            background = 0xff1d4d13.argb,
            borderTop = 0xff779471.argb,
            borderBottom = 0xff608259.argb,
            borderJunction = 0xffa0b49b.argb,
        ),
        active = ButtonStyle(
            background = 0xff1d4d13.argb,
            borderTop = 0xff779471.argb,
            borderBottom = 0xff608259.argb,
            borderJunction = 0xffa0b49b.argb,
            activeDash = OreColors.White,
        ),
    )
    val Purple = ButtonStyles(
        normal = ButtonStyle(
            background = 0xff7345e5.argb,
            shadow = 0xff4a1cac.argb,
            borderTop = 0xffa163f2.argb,
            borderBottom = 0xff8e49eb.argb,
            borderJunction = 0xffaa5bf3.argb,
        ),
        hover = ButtonStyle(
            background = 0xff5d2cc6.argb,
            shadow = 0xff4a1cac.argb,
            borderTop = 0xffa163f2.argb,
            borderBottom = 0xff8e49eb.argb,
            borderJunction = 0xffaa5bf3.argb,
        ),
        press = ButtonStyle(
            background = 0xff4a1cac.argb,
            borderTop = 0xffa864e6.argb,
            borderBottom = 0xff8b3cd8.argb,
            borderJunction = 0xffb059ef.argb,
            activeDash = OreColors.White,
        ),
    )
    val Red = ButtonStyles(
        normal = ButtonStyle(
            background = 0xffca3636.argb,
            shadow = 0xffad1d1d.argb,
            borderTop = 0xffd55e5e.argb,
            borderBottom = 0xffcf4a4a.argb,
            borderJunction = 0xffd96e6e.argb,
        ),
        hover = ButtonStyle(
            background = 0xffc02d2d.argb,
            shadow = 0xffad1d1d.argb,
            borderTop = 0xffe09696.argb,
            borderBottom = 0xffd98181.argb,
            borderJunction = 0xffecc0c0.argb,
        ),
        press = ButtonStyle(
            background = 0xffad1d1d.argb,
            borderTop = 0xffd68e8e.argb,
            borderBottom = 0xffce7777.argb,
            borderJunction = 0xffe6bbbb.argb,
            activeDash = OreColors.White,
        ),
    )
}
val ButtonStyles.Bold
    @Composable
    get() = run {
        val offset = with(LocalDensity.current) { Offset(1.px.toPx(), 1.px.toPx()) }
        val fontSize = with(LocalDensity.current) { 10.px.toSp() }
        fun TextStyle.shadow() = copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            shadow = Shadow(0x50000000.argb, offset, 0f)
        )
        fun ButtonStyle.bold() = copy(
            textStyle = { this@bold.textStyle(this).shadow() },
            sound = { LocalSounds.current.clickPop }
        )
        copy(
            normal = normal.bold(),
            hover = hover.bold(),
            press = press.bold(),
            active = active.bold(),
        )
    }

object OreButtonPaddings {
    val Common: PaddingValues
        @Composable
        get() = PaddingValues(horizontal = 10.px, vertical = 6.px)
    val Compact: PaddingValues
        @Composable
        get() = PaddingValues(horizontal = 11.px, vertical = 6.px)
}

object OreButtonSizes {
    val Common: DpRangeSize
        @Composable
        get() = DpRangeSize(minHeight = 28.px)
    val Compact: DpRangeSize
        @Composable
        get() = DpRangeSize(minHeight =  21.px)
}
