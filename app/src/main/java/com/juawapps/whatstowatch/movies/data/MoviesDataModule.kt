package com.juawapps.whatstowatch.movies.data

import com.juawapps.whatstowatch.common.data.Mapper
import com.juawapps.whatstowatch.movies.data.api.MoviesApi
import com.juawapps.whatstowatch.movies.data.mapper.MovieDetailMapper
import com.juawapps.whatstowatch.movies.data.mapper.MovieListItemMapper
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.data.repository.MoviesRepositoryImpl
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
abstract class MoviesDataModule {

    @Binds
    internal abstract fun moviesRepository(repository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    internal abstract fun movieListMapper(mapper: MovieListItemMapper): Mapper<MovieListItemDTO, MovieListItem>

    @Binds
    internal abstract fun movieDetailsMapper(mapper: MovieDetailMapper): Mapper<MovieListItemDTO, MovieDetails>

    companion object {
        @Provides
        internal fun provideApi(
            retrofit: Retrofit
        ): MoviesApi {
            return retrofit.create(MoviesApi::class.java)
        }
    }
}
