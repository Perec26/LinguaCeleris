// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

dependencies {
    kover(project(":feature:auth:impl"))
    kover(project(":feature:home:impl"))
    kover(project(":feature:settings:impl"))
    kover(project(":feature:start:impl"))
    kover(project(":feature:quiz:impl"))
}

kover.reports {
    total {
        filters.excludes {
            androidGeneratedClasses()
            classes(
                "*_Factory*",
                "*_HiltModules*",
                "*NavKey*",
                "*EntryProviderKt*",
                "**.*ComposableSingletons*",
                "**.*Preview*",
                "*Mock*"
            )
            annotatedBy(
                "com.linguaceleris.testing.ExcludeFromKover",
                "com.linguaceleris.testing.PendingUiTests",
            )
        }
    }

    verify {
        rule {
            minBound(80)
        }
    }
}
