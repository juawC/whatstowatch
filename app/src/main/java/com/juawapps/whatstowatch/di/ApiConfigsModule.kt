package com.juawapps.whatstowatch.di

import com.juawapps.whatstowatch.di.annotation.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class ApiConfigsModule {

    @Provides
    @ApiConfig
    fun providesApiUrl(): String {
        return "https://api.themoviedb.org/3/"
    }
}
