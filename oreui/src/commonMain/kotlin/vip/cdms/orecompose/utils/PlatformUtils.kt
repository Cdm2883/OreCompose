package vip.cdms.orecompose.utils

sealed interface Platform {
    interface Jvm : Platform
    interface Android : Platform
    interface Web : Platform
    interface Js : Web
    interface WasmJs : Web
}

internal expect fun getPlatform(): Platform
val RuntimePlatform = getPlatform()
