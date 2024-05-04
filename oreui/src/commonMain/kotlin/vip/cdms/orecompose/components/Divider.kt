package vip.cdms.orecompose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import vip.cdms.orecompose.style.px
import vip.cdms.orecompose.utils.argb

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    height: Dp = 2.px
) {
    Spacer(Modifier.height(height).fillMaxWidth().drawBehind {
        drawRect(0xff1d1e1f.argb)
        drawRect(0xff454647.argb, Offset.Zero.copy(y = size.height / 2), size.copy(height = size.height / 2))
    } then modifier)
}
