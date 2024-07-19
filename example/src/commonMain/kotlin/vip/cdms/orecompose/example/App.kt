package vip.cdms.orecompose.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import vip.cdms.orecompose.components.*
import vip.cdms.orecompose.effect.outline
import vip.cdms.orecompose.effect.sound
import vip.cdms.orecompose.style.Bold
import vip.cdms.orecompose.style.OreButtonStyles
import vip.cdms.orecompose.style.OreIcons
import vip.cdms.orecompose.style.px
import vip.cdms.orecompose.utils.buildPixelIcon
import vip.cdms.orecompose.utils.mcFormat
import vip.cdms.orecompose.utils.painter

@OptIn(ExperimentalTextApi::class)
@Composable
@Preview
fun App() {
    
    @Composable
    fun RowGap(content: @Composable RowScope.() -> Unit) = Row(horizontalArrangement = Arrangement.spacedBy(8.px), content = content)
    @Composable
    fun Content() {
        
        Label("Hello, ${getPlatform().message}!")
        
        Image(
            painter = buildPixelIcon(10) {
                W + W + W + W + W + W + W + W + W + W    +
                W +   +   +   +   +   +   +   +   + W    +
                W +   +   +   +   +   +   +   +   + W    +
                W +   +   +   + W +   +   + W +   + W    +
                W +   +   +   + W +   +   + W +   + W    +
                W +   +   +   + W +   +   + W +   + W    +
                W +   +   +   +   +   +   +   +   + W    +
                W +   +   +   +   +   +   +   +   + W    +
                W +   +   +   +   +   +   +   +   + W    +
                W + W + W + W + W + W + W + W + W + W
            }.painter,
            contentDescription = null,
            modifier = Modifier.size(20.px)
        )

//        val parentBackground = 0xff313233.argb
//        Label(mcFormat { "111" .. "222".red.obfuscated .. "333" }, parentBackground = parentBackground)
//        Label("§n§9§o12§l§43§64§2§k5§16§d7§5.", parentBackground = parentBackground)
//        Label("§n§9§oCdm's§r »§l§4测§6试§r§2§k服§1务§d器§5.")
    
        var bold by remember { mutableStateOf(false) }
        RowGap {
            Toggle(bold, { bold = !bold })
            Toggle(!bold, { bold = !bold })
            Toggle(bold, {}, enabled = false)
        }

        @Composable
        fun ShowButtons(styles: ButtonStyles) = RowGap {
            val bolded = if (bold) styles.Bold else styles
            var activated by remember { mutableStateOf(true) }
            Button({}, styles = bolded) { Label("Test正常状态") }
            Button({}, styles = bolded, enabled = false) { Label("Test禁止状态") }
            Button({ activated = !activated }, styles = bolded, activated = activated) { Label("Test激活状态") }
        }
        ShowButtons(OreButtonStyles.Common)
        ShowButtons(OreButtonStyles.Gray)
        ShowButtons(OreButtonStyles.Green)
        ShowButtons(OreButtonStyles.Purple)
        ShowButtons(OreButtonStyles.Red)
        
        var showMark by remember { mutableStateOf(false) }
        Button({ showMark = !showMark }, Modifier.outline().sound()/*.animateContentSize()*/) {
            Icon(
                painter = OreIcons.Close.painter,
                contentDescription = null,
                modifier = Modifier.size(8.px)
            )
            Spacer(Modifier.size(8.px))
            Label(
                mcFormat {
                    "Cdm's".blue.underline.italic .. " »" .. ("测".dark_red + "试".gold).bold .. "服".dark_green .. "务".dark_blue .. "器".light_purple .. ".".dark_purple
                },
                showFormatMark = showMark
            )
//            Label("§n§9§oCdm's§r »§l§4测§6试§r§2服§1务§d器§5.", showFormatMark = showMark)
        }
//        
    }
    
    OreComposeExampleTheme {
        PanoramaBackground {
            val scrollState = rememberScrollState()
            Column(
                Modifier
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(vertical = 8.px),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.px),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Content()
                }
            }
            VerticalScrollbar(
                modifier = ScrollbarDefaults.VerticalModifier.align(Alignment.CenterEnd),
                state = scrollState
            )
        }
    }

}
