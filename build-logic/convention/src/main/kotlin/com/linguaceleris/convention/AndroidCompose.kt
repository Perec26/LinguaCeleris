package com.linguaceleris.convention


import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.resolver.BUILD_SRC_SOURCE_ROOTS_FILE_PATH
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension,
) {

    commonExtension.apply {
        buildFeatures.compose = true

        dependencies {
            val bom = libs.findLibraryString("androidx-compose-bom")
            add("implementation", platform(bom))
            add("implementation", libs.findLibraryString("androidx-activity-compose"))
            add("implementation", libs.findLibraryString("androidx-compose-ui-tooling-preview"))
            add("implementation", libs.findLibraryString("androidx-compose-ui"))
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
        fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
            rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

        project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        stabilityConfigurationFiles.addAll(
            rootProject.layout.projectDirectory.file("compose_compiler_config.conf")
        )
    }
}