package vip.cdms.orecompose.example

import android.os.Build

actual fun getPlatform(): Platform = Platform.Android("Android ${Build.VERSION.SDK_INT}")
