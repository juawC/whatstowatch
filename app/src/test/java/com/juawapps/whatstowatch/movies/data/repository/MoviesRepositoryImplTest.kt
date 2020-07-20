package com.juawapps.whatstowatch.movies.data.repository

import com.appmattus.kotlinfixture.kotlinFixture
import com.juawapps.whatstowatch.common.data.*
import com.juawapps.whatstowatch.common.domain.DomainException
import com.juawapps.whatstowatch.movies.data.api.MoviesApi
import com.juawapps.whatstowatch.movies.data.database.MovieListItemDao
import com.juawapps.whatstowatch.movies.data.model.MovieDetailDTO
import com.juawapps.whatstowatch.movies.data.model.MovieListItemDTO
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.util.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesRepositoryImplTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var moviesApi: MoviesApi

    @MockK
    lateinit var moviesListItemDao: MovieListItemDao

    private val fixture = kotlinFixture()
    private val aMovieDetails = fixture<MovieDetails>()
    private val aMovieDetailsDto = fixture<MovieDetailDTO>()
    private val aMovieList = fixture<List<MovieListItem>>()
    private val aDtoMovieList = fixture<List<MovieListItemDTO>>()
    private val aDtoMovieEmptyList = emptyList<MovieListItemDTO>()
    private val aMovieEmptyList = emptyList<MovieListItem>()
    private val aSecondDtoMovieList = fixture<List<MovieListItemDTO>>()
    private val aSecondMovieList = fixture<List<MovieListItem>>()
    private val aHttp404Error =  DomainException.HttpError(404, "Response.error()")

    private val moviesListMapper: ListMapper<MovieListItemDTO, MovieListItem> = mockk {
        every { map(aDtoMovieList) } returns aMovieList
        every { map(aSecondDtoMovieList) } returns aSecondMovieList
        every { map(aDtoMovieEmptyList) } returns aMovieEmptyList
    }
    private val moviesDetailsMapper: Mapper<MovieDetailDTO, MovieDetails> = mockk {
        every { map(aMovieDetailsDto) } returns aMovieDetails
    }

    private lateinit var moviesRepositoryImpl: MoviesRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        moviesRepositoryImpl = MoviesRepositoryImpl(
            moviesApi,
            moviesListItemDao,
            moviesListMapper,
            moviesDetailsMapper
        )
    }

    @Test
    fun `discoverMovies() when database is empty and api returns a successful response it returns a successful Resource`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                moviesApi.discoverMovies()
            } returns aDtoMovieList.wrapWithListResponse().asResponseSuccess()

            coEvery {
                moviesListItemDao.getAll()
            } returnsMany listOf(aDtoMovieEmptyList, aSecondDtoMovieList)

            val expectedResult = listOf(
                aMovieEmptyList.asResourceLoading(),
                aSecondMovieList.asResourceSuccess()
            )

            // Act
            val resultFlow = moviesRepositoryImpl.discoverMovies()

            // Assert
            assertEquals(
                expectedResult,
                resultFlow.toList()
            )

            coVerify {
                moviesListItemDao.replaceAll(aDtoMovieList)
            }
        }

    @Test
    fun `discoverMovies() when database is empty and api returns an error response it returns an error Resource with empty list`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                moviesApi.discoverMovies()
            } returns 404.asResponseError()

            coEvery {
                moviesListItemDao.getAll()
            } returnsMany listOf(aDtoMovieEmptyList, aDtoMovieEmptyList)

            val expectedResult = arrayListOf(
                aMovieEmptyList.asResourceLoading(),
                aHttp404Error.asResourceError(aMovieEmptyList)
            )

            // Act
            val resultFlow = moviesRepositoryImpl.discoverMovies()

            // Assert
            assertEquals(
                expectedResult,
                resultFlow.toList()
            )
        }

    @Test
    fun `discoverMovies() when database is not empty and api returns an error it returns an error Resource with the current database data`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                moviesApi.discoverMovies()
            } returns 404.asResponseError()

            coEvery {
                moviesListItemDao.getAll()
            } returns aDtoMovieList

            val expectedResult = arrayListOf(
                aMovieList.asResourceLoading(),
                aHttp404Error.asResourceError(aMovieList)
            )

            // Act
            val resultFlow = moviesRepositoryImpl.discoverMovies()

            // Assert
            assertEquals(
                expectedResult,
                resultFlow.toList()
            )
        }

    @Test
    fun `getMovieDetails() when api returns a successful response it returns a successful Result`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                moviesApi.getMovieDetails(1)
            } returns aMovieDetailsDto.asResponseSuccess()

            // Act
            val result = moviesRepositoryImpl.getMovieDetails(1)

            // Assert
            assertEquals(
                aMovieDetails.asSuccess(),
                result
            )
        }

    @Test
    fun `getMovieDetails() when api returns an error response it returns a error Result`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                moviesApi.getMovieDetails(1)
            } returns 404.asResponseError()

            // Act
            val result = moviesRepositoryImpl.getMovieDetails(1)

            // Assert
            assertEquals(
                aHttp404Error.asError<MovieDetails, DomainException.HttpError>(),
                result
            )
        }

    @Test
    fun `getMovieDetails() when api returns a successful response but throws exception it returns a error Result`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            val aException = Exception()
            val aDomainException = DomainException.NetworkError(aException)

            coEvery {
                moviesApi.getMovieDetails(1)
            } throws aException

            // Act
            val result = moviesRepositoryImpl.getMovieDetails(1)

            // Assert
            assertEquals(
                aDomainException.asError<MovieDetails, DomainException.NetworkError>(),
                result
            )
        }
}