package vip.cdms.orecompose.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import vip.cdms.orecompose.style.OreTheme
import vip.cdms.orecompose.style.px
import vip.cdms.orecompose.utils.argb
import vip.cdms.orecompose.utils.mcFormat

@OptIn(ExperimentalTextApi::class)
@Composable
@Preview
fun App() {
    
    @Composable
    fun RowGap(content: @Composable RowScope.() -> Unit) = Row(horizontalArrangement = Arrangement.spacedBy(8.px), content = content)
    @Composable
    fun Content() {
        
        Label("Hello, ${getPlatform()}!")
        
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
        
        Divider()
        
        var showMark by remember { mutableStateOf(false) }
        Button({ showMark = !showMark }, Modifier.outline().sound()/*.animateContentSize()*/) {
            Label(
                mcFormat {
                    "Cdm's".blue.underline.italic .. " »" .. ("测".dark_red.bold + "试".gold) .. "服".dark_green .. "务".dark_blue .. "器".light_purple .. ".".dark_purple
                },
                showFormatMark = showMark
            )
//            Label("§n§9§oCdm's§r »§l§4测§6试§r§2服§1务§d器§5.", showFormatMark = showMark)
        }
//        
    }
    
    
    
    OreTheme {
        // 居中显示而已
        Row(
            Modifier.fillMaxSize().background(0xff313233.argb),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.px),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Content()
                }
            }
        }
    }
    
}
