package vip.cdms.orecompose.gallery

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import orecompose.gallery.generated.resources.HarmonyOS_Sans_SC_Regular
import orecompose.gallery.generated.resources.Res
import vip.cdms.orecompose.utils.PreloadResources
import vip.cdms.orecompose.utils.ResourceUrl.Companion.resourceUrl
import vip.cdms.orecompose.utils.ResourceUrl.Companion.ttf

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val packaging = "orecompose.gallery.generated.resources"
        PreloadResources(
            Res.font.resourceUrl(packaging) { ::HarmonyOS_Sans_SC_Regular.ttf },
        ) {
            App()
        }
    }
}
