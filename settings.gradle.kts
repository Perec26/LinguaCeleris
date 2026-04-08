@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LinguaCeleris"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":common:time")
include(":core:designsystem")
include(":core:media")
include(":core:navigation")
include(":core:network")
include(":core:services")
include(":core:ui")
include(":data:auth")
include(":data:settings")
include(":data:streak")
include(":data:quiz")
include(":feature:auth:api")
include(":feature:auth:impl")
include(":feature:home:api")
include(":feature:home:impl")
include(":feature:settings:api")
include(":feature:settings:impl")
include(":feature:quiz:api")
include(":feature:quiz:impl")
include(":feature:start:api")
include(":feature:start:impl")
