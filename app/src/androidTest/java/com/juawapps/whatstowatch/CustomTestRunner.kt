package com.juawapps.whatstowatch

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.juawapps.whatstowatch.utils.di.HiltTestApplication_Application

class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication_Application::class.java.name, context)
    }
}
