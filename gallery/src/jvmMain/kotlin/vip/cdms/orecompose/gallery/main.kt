package vip.cdms.orecompose.gallery

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import orecompose.gallery.generated.resources.Res
import orecompose.gallery.generated.resources.orecompose_logo
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        ::exitApplication,
        title = "OreCompose Gallery",
        icon = painterResource(Res.drawable.orecompose_logo)
    ) {
        App()
    }
}
