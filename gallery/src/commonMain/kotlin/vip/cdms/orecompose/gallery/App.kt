package vip.cdms.orecompose.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import orecompose.gallery.generated.resources.HarmonyOS_Sans_SC_Regular
import orecompose.gallery.generated.resources.Res
import org.jetbrains.compose.resources.Font
import vip.cdms.orecompose.components.Label
import vip.cdms.orecompose.style.OreTheme

@Composable
fun App() {
    OreTheme {
        ProvideTextStyle(TextStyle(fontFamily = FontFamily(Font(Res.font.HarmonyOS_Sans_SC_Regular)))) {
            var screenWidth by remember { mutableStateOf(Float.MIN_VALUE) }
            Box(
                Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { screenWidth = it.boundsInParent().width },
                contentAlignment = Alignment.Center
            ) {
                Label(
                    "OreCompose! 你好世界！",
                    fontSize = LocalDensity.current.run { (screenWidth / 16).toSp() }
                )
            }
        }
    }
}
