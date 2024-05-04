package vip.cdms.orecompose.effect

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import kotlinx.coroutines.*
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

private val caches by lazy { mutableMapOf<String, Clip>() }
actual suspend fun playSound(path: String) {
    caches[path] ?: withContext(Dispatchers.IO) {
        val bytes = getSound(path, false)
        val input = bytes.inputStream()
        val audio = AudioSystem.getAudioInputStream(input)
        val clip = AudioSystem.getClip()
        clip.open(audio)
        input.close()
        audio.close()
        clip
    }.start()
}

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.sound(
    path: String?,
    coroutineScope: CoroutineScope
) = path?.let {
    // https://github.com/JetBrains/compose-multiplatform/issues/3503
    onPointerEvent(PointerEventType.Press) {
        coroutineScope.launch { playSound(path) }
    }
} ?: this
