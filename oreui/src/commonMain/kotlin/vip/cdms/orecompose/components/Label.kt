package vip.cdms.orecompose.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import vip.cdms.orecompose.utils.*

// TODO need better impl (support obfuscated), only edit Label.kt/McFormatter.kt and do NOT exit original text
@ExperimentalTextApi
@Composable
fun Label(
    text: AnnotatedString,
    mcFont: Boolean = true,
    mcFormat: Boolean = false,
    showFormatMark: Boolean = false,
    parentBackground: Color? = null, //
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val obfuscatedAnimation = rememberInfiniteTransition()
        .animateFloat(0f, 1f, infiniteRepeatable(tween()))
    
    val textMeasure = rememberTextMeasurer()
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var onDrawObfuscated: DrawScope.() -> Unit by remember { mutableStateOf({}) }
    
    val obfuscatedIndexes = mutableMapOf<Int, McFormatFragment>()
    fun mcFormatReceiver(fragment: McFormatFragment) {
        if (!fragment.obfuscated) return
        (fragment.index..<fragment.index + fragment.buffer.length).forEach { obfuscatedIndexes[it] = fragment }
    }
    
    val finalColor = if (color.isSpecified) {
            color
        } else if (style.color.isSpecified) {
            style.color
        } else {
            LocalContentColor.current.copy(LocalContentAlpha.current)
        }
    val finalText = text
            .run { if (mcFormat) mcFormat(showFormatMark, ::mcFormatReceiver) else this }
            .run { if (mcFont) mcFont() else this }
    val finalStyle = style.merge(
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign ?: TextAlign.Unspecified,
        lineHeight = lineHeight,
        fontFamily = fontFamily,
        textDecoration = textDecoration,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing
    )
    
    @Suppress("SpellCheckingInspection") val monocraft = Monocraft
    LaunchedEffect(textLayoutResult) {
        textLayoutResult?.let {
            onDrawObfuscated = {
                for ((index, fragment) in obfuscatedIndexes) {
                    val bounds = it.getBoundingBox(index)
                    
                    obfuscatedAnimation.value  // 让compose收集到而已, 使其一直刷新
                    drawRect(
                        parentBackground ?: Color(1 - finalColor.red, 1 - finalColor.green, 1 - finalColor.blue, finalColor.alpha, finalColor.colorSpace),
                        bounds.topLeft,
                        bounds.size,
                    )
                    drawText(
                        textMeasurer = textMeasure,
                        style = finalStyle
                            .copy(color = fragment.color ?: finalColor)
                            .run { if (mcFont) copy(fontFamily = monocraft /* L104 硬编码吧 */)  else this }
                            .run { if (fragment.bold) copy(fontWeight = FontWeight.Bold) else this }
                            .run { if (fragment.italic) copy(fontStyle = FontStyle.Italic) else this }
                            .run {
                                when {
                                    fragment.strikethrough && fragment.underline -> copy(textDecoration = TextDecoration.combine(listOf(TextDecoration.LineThrough, TextDecoration.Underline)))
                                    fragment.strikethrough -> copy(textDecoration = TextDecoration.LineThrough)
                                    fragment.underline -> copy(textDecoration = TextDecoration.Underline)
                                    else -> this
                                }
                            },
                        text = (
                            if (isMonocraftInclude(finalText[index])) ('A'..'Z') + ('a'..'z') + ('0'..'9')
                            else ('\u4e00'..'\u9fa5') + ('\u4e00'..'\u9fa5')
                        ).random().toString(),
                        topLeft = bounds.topLeft.run {
                            if (mcFont) copy(y = y * 0.1f)  // L104 硬编码吧
                            else this
                        },
                    )
                }
            }
        }
    }

    val finalAnnotatedString = if (finalText is AnnotatedString) finalText else AnnotatedString.Builder().append(finalText).toAnnotatedString()
    BasicText(
        text = finalAnnotatedString,
        modifier = modifier.drawWithCache {
            onDrawWithContent {
                drawContent()
                if (mcFormat) onDrawObfuscated()
            }
        },
        style = finalStyle,
        onTextLayout = {
            textLayoutResult = it
            if (mcFormat) onTextLayout(it)
        },
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        color = { finalColor }
    )
}

@ExperimentalTextApi
@Composable
fun Label(
    text: String,
    mcFont: Boolean = true,
    mcFormat: Boolean = true,
    showFormatMark: Boolean = false,
    parentBackground: Color? = null,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) = Label(
    buildAnnotatedString { append(text) },
    mcFont,
    mcFormat,
    showFormatMark,
    parentBackground,
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
    inlineContent,
    onTextLayout,
    style
)
