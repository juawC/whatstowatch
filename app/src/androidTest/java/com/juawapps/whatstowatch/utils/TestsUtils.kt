package com.juawapps.whatstowatch.utils

import android.app.Application
import android.util.Log
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher

fun getJsonFromAssets(path: String): String? {
    val context = ApplicationProvider.getApplicationContext<Application>()
    Log.d("JsonFromAssets", "Reading path: $path")
    return context.assets.open(path).bufferedReader().use { it.readText() }
}

fun pauseScreen(milliseconds: Long = 60000) {
    onView(isRoot()).perform(LoopMainThread(milliseconds))
}

class LoopMainThread(private val milliseconds: Long) : ViewAction {
    override fun getDescription() = "Looping main thread!"
    override fun getConstraints(): Matcher<View> = ViewMatchers.isDisplayed()

    override fun perform(uiController: UiController?, view: View?) {
        uiController?.loopMainThreadForAtLeast(milliseconds)
    }
}
