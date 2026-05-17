@file:Suppress("unused")

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class DataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "linguaceleris.android.library")
            apply(plugin = "linguaceleris.hilt")
            apply(plugin = "linguaceleris.unittest")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
        }
    }
}
