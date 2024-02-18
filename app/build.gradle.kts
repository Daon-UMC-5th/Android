import java.util.Properties;
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    "kotlin-android"
}
var properties:Properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
var serverURl = properties.getProperty("BASE_URL")

android {
    namespace = "com.example.daon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.daon"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", serverURl)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        buildFeatures{
            viewBinding = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //implementation("com.github.prolificinteractive:material-calendarview:1.6.0")
    implementation("com.jakewharton.threetenabp:threetenabp:1.2.1")

    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.databinding:databinding-runtime:8.2.2")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:(latest version)")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}