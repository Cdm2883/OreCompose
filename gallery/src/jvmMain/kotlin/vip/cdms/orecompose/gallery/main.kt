package vip.cdms.orecompose.gallery

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mayakapps.compose.windowstyler.WindowBackdrop
import com.mayakapps.compose.windowstyler.WindowStyle
import orecompose.gallery.generated.resources.Res
import orecompose.gallery.generated.resources.orecompose_logo
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        ::exitApplication,
        title = "OreCompose Gallery",
        icon = painterResource(Res.drawable.orecompose_logo)
    ) {
        WindowStyle(
            isDarkTheme = isSystemInDarkTheme(),
            backdropType = WindowBackdrop.Mica,
        )
        App()
    }
}
