package com.juawapps.whatstowatch.utils.di

import com.juawapps.whatstowatch.di.annotation.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class ApiConfigsTestModule {

    @Provides
    @ApiConfig
    fun providesApiUrl(): String {
        return "http://localhost:8080/"
    }
}
