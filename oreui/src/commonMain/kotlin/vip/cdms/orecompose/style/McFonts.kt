package vip.cdms.orecompose.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import orecompose.oreui.generated.resources.Minecraft
import orecompose.oreui.generated.resources.Res
import org.jetbrains.compose.resources.Font
import vip.cdms.orecompose.utils.FontFamilyFallback

object McFonts {

    // https://github.com/IdreesInc/Minecraft-Font
    // OFL-1.1 license
    object Ascii {
        val Resource = Res.font.Minecraft
        val FontFamily
            @Composable inline get() = FontFamily(Font(Resource))
        val Fallback: FontFamilyFallback
            @Composable inline get() = FontFamily to ::hasGlyph
        val Glyphs = arrayOf(33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
            43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
            57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
            71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84,
            85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98,
            99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
            110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120,
            121, 122, 123, 124, 125, 126, 161, 162, 163, 164, 165,
            166, 167, 168, 169, 170, 171, 172, 174, 175, 176, 177,
            178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188,
            189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199,
            200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210,
            211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221,
            222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232,
            233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243,
            244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 8364,
            9786, 9787, 9829)
        @Suppress("NOTHING_TO_INLINE")
        inline fun hasGlyph(c: Char) = c.code in Glyphs
    }

    // TODO(oreui): bold font (e.g. title)
}
