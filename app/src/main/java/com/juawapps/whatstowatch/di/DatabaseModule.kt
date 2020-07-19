package com.juawapps.whatstowatch.di

import android.content.Context
import androidx.room.Room
import com.juawapps.whatstowatch.common.data.AppDatabase
import com.juawapps.whatstowatch.movies.data.database.MovieListItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * AppDatabase module
 */
@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    internal fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    internal fun provideListItemDao(database: AppDatabase): MovieListItemDao {
        return database.movieListItemDao()
    }
}