package vip.cdms.orecompose.utils

private object CurrentPlatform : Platform.Jvm
actual fun getPlatform(): Platform = CurrentPlatform

