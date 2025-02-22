package vip.cdms.orecompose.gallery

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import vip.cdms.orecompose.components.Label
import vip.cdms.orecompose.effect.LocalOutlineColor
import vip.cdms.orecompose.effect.LocalOutlineWidth
import vip.cdms.orecompose.effect.outline
import vip.cdms.orecompose.gallery.ui.GalleryTheme
import vip.cdms.orecompose.layout.panorama.Panorama
import vip.cdms.orecompose.utils.accessibilityIndicator
import vip.cdms.orecompose.utils.toPx

@Composable
fun App() {
    GalleryTheme {
        CompositionLocalProvider(
            LocalOutlineColor provides Color.LightGray,
        ) {
            var screenWidth by remember { mutableStateOf(Float.MIN_VALUE) }
            Box(
                Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { screenWidth = it.boundsInParent().width },
                contentAlignment = Alignment.Center
            ) {
                Panorama()
                Column {
                    Label(
                        "OreCompose! 你好世界！",
                        color = Color.White,
                        fontSize = (screenWidth / 16).toPx()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(-LocalOutlineWidth.current.toDp())) {
                        @Composable
                        fun Modifier.buttonLike(onClick: () -> Unit) = remember { MutableInteractionSource() }.let { interactionSource ->
                            semantics { role = Role.Button }
                                .accessibilityIndicator(interactionSource)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = LocalIndication.current,
                                    onClick = onClick
                                )
                        }
                        Spacer(Modifier.size(40.dp)
                            .background(Color.Yellow)
                            .buttonLike {})
                        Spacer(Modifier.size(10.dp))
                        Spacer(Modifier.size(40.dp)
                            .background(Color.Red)
                            .outline()
                            .buttonLike {})
                        Spacer(Modifier.size(40.dp)
                            .background(Color.Green)
                            .outline()
                            .buttonLike {})
                        Spacer(Modifier.size(40.dp)
                            .background(Color.Blue)
                            .outline()
                            .buttonLike {})
                    }
                }
            }
        }
    }
}
