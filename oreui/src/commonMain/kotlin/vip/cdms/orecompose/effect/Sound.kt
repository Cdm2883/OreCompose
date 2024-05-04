package vip.cdms.orecompose.effect

import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import orecompose.oreui.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

private val  caches by lazy { mutableMapOf<String, ByteArray>() }
@OptIn(ExperimentalResourceApi::class)
internal suspend fun getSound(path: String, cache: Boolean = true) =
    if (cache) caches[path] ?: Res.readBytes(path).apply { caches[path] = this }
    else Res.readBytes(path)
internal expect suspend fun playSound(path: String)

data class Sounds(
    val click: String? = null,
    val clickPop: String? = null,
    val toast: String? = null
) {
    companion object {
        suspend fun get(path: String, cache: Boolean = true) = getSound(path, cache)
        suspend fun play(path: String) = playSound(path)
    }
}
val DefaultSounds = Sounds()
val LocalSounds = staticCompositionLocalOf { DefaultSounds }

val LocalSoundEffectEnabled = staticCompositionLocalOf { true }
val LocalSoundEffect = compositionLocalOf<String?> { null }
@OptIn(InternalComposeApi::class)
fun Modifier.sound() = composed {
    if (LocalSoundEffectEnabled.current) sound(LocalSoundEffect.current, CoroutineScope(currentComposer.applyCoroutineContext))
    else this
}
@OptIn(DelicateCoroutinesApi::class)
expect fun Modifier.sound(path: String?, coroutineScope: CoroutineScope = GlobalScope): Modifier
