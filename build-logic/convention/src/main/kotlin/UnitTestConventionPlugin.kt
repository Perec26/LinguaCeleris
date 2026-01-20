@file:Suppress("unused")

import com.android.build.api.dsl.LibraryExtension
import com.linguaceleris.convention.findLibraryString
import com.linguaceleris.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class UnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                testOptions.unitTests.all {
                    it.useJUnitPlatform()
                }
            }

            dependencies {
                add("testImplementation", libs.findLibraryString("kotest"))
                add("testImplementation", libs.findLibraryString("mockk"))
                add("testImplementation", libs.findLibraryString("coroutines.test"))
                add("testImplementation", libs.findLibraryString("kotest.assertions.core"))
                add("testImplementation", libs.findLibraryString("turbine"))
            }
        }
    }
}