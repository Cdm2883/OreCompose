package vip.cdms.orecompose.example

actual fun getPlatform(): Platform = Platform.Desktop("Java ${System.getProperty("java.version")}")
