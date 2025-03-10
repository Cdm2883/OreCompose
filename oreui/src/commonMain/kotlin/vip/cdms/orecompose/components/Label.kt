package vip.cdms.orecompose.components

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import vip.cdms.orecompose.effect.Pixel
import vip.cdms.orecompose.effect.Px
import vip.cdms.orecompose.utils.*

object LocalLabel {
    val AutoFontsFallbackEnabled = staticCompositionLocalOf { false }
}

@Composable
fun Label(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: Px = Pixel.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: Px = Pixel.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: Px = Pixel.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    @Composable
    fun Text(text: AnnotatedString) = Text(
        text,
        modifier,
        color,
        fontSize.toSp(),
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing.toSp(),
        textDecoration,
        textAlign,
        lineHeight.toSp(),
        overflow,
        softWrap,
        maxLines,
        minLines,
        inlineContent,
        onTextLayout,
        style
    )

    // FIXME(oreui/web): cache label text
    if (RuntimePlatform is Platform.Web)
        // weird, localFontsFallback will be not working with another scope outer (like LaunchedEffect & if-else)
        return Text(if (LocalLabel.AutoFontsFallbackEnabled.current) text.localFontsFallback() else text)

    var texted by remember { mutableStateOf<AnnotatedString?>(null) }
    val localFontsFallback = LocalFontsFallback.current
    if (!localFontsFallback.isNullOrEmpty() && LocalLabel.AutoFontsFallbackEnabled.current) LaunchedEffect(text) {
        texted = text.fontsFallback(*localFontsFallback)  // !!! TIME-CONSUMING !!!
    }
    Text(texted ?: text)
}

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: Px = Pixel.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: Px = Pixel.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: Px = Pixel.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) = Label(
    AnnotatedString(text),
    modifier,
    color,
    fontSize,
    fontStyle,
    fontWeight,
    fontFamily,
    letterSpacing,
    textDecoration,
    textAlign,
    lineHeight,
    overflow,
    softWrap,
    maxLines,
    minLines,
    onTextLayout = onTextLayout ?: {},
    style = style,
)
