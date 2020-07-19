object LibraryDependency {
    private object Version {
        const val RETROFIT = "2.6.2"
        const val RECYCLER_VIEW = "1.0.0"
        const val COORDINATOR_LAYOUT = "1.0.0"
        const val MATERIAL = "1.2.0-alpha05"
        const val CONSTRAINT_LAYOUT = "1.1.3"
        const val CORE_KTX = "1.1.0"
        const val FRAGMENT_KTX = "1.1.0"
        const val FRAGMENT = "1.1.0"
        const val LIFECYCLE_KTX = "2.2.0"
        const val COIL = "0.9.5"
        const val MOSHI = "1.9.2"
        const val ARROW = "0.10.4"
        const val SPIN_KIT = "1.4.0"
        const val SWIPE_REFRESH_LAYOUT = "1.0.0"
    }

    // Android
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Version.RECYCLER_VIEW}"
    const val COORDINATOR_LAYOUT = "androidx.coordinatorlayout:coordinatorlayout:${Version.COORDINATOR_LAYOUT}"
    const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Version.FRAGMENT_KTX}"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:${Version.FRAGMENT}"
    const val LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.LIFECYCLE_KTX}"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFECYCLE_KTX}"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${CommonVersion.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${CommonVersion.NAVIGATION}"
    const val SUPPORT_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:${Version.SWIPE_REFRESH_LAYOUT}"

    // Network
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
    const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Version.MOSHI}"
    const val MOSHI_CODE_GEM = "com.squareup.moshi:moshi-kotlin-codegen:${Version.MOSHI}"
    const val RETROFIT_MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${Version.RETROFIT}"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${CommonVersion.OKHTTP}"

    // UI
    const val COIL = "io.coil-kt:coil:${Version.COIL}"
    const val SPIN_KIT = "com.github.ybq:Android-SpinKit:${Version.SPIN_KIT}"

    // DI
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${CommonVersion.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${CommonVersion.HILT}"

    // General purpose
    const val ARROW = "io.arrow-kt:arrow-core:${Version.ARROW}"
}
