package vip.cdms.orecompose.gallery

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import orecompose.gallery.generated.resources.HarmonyOS_Sans_SC_Regular
import orecompose.gallery.generated.resources.Res
import org.jetbrains.skiko.wasm.onWasmReady
import vip.cdms.orecompose.utils.PreloadResources
import vip.cdms.orecompose.utils.ResourceUrl.Companion.resourcesUrl
import vip.cdms.orecompose.utils.ResourceUrl.Companion.ttf

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        ComposeViewport(document.body!!) {
            val packaging = "orecompose.gallery.generated.resources"
            PreloadResources(
                Res.font.resourcesUrl(packaging) { ::HarmonyOS_Sans_SC_Regular.ttf },
            ) {
                App()
            }
        }
    }
}
