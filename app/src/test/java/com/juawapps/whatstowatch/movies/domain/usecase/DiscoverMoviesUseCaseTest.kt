package com.juawapps.whatstowatch.movies.domain.usecase

import com.appmattus.kotlinfixture.kotlinFixture
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import com.juawapps.whatstowatch.util.TestCoroutineRule
import com.juawapps.whatstowatch.util.asList
import com.juawapps.whatstowatch.util.asSuccess
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DiscoverMoviesUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var moviesRepository: MoviesRepository

    val fixture = kotlinFixture()

    private lateinit var discoverMoviesUseCase: DiscoverMoviesUseCase

    private val aMovieItemsList = fixture<List<MovieListItem>>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        discoverMoviesUseCase = DiscoverMoviesUseCase(moviesRepository)
    }

    @Test
    fun `invoke() when repository returns a successful response it gets a successful response`() =
        testCoroutineRule.runBlockingTest {

            every {
                moviesRepository.discoverMovies()
            } returns aMovieItemsList.asSuccess().asList().asFlow()

            val resultFlow = discoverMoviesUseCase.invoke()

            assertEquals(
                aMovieItemsList.asSuccess().asList(),
                resultFlow.toList()
            )
        }
}