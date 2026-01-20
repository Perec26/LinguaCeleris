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

            //TODO: убрать после того как обновиться хилт для работы с градл 9
            apply(plugin = "org.jetbrains.kotlin.android")

            apply(plugin = "com.android.application")
            apply(plugin = "linguaceleris.ktlint")
            apply(plugin = "linguaceleris.hilt")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
            apply(plugin = "com.google.gms.google-services")
            apply(plugin = "com.google.firebase.crashlytics")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersionInt("targetSdk")
            }

            dependencies {
                add("implementation", libs.findLibraryString("androidx.core.ktx"))
                add("implementation", platform(libs.findLibraryString("firebase.bom")))
                add("implementation", libs.findLibraryString("firebase.crashlytics"))
                // TODO: проверить все ли библиотеки навигации нужны
                add("implementation",libs.findLibraryString("kotlinx.serialization.json"))
                add("implementation", libs.findLibraryString("androidx.navigation3.runtime"))
                add("implementation", libs.findLibraryString("androidx.navigation3.ui"))
                add("implementation", libs.findLibraryString("androidx.lifecycle.viewmodel.navigation3"))
                add("implementation", libs.findLibraryString("androidx.material3.adaptive.navigation3"))
            }
        }
    }
}