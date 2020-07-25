package com.juawapps.whatstowatch.movies.ui.discover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appmattus.kotlinfixture.kotlinFixture
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.data.asResourceError
import com.juawapps.whatstowatch.common.data.asResourceLoading
import com.juawapps.whatstowatch.common.data.asResourceSuccess
import com.juawapps.whatstowatch.common.ui.DefaultViewStateStore
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.usecase.DiscoverMoviesUseCase
import com.juawapps.whatstowatch.util.TestCoroutineRule
import com.juawapps.whatstowatch.util.ViewModelTestRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DiscoverMoviesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get: Rule
    val viewModelTestRule = ViewModelTestRule<DiscoverMoviesViewState, DiscoverMoviesViewEffect>()

    @MockK
    private lateinit var viewModel: DiscoverMoviesViewModel
    @MockK
    private lateinit var discoverMoviesUseCase: DiscoverMoviesUseCase

    private val fixture = kotlinFixture()
    private val aMovieItemsList = fixture<List<MovieListItem>>()
    private val aMovieEmptyList = emptyList<MovieListItem>()
    private val aSecondMovieItemsList = fixture<List<MovieListItem>>()
    private val anError: Exception = Exception()
    private val aBaseViewState = DiscoverMoviesViewState()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun intViewModel(
        initialViewState: DiscoverMoviesViewState = aBaseViewState
    ) {
        val viewStateStore: DiscoverMoviesStateStore = DefaultViewStateStore(initialViewState)
        viewModelTestRule.observe(viewStateStore)
        viewModel = DiscoverMoviesViewModel(discoverMoviesUseCase, viewStateStore)
    }

    @Test
    fun `init() when discoverMoviesUseCase returns successfully it updates the view movies list`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            every {
                discoverMoviesUseCase.invoke()
            } returns listOf(
                aMovieEmptyList.asResourceLoading(),
                aMovieItemsList.asResourceSuccess()
            ).asFlow()

            // Act
            intViewModel()

            // Assert
            viewModelTestRule.assertViewStateSequence(
                aBaseViewState,
                aBaseViewState.copy(
                    isLoading = true
                ),
                aBaseViewState.copy(
                    isLoading = false,
                    movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
                )
            )
        }

    @Test
    fun `init() when discoverMoviesUseCase fails it updates the view with an error`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            every {
                discoverMoviesUseCase.invoke()
            } returns listOf(
                aMovieEmptyList.asResourceLoading(),
                anError.asResourceError(aMovieEmptyList)
            ).asFlow()

            // Act
            intViewModel()

            // Assert
            viewModelTestRule.assertViewStateSequence(
                aBaseViewState,
                aBaseViewState.copy(
                    isLoading = true
                ),
                aBaseViewState.copy(
                    isLoading = false,
                    errorMessage = R.string.error_message
                )
            )
        }

    @Test
    fun `refresh() when discoverMoviesUseCase returns successfully it updates the view movies list`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            every {
                discoverMoviesUseCase.invoke()
            } returns listOf(
                aMovieItemsList.asResourceLoading(),
                aSecondMovieItemsList.asResourceSuccess()
            ).asFlow()
            val viewStateWithMovieList = aBaseViewState.copy(
                movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
            )

            // Act
            intViewModel(initialViewState = viewStateWithMovieList)

            // Assert
            viewModelTestRule.assertViewStateSequence(
                viewStateWithMovieList,
                viewStateWithMovieList.copy(isRefreshing = true),
                viewStateWithMovieList.copy(
                    isRefreshing = false,
                    movies = aSecondMovieItemsList.map(MovieListUiItem.Factory::create)
                )
            )
        }

    @Test
    fun `refresh() when discoverMoviesUseCase fails and the view has a movie list it triggers an error message`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            every {
                discoverMoviesUseCase.invoke()
            } returns listOf(
                aMovieItemsList.asResourceLoading(),
                anError.asResourceError(aMovieItemsList)
            ).asFlow()
            val viewStateWithMovieList = aBaseViewState.copy(
                movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
            )

            // Act
            intViewModel(initialViewState = viewStateWithMovieList)

            // Assert
            viewModelTestRule.assertViewStateSequence(
                viewStateWithMovieList,
                viewStateWithMovieList.copy(isRefreshing = true),
                viewStateWithMovieList.copy(isRefreshing = false)

            )
            viewModelTestRule.hadEffect(
                DiscoverMoviesViewEffect.ShowErrorMessage
            )
        }

    @Test
    fun `tapOnMovie() triggers a NavigateToDetail event`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            every {
                discoverMoviesUseCase.invoke()
            } returns listOf(
                aMovieEmptyList.asResourceLoading(),
                aMovieItemsList.asResourceSuccess()
            ).asFlow()
            val aMovieId = aMovieItemsList.first().id
            intViewModel()

            // Act
            viewModel.tapOnMovie(aMovieId)

            // Assert
            viewModelTestRule.assertViewStateSequence(
                aBaseViewState,
                aBaseViewState.copy(isLoading = true),
                aBaseViewState.copy(
                    isLoading = false,
                    movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
                )
            )
            viewModelTestRule.hadEffect(
                DiscoverMoviesViewEffect.NavigateToDetail(aMovieId)
            )
        }
}