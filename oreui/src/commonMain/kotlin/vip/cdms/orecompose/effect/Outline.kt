package vip.cdms.orecompose.effect

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import vip.cdms.orecompose.style.LocalPixelSize
import vip.cdms.orecompose.style.OreColors

val LocalOutlineColor = staticCompositionLocalOf { OreColors.Outline }

fun Modifier.outline(color: Color? = null, shineTrigger: Any? = null, animationSpec: AnimationSpec<Float> = tween()) = composed {
    val shine = remember { Animatable(0f) }
    var initialized by remember { mutableStateOf(false) }
    if (shineTrigger != null) LaunchedEffect(shineTrigger) {
        if (!initialized) {
            initialized = true
            return@LaunchedEffect
        }
        shine.snapTo(0f)
        shine.animateTo(1f, animationSpec)
    }
    outline(color ?: LocalOutlineColor.current, LocalPixelSize.current, shine.value)
}
fun Modifier.outline(color: Color, pixel: Dp, shine: Float = 0f) = drawWithContent {
    // TODO shine 发光边缘显示进度百分比
    // drawRect brush 径向渐变
    // 混合模式, 只画在边框上
    
    val ps = pixel.toPx()
    drawRect(color, Offset(x = -ps, y = -ps), Size(width = size.width + ps, height = ps))
    drawRect(color, Offset(x = size.width, y = -ps), Size(width = ps, height = size.height + ps))
    drawRect(color, Offset.Zero.copy(y = size.height), size.copy(width = size.width + ps, height = ps))
    drawRect(color, Offset.Zero.copy(x = -ps), size.copy(width = ps, height = size.height + ps))
    drawContent()
}
