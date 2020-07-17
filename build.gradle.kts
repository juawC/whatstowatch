buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(GradlePluginId.ANDROID_GRADLE_CLASSPATH)
        classpath(GradlePluginId.KOTLIN_GRADLE_CLASSPATH)
        classpath(GradlePluginId.SAFE_ARGS_GRADLE_CLASSPATH)
        classpath(GradlePluginId.HILT_CLASSPATH)
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}