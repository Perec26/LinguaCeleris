@file:Suppress("unused")

import com.linguaceleris.convention.findLibraryString
import com.linguaceleris.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "linguaceleris.android.library.compose")
            apply(plugin = "linguaceleris.hilt")
            apply(plugin = "linguaceleris.unittest")
            apply(plugin = "linguaceleris.ktlint")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                // TODO: проверить все ли библиотеки навигации нужны
                "implementation"(libs.findLibraryString("androidx.navigation3.runtime"))
                "implementation"(libs.findLibraryString("androidx.navigation3.ui"))
                "implementation"(libs.findLibraryString("androidx.lifecycle.viewmodel.navigation3"))
                "implementation"(libs.findLibraryString("androidx.material3.adaptive.navigation3"))

                "implementation"(libs.findLibraryString("androidx.hilt.navigation.compose"))
                "debugImplementation"(libs.findLibraryString("androidx.ui.tooling"))
                "implementation"(libs.findLibraryString("kotlinx.serialization.json"))
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:navigation"))
            }
        }
    }
}
