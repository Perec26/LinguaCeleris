@file:Suppress("unused")

import com.android.build.api.dsl.ApplicationExtension
import com.linguaceleris.convention.configureKotlinAndroid
import com.linguaceleris.convention.findLibraryString
import com.linguaceleris.convention.findVersionInt
import com.linguaceleris.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
            apply(plugin = "com.google.gms.google-services")
            apply(plugin = "com.google.firebase.crashlytics")
            apply(plugin = "linguaceleris.ktlint")
            apply(plugin = "linguaceleris.hilt")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersionInt("targetSdk")
            }

            dependencies {
                "implementation"(libs.findLibraryString("androidx.core.ktx"))
                "implementation"(platform(libs.findLibraryString("firebase.bom")))
                "implementation"(libs.findLibraryString("firebase.crashlytics"))
                // TODO: проверить все ли библиотеки навигации нужны
                "implementation"(libs.findLibraryString("kotlinx.serialization.json"))
                "implementation"(libs.findLibraryString("androidx.navigation3.runtime"))
                "implementation"(libs.findLibraryString("androidx.navigation3.ui"))
                "implementation"(libs.findLibraryString("androidx.lifecycle.viewmodel.navigation3"))
                "implementation"(libs.findLibraryString("androidx.material3.adaptive.navigation3"))
            }
        }
    }
}
