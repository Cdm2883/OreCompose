plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.dokka) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.vanniktech.maven.publish) apply false
}
