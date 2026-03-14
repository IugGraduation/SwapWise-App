import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
}

// Read the secrets.properties file at the top level
val secretsProperties = Properties()
val secretsPropertiesFile = rootProject.file("secrets.properties")
if (secretsPropertiesFile.exists()) {
    secretsProperties.load(secretsPropertiesFile.inputStream())
}

android {
    namespace = "com.sam.swapwise"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sam.swapwise"
        minSdk = 24
        targetSdk = 36
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Use the properties to create BuildConfig fields for ALL build types
        buildConfigField("String", "SUPABASE_URL", "${secretsProperties.getProperty("supabaseUrl")}")
        buildConfigField("String", "SUPABASE_KEY", "${secretsProperties.getProperty("supabaseKey")}")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // fix for Time.Instant not found, for Supabase
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":ui"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Hilt for Dependency Injection
    implementation(libs.dagger.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Compose support for Hilt

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Supabase
    implementation(platform(libs.supabase.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.auth.kt)
    implementation(libs.ktor.client.android)
    implementation(libs.storage.kt)

    // fix for Time.Instant not found, for Supabase
    coreLibraryDesugaring(libs.desugar.jdk.libs)


}
