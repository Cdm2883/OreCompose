package vip.cdms.orecompose.example

import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import vip.cdms.orecompose.layout.ExperimentalPanoramaApi
import vip.cdms.orecompose.layout.PanoramaViewer
import vip.cdms.orecompose.style.OreTheme
import vip.cdms.orecompose.utils.argb
import vip.cdms.orecompose.utils.tickFloat
import vip.cdms.orecompose.utils.tickerFlow
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun OreComposeExampleTheme(content: @Composable () -> Unit) {
    OreTheme(content = content)
}

@Composable
fun PanoramaBackground(content: @Composable BoxScope.() -> Unit) {
    Box {
        PanoramaBackground()
        content()
    }
}
// prevent recompose others too often
@OptIn(ExperimentalPanoramaApi::class)
@Composable
fun PanoramaBackground() {
    val yaw by if (getPlatform() !is Platform.Desktop)
        remember { mutableFloatStateOf((8..55).random() * 0.1f) }  // TODO wait for three.js support
    else tickFloat(
        begin = 0.8f,
        end = 5.5f,
        step = 0.01f,
        ticker = { tickerFlow(500.milliseconds) },
        repeatMode =  RepeatMode.Reverse
    )
    PanoramaViewer(
        yaw = yaw,
        modifier = Modifier.fillMaxSize().background(0xff161616.argb)
    )
}
