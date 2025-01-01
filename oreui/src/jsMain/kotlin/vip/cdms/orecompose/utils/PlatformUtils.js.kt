package vip.cdms.orecompose.utils

private object CurrentPlatform : Platform.Js
actual fun getPlatform(): Platform = CurrentPlatform
