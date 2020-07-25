package com.juawapps.whatstowatch.movies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmattus.kotlinfixture.kotlinFixture
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.DefaultViewStateStore
import com.juawapps.whatstowatch.movies.domain.model.MovieDetails
import com.juawapps.whatstowatch.movies.domain.usecase.GetMovieDetailsUseCase
import com.juawapps.whatstowatch.util.TestCoroutineRule
import com.juawapps.whatstowatch.util.ViewModelTestRule
import com.juawapps.whatstowatch.util.asError
import com.juawapps.whatstowatch.util.asSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get: Rule
    val viewModelTestRule = ViewModelTestRule<MoviesDetailsViewState, MovieDetailsViewEffect>()

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private val fixture = kotlinFixture()
    private val aMovieDetails = fixture<MovieDetails>()
    private val aMovieDetailsUiItem = MovieDetailsUiItem.create(aMovieDetails)
    private val aSecondMovieDetails = fixture<MovieDetails>()
    private val aSecondMovieDetailsUiItem = MovieDetailsUiItem.create(aSecondMovieDetails)
    private val aBaseViewState = MoviesDetailsViewState()
    private val aMovieId = 1L

    private val anError: Exception = Exception()

    @Before
    fun setup() {
        getMovieDetailsUseCase = mockk()
    }

    private fun initViewModel(
        initialViewState: MoviesDetailsViewState = aBaseViewState
    ) {
        val viewStateStore = DefaultViewStateStore<MoviesDetailsViewState, MovieDetailsViewEffect>(
            initialViewState
        )
        viewModelTestRule.observe(viewStateStore)
        viewModel = MovieDetailsViewModel(
            aMovieId,
            getMovieDetailsUseCase,
            viewStateStore
        )
    }

    @Test
    fun `init() when getMovieDetailsUseCase succeeds it shows the movie details`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                getMovieDetailsUseCase.invoke(aMovieId)
            } returns aMovieDetails.asSuccess()

            // Act
            initViewModel()

            // Assert
            viewModelTestRule.assertViewStateSequence(
                aBaseViewState,
                aBaseViewState.copy(isLoading = true),
                aBaseViewState.copy(
                    isLoading = false,
                    movie = aMovieDetailsUiItem
                )
            )
        }

    @Test
    fun `init() when getMovieDetailsUseCase fails it shows the movie details`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                getMovieDetailsUseCase.invoke(aMovieId)
            } returns anError.asError()

            // Act
            initViewModel()

            // Assert
            viewModelTestRule.assertViewStateSequence(
                aBaseViewState,
                aBaseViewState.copy(isLoading = true),
                aBaseViewState.copy(
                    isLoading = false,
                    errorMessage = R.string.error_message
                )
            )
        }

    @Test
    fun `refresh() when getMovieDetailsUseCase succeeds it shows the movie details`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                getMovieDetailsUseCase.invoke(aMovieId)
            } returnsMany listOf(aMovieDetails.asSuccess(), aSecondMovieDetails.asSuccess())
            initViewModel()

            // Act
            viewModel.refresh()

            // Assert
            viewModelTestRule.assertViewStateSequence(
                aBaseViewState,
                aBaseViewState.copy(isLoading = true),
                aBaseViewState.copy(
                    isLoading = false,
                    movie = aMovieDetailsUiItem
                ),
                aBaseViewState.copy(
                    isRefreshing = true,
                    movie = aMovieDetailsUiItem
                ),
                aBaseViewState.copy(
                    isRefreshing = false,
                    movie = aSecondMovieDetailsUiItem
                )
            )
        }


    @Test
    fun `refresh() when getMovieDetailsUseCase fails it triggers an error message`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            coEvery {
                getMovieDetailsUseCase.invoke(aMovieId)
            } returnsMany listOf(aMovieDetails.asSuccess(), anError.asError())
            initViewModel()

            // Act
            viewModel.refresh()

            // Assert
            viewModelTestRule.assertViewStateSequence(
                aBaseViewState,
                aBaseViewState.copy(isLoading = true),
                aBaseViewState.copy(
                    isLoading = false,
                    movie = aMovieDetailsUiItem
                ),
                aBaseViewState.copy(
                    isRefreshing = true,
                    movie = aMovieDetailsUiItem
                ),
                aBaseViewState.copy(
                    isRefreshing = false,
                    movie = aMovieDetailsUiItem
                )
            )
            viewModelTestRule.hadEffect(
                MovieDetailsViewEffect.ShowErrorMessage
            )
        }
}