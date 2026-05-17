plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("hilt") {
            id = "linguaceleris.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidApplication") {
            id = "linguaceleris.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "linguaceleris.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("ktlint") {
            id = "linguaceleris.ktlint"
            implementationClass = "KtlintConventionPlugin"
        }
        register("androidLibrary") {
            id = "linguaceleris.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "linguaceleris.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("unitTest") {
            id = "linguaceleris.unittest"
            implementationClass = "UnitTestConventionPlugin"
        }
        register("featureApi") {
            id = "linguaceleris.feature.api"
            implementationClass = "FeatureApiConventionPlugin"
        }
        register("featureImpl") {
            id = "linguaceleris.feature.impl"
            implementationClass = "FeatureImplConventionPlugin"
        }

        register("data") {
            id = "linguaceleris.data"
            implementationClass = "DataConventionPlugin"
        }

        register("jvmLibrary") {
            id = "linguaceleris.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
