package vip.cdms.orecompose.utils

enum class Platform {
    Android,
    Desktop,
    WasmJs
}
expect val currentPlatform: Platform
