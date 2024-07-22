package vip.cdms.orecompose.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import orecompose.example.generated.resources.Res
import orecompose.example.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OreCompose Example",
        icon = painterResource(Res.drawable.logo),
    ) {
        App()
    }
}
