package vip.cdms.orecompose.utils

private object CurrentPlatform : Platform.WasmJs
actual fun getPlatform(): Platform = CurrentPlatform
