package vip.cdms.orecompose.effect

import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope

actual suspend fun playSound(path: String) {
}

actual fun Modifier.sound(
    path: String?,
    coroutineScope: CoroutineScope
) = this
