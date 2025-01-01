package vip.cdms.orecompose.utils

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get

actual suspend fun loadResource(fontFamilyResolver: FontFamily.Resolver, resource: ResourceUrl) {
    val response = window.fetch(resource.url).await()
    if (!resource.isFont) return
    val fontBytes = response.arrayBuffer().await().toByteArray()
    val fontFamily = FontFamily(Font(resource.fileName.split(".").first(), fontBytes))
    fontFamilyResolver.preload(fontFamily)
}

fun ArrayBuffer.toByteArray(): ByteArray {
    val uint8Array = Uint8Array(this)  // k/js pretty simple :)
    return ByteArray(uint8Array.length) { i -> uint8Array[i] }
}
