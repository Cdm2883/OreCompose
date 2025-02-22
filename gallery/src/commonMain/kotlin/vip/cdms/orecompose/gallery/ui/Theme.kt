package vip.cdms.orecompose.gallery.ui

import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import orecompose.gallery.generated.resources.HarmonyOS_Sans_SC_Regular
import orecompose.gallery.generated.resources.Res
import org.jetbrains.compose.resources.Font
import vip.cdms.orecompose.layout.panorama.OrePanoramaModule
import vip.cdms.orecompose.style.OreModule
import vip.cdms.orecompose.style.OreTheme

private val TextFontModule = OreModule.wrap {
    val fontFamily = FontFamily(Font(Res.font.HarmonyOS_Sans_SC_Regular))
    ProvideTextStyle(TextStyle(fontFamily = fontFamily), content = it)
}

@Composable
fun GalleryTheme(content: @Composable () -> Unit) {
    OreTheme(
        modules = arrayOf(
            OrePanoramaModule,
            TextFontModule,
        ),
        content = content
    )
}
