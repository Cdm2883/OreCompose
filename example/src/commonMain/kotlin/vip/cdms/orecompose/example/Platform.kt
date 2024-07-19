package vip.cdms.orecompose.example

sealed class Platform(val message: String) {
    class Android(message: String) : Platform(message)
    class Desktop(message: String) : Platform(message)
    class WasmJs(message: String) : Platform(message)
}
expect fun getPlatform(): Platform
