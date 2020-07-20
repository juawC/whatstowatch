package com.juawapps.whatstowatch.movies.data.repository

import com.juawapps.whatstowatch.common.data.*
import com.juawapps.whatstowatch.movies.data.api.MoviesApi
import com.juawapps.whatstowatch.movies.data.database.MovieListItemDao
import com.juawapps.whatstowatch.movies.data.model.MovieDetailDTO
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesListItemDao: MovieListItemDao,
    private val moviesListMapper: ListMapper<MovieListItemDTO, MovieListItem>,
    private val moviesDetailsMapper: Mapper<MovieDetailDTO, MovieDetails>
) : MoviesRepository {

    override fun discoverMovies(): Flow<Resource<List<MovieListItem>>> = flowDataSource(
        fetchFromApi = { wrapWithResult { moviesApi.discoverMovies() }.map { it.items } },
        fetchFromDatabase = moviesListItemDao::getAll,
        saveInDatabase = moviesListItemDao::replaceAll,
        mapToDomain = moviesListMapper::map,
        mapToDatabase = { this }
    )

    override suspend fun getMovieDetails(id: Long): Result<MovieDetails> {
        return wrapWithResult { moviesApi.getMovieDetails(id) }.map(moviesDetailsMapper::map)
    }

    private fun <DomainType, DatabaseType, ApiType> flowDataSource(
        fetchFromApi: suspend () -> Result<ApiType>,
        fetchFromDatabase: suspend () -> DatabaseType,
        saveInDatabase: suspend (DatabaseType) -> Unit,
        mapToDomain: DatabaseType.() -> DomainType,
        mapToDatabase: ApiType.() -> DatabaseType
    ): Flow<Resource<DomainType>> = flow {
        val cachedData = fetchFromDatabase().mapToDomain()

        emit(cachedData.asResourceLoading())
        emit(
            fetchFromApi().suspendFold(
                ifFailure = { apiException -> apiException.asResourceError(cachedData) },
                ifSuccess = { apiData ->
                    saveInDatabase(apiData.mapToDatabase())
                    fetchFromDatabase().mapToDomain().asResourceSuccess()
                }
            )
        )
    }
}