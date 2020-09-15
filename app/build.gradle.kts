plugins {
    androidApplication
    kotlin(kotlinAndroid)
    kotlin(kotlinAndroidExtension)
    kotlin(kotlinKapt)
    safeArgs
    daggerHilt
}

android {
    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Version.minSdkVersion)
        compileSdkVersion(Config.Version.compileSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
        versionCode = Config.Version.versionCode
        versionName = Config.Version.versionName
        multiDexEnabled = Config.isMultiDexEnabled
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
    }

    androidExtensions {
        isExperimental = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    @Suppress("UnstableApiUsage")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementAll(Dependencies.View.components)
    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.Network.moshi)
    implementation(Dependencies.DI.daggerHiltAndroid)
    implementAll(Dependencies.AndroidX.components)

    kapt(Dependencies.DI.AnnotationProcessor.daggerHiltAndroid)

    implementAll(Dependencies.Coroutines.components)
    implementation(Dependencies.FlowBinding.android)

    implementAll(Dependencies.Utils.components)

    implementAll(Dependencies.Firebase.components)

    // Testing Dependencies
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.testExt)
    androidTestImplementation(Dependencies.Test.espresso)
    testImplementation(Dependencies.Test.coroutinesTest)
    androidTestImplementation(Dependencies.DI.hiltAndroidTesting)
    kaptAndroidTest(Dependencies.DI.AnnotationProcessor.daggerHiltAndroid)
}