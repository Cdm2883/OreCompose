package vip.cdms.orecompose.utils

private object CurrentPlatform : Platform.Android
actual fun getPlatform(): Platform = CurrentPlatform
