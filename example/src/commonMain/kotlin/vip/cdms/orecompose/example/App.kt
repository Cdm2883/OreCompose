package vip.cdms.orecompose.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import vip.cdms.orecompose.components.*
import vip.cdms.orecompose.effect.outline
import vip.cdms.orecompose.effect.sound
import vip.cdms.orecompose.layout.TopBar
import vip.cdms.orecompose.layout.TopBarActionButton
import vip.cdms.orecompose.layout.TopBarLargeHeight
import vip.cdms.orecompose.layout.TopBarNavigationButton
import vip.cdms.orecompose.style.*
import vip.cdms.orecompose.utils.*
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalTextApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
@Preview
fun App() {
    val windowSizeClass = calculateWindowSizeClass()
    
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

        Button(
            rangeSize = OreButtonSizes.Compact,
            contentPadding = OreButtonPaddings.Compact,
        ) { Label("Test中文测试") }

        @Composable
        fun ShowButtons(styles: ButtonStyles) = RowGap {
            val bolded = if (bold) styles.Bold else styles
            var activated by remember { mutableStateOf(true) }
            val small = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
            var enabled by remember { mutableStateOf(true) }
            if (!small) Button({}, styles = bolded) { Label("Test正常状态") }
            else LaunchedEffect(Unit) {
                tickerFlow(2.seconds).collect { enabled = !enabled }
            }
            Button({}, styles = bolded, enabled = small && enabled) { Label("Test${if (enabled) "正常" else "禁止"}状态") }
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
            Column(Modifier.fillMaxSize()) {

                TopBar(
                    modifier = if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
                        Modifier.height(TopBarLargeHeight) else Modifier,
                    navigation = {
                        TopBarNavigationButton(
                            painter = OreIcons.Back.painter,
                            contentDescription = null,
                            contentPadding = PaddingValues(5.px),
                        ) {}
                    },
                    actions = {
                        TopBarActionButton(
                            painter = buildPixelIcon(10) @Suppress("LocalVariableName") {
                                val P1_H = 0xff8a6294.argb; val P2_H = 0xff442515.argb; val P2_B = 0xff2e180e.argb
                                val P1_S = 0xffab724c.argb; val P2_S = 0xffb9674a.argb
                                val P1_E = 0xff1c1c1c.argb; val P2_E = 0xff1c1c1c.argb
                                val P3_H = 0xff221611.argb; val P4_H = 0xff2f2f2f.argb
                                val P3_S = 0xff7e5337.argb; val P4_S = 0xfff29f5f.argb
                                val P3_E = 0xff1c1c1c.argb; val P4_E = 0xff202020.argb

                                P1_H+P1_H+P1_H+P1_H+P1_H + P2_H+P2_B+P2_H+P2_B+P2_H +
                                P1_H+P1_H+P1_H+P1_H+P1_H + P2_H+P2_S+P2_S+P2_S+P2_H +
                                P1_H+P1_S+P1_S+P1_S+P1_H + P2_S+P2_S+P2_S+P2_S+P2_S +
                                P1_H+P1_E+P1_S+P1_E+P1_H + P2_S+P2_E+P2_S+P2_E+P2_S +
                                P1_H+P1_S+P1_S+P1_S+P1_H + P2_S+P2_S+P2_S+P2_S+P2_S +

                                P3_H+P3_H+P3_H+P3_H+P3_H + P4_H+P4_H+P4_H+P4_H+P4_H +
                                P3_H+P3_H+P3_H+P3_H+P3_H + P4_H+P4_H+P4_H+P4_H+P4_H +
                                P3_H+P3_S+P3_S+P3_S+P3_H + P4_H+P4_H+P4_H+P4_H+P4_H +
                                P3_S+P3_E+P3_S+P3_E+P3_S + P4_S+P4_E+P4_S+P4_E+P4_S +
                                P3_S+P3_S+P3_S+P3_S+P3_S + P4_S+P4_S+P4_S+P4_S+P4_S
                            }.padding(1, OreColors.Outline).painter,
                            contentDescription = "好友",
                            visibleDescription = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
                        ) {}
                    }
                ) {
                    Label("标题")
                }

                Box(Modifier.fillMaxSize()) {
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
    }

}
