package com.linguaceleris.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.findVersionString(name: String) = findVersion(name).get().toString()

internal fun VersionCatalog.findLibraryString(name: String) = findLibrary(name).get()

internal fun VersionCatalog.findVersionInt(name: String) = findVersionString(name).toInt()
