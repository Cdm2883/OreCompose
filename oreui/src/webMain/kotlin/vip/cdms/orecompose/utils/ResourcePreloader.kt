package vip.cdms.orecompose.utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.font.FontFamily
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.FontResource
import kotlin.reflect.KProperty0

val LocalPreloadResources = staticCompositionLocalOf<Array<ResourceUrl>?> { null }
/*value*/ class ResourceUrl(val url: String) {
    constructor(packaging: String, type: String, file: String) : this("$ROOT/$packaging/$type/$file")
    val fileName get() = url.split("/").last()
    val isFont get() = url.contains("/font/")
    companion object {
        const val ROOT = "./composeResources"

        val KProperty0<DrawableResource>.png inline get() = "$name.png"
        val KProperty0<DrawableResource>.jpg inline get() = "$name.jpg"
        val KProperty0<DrawableResource>.bmp inline get() = "$name.bmp"
        val KProperty0<DrawableResource>.webp inline get() = "$name.webp"
        val KProperty0<DrawableResource>.xml inline get() = "$name.xml"

        val KProperty0<FontResource>.ttf inline get() = "$name.ttf"
        val KProperty0<FontResource>.otf inline get() = "$name.otf"

        // stupid kmp reflect metadata, we need real qualifiedName >:(
        inline fun <reified T> T.resourcesUrl(packaging: String, suffix: String = "", file: T.() -> String) =
            ResourceUrl(packaging, T::class.simpleName!! + suffix, file(this))
    }
}

private val DisablePreloadResources = arrayOf<ResourceUrl>()
@Deprecated("Do NOT use this unless you know what are you doing now!", ReplaceWith("content()"))
@Composable
fun DisablePreloadResources(content: @Composable () -> Unit) = CompositionLocalProvider(LocalPreloadResources provides DisablePreloadResources, content)

@Composable
fun PreloadResources(vararg urls: /*don't allow value class*/ ResourceUrl, content: @Composable () -> Unit) {
    var preloadResources = LocalPreloadResources.current ?: emptyArray()
    if (preloadResources !== DisablePreloadResources) preloadResources += urls
    CompositionLocalProvider(LocalPreloadResources provides preloadResources, content)
}

val LocalPreloadResourcePlaceholder = staticCompositionLocalOf<(@Composable () -> Unit)?> { null }
@Composable
fun ResourcePreloader(content: @Composable () -> Unit) {
    val urls = LocalPreloadResources.current
    val fontFamilyResolver = LocalFontFamilyResolver.current

    var loaded by remember { mutableStateOf(urls == null || urls === DisablePreloadResources) }
    if (loaded) return content()
    else LocalPreloadResourcePlaceholder.current?.let { it() }

    LaunchedEffect(Unit) {
        urls!!.map { async { loadResource(fontFamilyResolver, it) } }.awaitAll()
        loaded = true
    }
}

expect suspend fun loadResource(fontFamilyResolver: FontFamily.Resolver, resource: ResourceUrl)
