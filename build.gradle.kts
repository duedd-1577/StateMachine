import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlin_version by extra("1.4.0-rc")
    repositories.applyDefault()
    dependencies {
        addPlugins(listOf())
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories.applyDefault()
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

subprojects {
    applySpotless
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs +=
            "-Xuse-experimental=" +
                    "kotlin.Experimental," +
                    "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                    "kotlinx.coroutines.InternalCoroutinesApi," +
                    "kotlinx.coroutines.FlowPreview"
    }
}

tasks.register("clean", Delete::class.java) {
    delete(buildDir)
}