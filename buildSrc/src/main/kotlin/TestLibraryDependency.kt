object TestLibraryDependency {
    private object Version {
        const val JUNIT = "4.12"
        const val JUNIT_4_13 = "4.13"
        const val ANDROID_TEST_JUNIT = "1.1.2-rc01"
        const val TEST_RUNNER = "1.2.0"
        const val TEST_RULES = "1.2.0"
        const val TEST_CORE = "1.2.0"
        const val TEST_ARCH = "2.1.0"
        const val ESPRESSO_CORE = "3.2.0"
        const val MOCKK = "1.9.3"
        const val ANDROID_X_TEST = "1.2.0"
        const val COROUTINES_ANDROID = "1.3.2"
        const val FRAGMENT_VERSION = "1.2.5"
    }

    // Base framework
    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val JUNIT_4_13 = "junit:junit:${Version.JUNIT_4_13}"
    const val ANDROID_TEST_JUNIT = "androidx.test.ext:junit:${Version.ANDROID_TEST_JUNIT}"
    const val TEST_CORE = "androidx.test:core:${Version.TEST_CORE}"
    const val TEST_ARCH = "androidx.arch.core:core-testing:${Version.TEST_ARCH}"
    const val TEST_RUNNER = "androidx.test:runner:${Version.TEST_RUNNER}"
    const val TEST_RULES = "androidx.test:rules:${Version.TEST_RULES}"
    const val ANDROID_X_TEST_KTS = "androidx.test:core-ktx:${Version.ANDROID_X_TEST}"
    const val TEST_ORCHESTRATOR = "androidx.test:orchestrator:${Version.TEST_RUNNER}"

    // Mock
    const val MOCKK = "io.mockk:mockk:${Version.MOCKK}"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:${Version.MOCKK}"

    // Espresso / UI
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Version.ESPRESSO_CORE}"
    const val FRAGMENT_TESTING = "androidx.fragment:fragment-testing:${Version.FRAGMENT_VERSION}"
    const val HILT_TESTING = "com.google.dagger:hilt-android-testing:${CommonVersion.HILT}"
    const val HILT_TESTING_COMPILER = "com.google.dagger:hilt-android-compiler:${CommonVersion.HILT}"

    // Coroutines
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.COROUTINES_ANDROID}"
}
