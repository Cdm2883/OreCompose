package vip.cdms.orecompose.utils

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Font
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.fetch.Response
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

actual suspend fun loadResource(fontFamilyResolver: FontFamily.Resolver, resource: ResourceUrl) {
    val response = window.fetch(resource.url).await<Response>()
    if (!resource.isFont) return
    val fontBytes = response.arrayBuffer().await<ArrayBuffer>().toByteArray()
    val fontFamily = FontFamily(Font(resource.fileName.split(".").first(), fontBytes))
    fontFamilyResolver.preload(fontFamily)
}

// https://github.com/JetBrains/compose-multiplatform-core/pull/1400/files#diff-9d3f03189881301ef36eabc7a053f7f1184e4cc9b831c80a0d56424ed8a0c548R62
// https://github.com/JetBrains/compose-multiplatform-core/blob/7eabcc8e1bf32f1b0f5723def9576c7123b519bf/compose/mpp/demo/src/wasmJsMain/kotlin/androidx/compose/mpp/demo/main.js.kt#L83

fun ArrayBuffer.toByteArray(): ByteArray {
    val source = Int8Array(this, 0, byteLength)
    return jsInt8ArrayToKotlinByteArray(source)
}

internal fun jsInt8ArrayToKotlinByteArray(x: Int8Array): ByteArray {
    val size = x.length
    @OptIn(UnsafeWasmMemoryApi::class)
    return withScopedMemoryAllocator { allocator ->
        val memBuffer = allocator.allocate(size)
        val dstAddress = memBuffer.address.toInt()
        jsExportInt8ArrayToWasm(x, size, dstAddress)
        ByteArray(size) { i -> (memBuffer + i).loadByte() }
    }
}

//language=JavaScript
@JsFun("""
    (src, size, dstAddr) => {
        // noinspection JSUnresolvedReference
        const mem8 = new Int8Array(wasmExports.memory.buffer, dstAddr, size);
        mem8.set(src);
    }
""")
internal external fun jsExportInt8ArrayToWasm(src: Int8Array, size: Int, dstAddr: Int)
