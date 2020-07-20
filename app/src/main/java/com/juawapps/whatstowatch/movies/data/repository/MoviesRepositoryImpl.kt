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
        apiGetCall = { suspend { moviesApi.discoverMovies() }.wrapWithResult { it.items } },
        dbGetCall = moviesListItemDao::getAll,
        dbSaveCall = moviesListItemDao::replaceAll,
        mapToModel = moviesListMapper::map
    )

    override suspend fun getMovieDetails(id: Long): Result<MovieDetails> {
        return suspend { moviesApi.getMovieDetails(id) }.wrapWithResult(moviesDetailsMapper::map)
    }

    private fun <ModelType, ModelTypeDB> flowDataSource(
        apiGetCall: suspend () -> Result<ModelTypeDB>,
        dbGetCall: suspend () -> ModelTypeDB,
        dbSaveCall: suspend (ModelTypeDB) -> Unit,
        mapToModel: ModelTypeDB.() -> ModelType
    ): Flow<Resource<ModelType>> = flow {
        val cachedData = dbGetCall().mapToModel()

        emit(cachedData.asResourceLoading())
        emit(
            apiGetCall().suspendFold(
                ifFailure = { exception -> exception.asResourceError(cachedData) },
                ifSuccess = { data ->
                    dbSaveCall(data)
                    val updatedData = dbGetCall().mapToModel()
                    updatedData.asResourceSuccess()
                }
            )
        )
    }
}