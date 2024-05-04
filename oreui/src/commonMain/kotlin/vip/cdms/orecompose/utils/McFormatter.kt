package vip.cdms.orecompose.utils

import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

const val SS = '§'
val McFormattingColors = mapOf(
    '0' to 0xFF_000000.argb,  // black
    '1' to 0xFF_0000AA.argb,  // dark_blue
    '2' to 0xFF_00AA00.argb,  // dark_green
    '3' to 0xFF_00AAAA.argb,  // dark_aqua
    '4' to 0xFF_AA0000.argb,  // dark_red
    '5' to 0xFF_AA00AA.argb,  // dark_purple
    '6' to 0xFF_FFAA00.argb,  // gold
    '7' to 0xFF_AAAAAA.argb,  // gray
    '8' to 0xFF_555555.argb,  // dark_gray
    '9' to 0xFF_5555FF.argb,  // blue (5455FF)
    'a' to 0xFF_55FF55.argb,  // green (55FF56)
    'b' to 0xFF_55FFFF.argb,  // aqua
    'c' to 0xFF_FF5555.argb,  // red
    'd' to 0xFF_FF55FE.argb,  // light_purple
    'e' to 0xFF_FFFF55.argb,  // yellow
    'f' to 0xFF_FFFFFF.argb,  // white
    'g' to 0xFF_EECF15.argb,  // minecoin_gold
    'h' to 0xFF_E3D4D1.argb,  // material_quartz
    'i' to 0xFF_CECACA.argb,  // material_iron
    'j' to 0xFF_443A3B.argb,  // material_netherite
//    'm' to 0xFF_971607.argb,  // material_redstone
//    'n' to 0xFF_B4684D.argb,  // material_copper
    'p' to 0xFF_DEB12D.argb,  // material_gold
    'q' to 0xFF_47A036.argb,  // material_emerald
    's' to 0xFF_2CBAA8.argb,  // material_diamond
    't' to 0xFF_21497B.argb,  // material_lapis
    'u' to 0xFF_9A5CC6.argb,  // material_amethyst
)

data class McFormatFragment(
    val index         : Int,
    val buffer        : StringBuilder,
    val color         : Color?,
    val obfuscated    : Boolean,
    val bold          : Boolean,
    val strikethrough : Boolean,
    val underline     : Boolean,
    val italic        : Boolean
)
typealias FragmentReceiver = McFormatFragment.() -> Unit

@Composable
fun CharSequence.mcFormat(showMark: Boolean = false, receiver: FragmentReceiver? = null) =
    this@mcFormat.mcFormat(showMark, LocalContentColor.current, receiver)

fun CharSequence.mcFormat(showMark: Boolean = false, defaultColor: Color = 0xffffffff.argb, receiver: FragmentReceiver? = null) = buildAnnotatedString {
    val text = this@mcFormat
    val buffer = StringBuilder()
    var formatting: Char?
    var color: Color? = null
    var obfuscated    = false  // k
    var bold          = false  // l
    var strikethrough = false  // m
    var underline     = false  // n
    var italic        = false  // o
    
    fun consumes() {
        if (buffer.isEmpty()) return
        if (receiver != null) receiver(McFormatFragment(length, buffer, color, obfuscated, bold, strikethrough, underline, italic))
        if (color == null && !obfuscated && !bold && !strikethrough && !underline && !italic) {
            append(buffer)
            buffer.clear()
            return
        }
        
        var style = color?.let { SpanStyle(it) } ?: SpanStyle()
        if (bold) style = style.copy(fontWeight = FontWeight.Bold)
        if (italic) style = style.copy(fontStyle = FontStyle.Italic) 
        style = when {
            strikethrough && underline -> style.copy(textDecoration = TextDecoration.combine(listOf(TextDecoration.LineThrough, TextDecoration.Underline)))
            strikethrough -> style.copy(textDecoration = TextDecoration.LineThrough)
            underline -> style.copy(textDecoration = TextDecoration.Underline)
            else -> style
        }
        
        if (buffer[0] == SS) withStyle(style.copy((color ?: defaultColor).copy(.5f))) {
            append(buffer.substring(0, 2))
            buffer.delete(0, 2)
        }
        
//        if (obfuscated) {
//            val randoms = ('A'..'Z') + ('a'..'z') + ('0'..'9')
//            val length = buffer.length
//            buffer.clear()
//            buffer.append((1..length).map { randoms.random() }.joinToString(""))
//        }
        
        withStyle(style) { append(buffer) }
        
        buffer.clear()
    }
    
    var i = 0
    while (i < text.length) {
        val c = text[i]
        if (c == SS) {
            formatting = text.getOrNull(i + 1)
            if (formatting != null) {
                consumes()
                when (formatting) {
                    'k' -> obfuscated = true
                    'l' -> bold = true
                    'm' -> strikethrough = true
                    'n' -> underline = true
                    'o' -> italic = true
                    'r' -> {
                        color = null
                        obfuscated = false
                        bold = false
                        strikethrough = false
                        underline = false
                        italic = false
                    }
                    else -> McFormattingColors[formatting]?.let { color = it }
                }
                if (showMark) buffer.append(SS).append(formatting)
                i++
            }
        } else buffer.append(c)
        i++
    }
    consumes()
}
