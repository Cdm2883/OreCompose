package vip.cdms.orecompose.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ProvideTextStyle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import orecompose.gallery.generated.resources.HarmonyOS_Sans_SC_Regular
import orecompose.gallery.generated.resources.Res
import org.jetbrains.compose.resources.Font
import vip.cdms.orecompose.components.Label
import vip.cdms.orecompose.style.OreTheme

fun main() = application {
    Window(::exitApplication, title = "OreCompose Gallery") {
        OreTheme {
            ProvideTextStyle(TextStyle(fontFamily = FontFamily(Font(Res.font.HarmonyOS_Sans_SC_Regular)))) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Label("OreCompose! 你好世界！", fontSize = 30.sp)
                }
            }
        }
    }
}
