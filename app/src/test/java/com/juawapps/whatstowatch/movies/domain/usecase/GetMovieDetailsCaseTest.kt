package com.juawapps.whatstowatch.movies.domain.usecase

import com.appmattus.kotlinfixture.kotlinFixture
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.repository.MoviesRepository
import com.juawapps.whatstowatch.util.TestCoroutineRule
import com.juawapps.whatstowatch.util.asList
import com.juawapps.whatstowatch.util.asSuccess
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
class GetMovieDetailsCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var moviesRepository: MoviesRepository

    val fixture = kotlinFixture()

    private lateinit var getMovieDetailsCase: GetMovieDetailsUseCase

    private val aMovieDetails = fixture<MovieDetails>()
    private val anId = 0L

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getMovieDetailsCase = GetMovieDetailsUseCase(moviesRepository)
    }

    @Test
    fun `invoke() when repository returns a successful response it gets a successful response`() =
        testCoroutineRule.runBlockingTest {

            coEvery {
                moviesRepository.getMovieDetails(anId)
            } returns aMovieDetails.asSuccess()

            val result = getMovieDetailsCase.invoke(anId)

            assertEquals(
                aMovieDetails.asSuccess(),
                result
            )
        }
}