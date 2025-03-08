import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.components.resources)
            compileOnly(libs.jetbrains.annotations)
            implementation(projects.oreui)
        }

        val nonWebMain by creating {
            dependsOn(commonMain.get())
        }

        androidMain {
            dependsOn(nonWebMain)
            dependencies {
                //noinspection UseTomlInstead
                implementation("com.github.androidZzT:VRPanoramaView:1.0.1")
            }
        }

        val skikoMain by creating {
            dependsOn(commonMain.get())
        }
        jvmMain {
            dependsOn(nonWebMain)
            dependsOn(skikoMain)
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        val webMain by creating {
            dependsOn(skikoMain)
            dependencies {
                implementation(libs.jetbrains.annotations)
                implementation(libs.kilua.dom)
            }
        }
        wasmJsMain {
            dependsOn(webMain)
        }
        jsMain {
            dependsOn(webMain)
        }
    }

    compilerOptions.freeCompilerArgs.addAll(
        "-Xexpect-actual-classes",
    )
}

android {
    namespace = "vip.cdms.orecompose.layout.panorama"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.sdk.min.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
