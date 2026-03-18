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
include(":data:quiz")
include(":feature:login:api")
include(":feature:login:impl")
include(":feature:quiz-selection:api")
include(":feature:quiz-selection:impl")
include(":feature:quiz:api")
include(":feature:quiz:impl")
include(":feature:quiz-summary:api")
include(":feature:quiz-summary:impl")
include(":feature:start:api")
include(":feature:start:impl")
