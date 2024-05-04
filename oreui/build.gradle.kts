import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.vanniktech.maven.publish)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "vip.cdms.orecompose"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
    signAllPublications()
    coordinates("vip.cdms", "orecompose", "1.0.0")

    pom {
        name.set(project.name)
        description.set("Compose Multiplatform component library for the new UI style of Minecraft Bedrock Edition.")
        inceptionYear.set("2024")
        url.set("https://github.com/Cdm2883/OreCompose/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("Cdm2883")
                name.set("Cdm2883")
                url.set("https://github.com/Cdm2883/")
            }
        }
        scm {
            url.set("https://github.com/Cdm2883/OreCompose/")
            connection.set("scm:git:git://github.com/Cdm2883/OreCompose.git")
            developerConnection.set("scm:git:ssh://git@github.com/Cdm2883/OreCompose.git")
        }
    }
}
