@file:Suppress("unused")

import com.android.build.gradle.LibraryExtension
import com.linguaceleris.convention.configureKotlinAndroid
import com.linguaceleris.convention.findVersionInt
import com.linguaceleris.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            //TODO: убрать после того как обновиться хилт для работы с градл 9
            apply(plugin = "org.jetbrains.kotlin.android")

            apply(plugin = "com.android.library")
            apply(plugin = "linguaceleris.ktlint")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersionInt("targetSdk")
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}