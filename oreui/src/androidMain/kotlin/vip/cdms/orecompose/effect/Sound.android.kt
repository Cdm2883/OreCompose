package vip.cdms.orecompose.effect

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.MemoryFile
import android.os.ParcelFileDescriptor
import android.view.MotionEvent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import orecompose.oreui.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.io.FileDescriptor

private val audioAttributes by lazy {
    AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()
}
private val soundPool by lazy {
    SoundPool.Builder()
        .setMaxStreams(4)
        .setAudioAttributes(audioAttributes)
        .build()
}

private val cache = mutableMapOf<String, Int>()

@OptIn(ExperimentalResourceApi::class)
actual suspend fun playSound(path: String) {
//    val id = cache[path] ?: run {
//        val bytes = Res.readBytes(path)

//        val file = MemoryFile(path.hashCode().toString(), bytes.size)
//        file.writeBytes(bytes, 0, 0, bytes.size)
//        MemoryFile::class.java
//            .getDeclaredMethod("deactivate").apply { isAccessible = true }
//            .invoke(file)
//        val fd = MemoryFile::class.java
//            .getDeclaredMethod("getFileDescriptor")
//            .invoke(file) as FileDescriptor
//        val id = soundPool.load(fd, 0, bytes.size.toLong(), 0)

//        val pfd =
//            ParcelFileDescriptor::class.java
//                .getDeclaredMethod("fromData", ByteArray::class.java, String::class.java)
//                .invoke(null, bytes, path.hashCode().toString()) as ParcelFileDescriptor
//        val id = soundPool.load(pfd.fileDescriptor, 0, bytes.size.toLong(), 0)

//        val pipe = ParcelFileDescriptor.createPipe()
//        val writeSide = pipe[1]
//        val pipeOut = FileOutputStream(writeSide.fileDescriptor)
//        pipeOut.write(bytes)
//        pipeOut.flush()
//        val id = soundPool.load(pipe[0].fileDescriptor, 0, bytes.size.toLong(), 0)

//        id
//    }
//    soundPool.play(id, 1f, 1f, 1, -1, 1f)
}

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.sound(
    path: String?,
    coroutineScope: CoroutineScope
) = path?.let {
    pointerInteropFilter { event ->
        if (event.action == MotionEvent.ACTION_DOWN)
            coroutineScope.launch { playSound(path) }
        false
    }
} ?: this
