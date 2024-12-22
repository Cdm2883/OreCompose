package vip.cdms.orecompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle

typealias FontFamilyFallback = Pair<FontFamily, (Char) -> Boolean>
val LocalFontsFallback = staticCompositionLocalOf<Array<FontFamilyFallback>?> { null }

@Composable
fun CharSequence.localFontsFallback() = LocalFontsFallback.current?.let { fontsFallback(*it) }
    ?: if (this is AnnotatedString) this else AnnotatedString(this.toString())

fun CharSequence.fontsFallback(vararg fonts: FontFamilyFallback) =
    charStyle(*fonts.map { SpanStyle(fontFamily = it.first) to it.second }.toTypedArray())

fun CharSequence.charStyle(vararg styles: Pair<SpanStyle, (Char) -> Boolean>) = buildAnnotatedString {
    val text = this@charStyle
    var i = 0
    while (i < text.length) {
        val char = text[i]
        if (char.code in 0xd800..0xdbff) {
            append(text.subSequence(i, i + if (text.getOrNull(i + 1) != null) 2 else 1))
            i += 2
            continue
        }
        styles.asSequence()
            .filter { (_, filter) -> filter(char) }
            .map { (style) -> style }
            .reduceOrNull { acc, next -> acc.merge(next) }
            ?.let { withStyle(it) { append(text.subSequence(i, i + 1)) } }
            ?: append(text.subSequence(i, i + 1))
        i++
    }
}
