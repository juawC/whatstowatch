object GradlePluginId {
    private object Versions {
        const val SAFE_ARGS = CommonVersion.NAVIGATION
        const val BUILD_TOOLS_VERSION = "4.0.0"
        const val KOTLIN_VERSION = "1.3.72"
    }

    const val ANDROID_GRADLE_CLASSPATH = "com.android.tools.build:gradle:${Versions.BUILD_TOOLS_VERSION}"
    const val KOTLIN_GRADLE_CLASSPATH = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}"
    const val SAFE_ARGS_GRADLE_CLASSPATH = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.SAFE_ARGS}"
    const val HILT_CLASSPATH = "com.google.dagger:hilt-android-gradle-plugin:${CommonVersion.HILT}"

    const val ANDROID_APPLICATION = "com.android.application"
    const val KOTLIN_ANDROID = "kotlin-android"
    const val KOTLIN_ANDROID_EXTENSIONS = "kotlin-android-extensions"
    const val KOTLIN_KAPT = "kotlin-kapt"
    const val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
    const val HILT = "dagger.hilt.android.plugin"
}
