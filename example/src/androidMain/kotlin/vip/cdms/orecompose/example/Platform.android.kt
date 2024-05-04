package vip.cdms.orecompose.example

import android.os.Build

actual fun getPlatform() = "Android ${Build.VERSION.SDK_INT}"
