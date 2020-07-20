package com.juawapps.whatstowatch.movies.ui.discover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.appmattus.kotlinfixture.kotlinFixture
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.DefaultViewStateStore
import com.juawapps.whatstowatch.common.ui.Event
import com.juawapps.whatstowatch.movies.domain.model.MovieListItem
import com.juawapps.whatstowatch.movies.domain.usecase.DiscoverMoviesUseCase
import com.juawapps.whatstowatch.util.*
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DiscoverMoviesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    private lateinit var viewModel: DiscoverMoviesViewModel

    @MockK
    private lateinit var discoverMoviesUseCase: DiscoverMoviesUseCase

    @MockK
    private lateinit var viewStateObserver: Observer<DiscoverMoviesViewState>

    @MockK
    private lateinit var viewEffectObserver: Observer<Event<DiscoverMoviesViewEffect>>

    val fixture = kotlinFixture()

    private val aMovieItemsList = fixture<List<MovieListItem>>()
    private val aMovieEmptyList = emptyList<MovieListItem>()
    private val aSecondMovieItemsList = fixture<List<MovieListItem>>()
    private val anError: Exception = Exception()
    private val baseViewState = DiscoverMoviesViewState()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
        confirmVerified(viewStateObserver, viewEffectObserver)
    }

    private fun intViewModel(
        initialViewState: DiscoverMoviesViewState = baseViewState
    ) {
        val viewStateStore: DiscoverMoviesStateStore = DefaultViewStateStore(initialViewState)
        viewStateStore.apply {
            viewState.observeForever(viewStateObserver)
            viewEffect.observeForever(viewEffectObserver)
        }
        viewModel = DiscoverMoviesViewModel(discoverMoviesUseCase, viewStateStore)
    }

    @Test
    fun `init() when discoverMoviesUseCase returns a success it updates the view movies list`() =
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
            viewStateSequence(
                baseViewState,
                baseViewState.copy(
                    isLoading = true
                ),
                baseViewState.copy(
                    isLoading = false,
                    movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
                )
            )
        }

    @Test
    fun `init() when discoverMoviesUseCase returns a failure it updates the view with an error`() =
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
            viewStateSequence(
                baseViewState,
                baseViewState.copy(isLoading = true),
                baseViewState.copy(
                    isLoading = false,
                    errorMessage = R.string.error_message
                )
            )
        }

    @Test
    fun `refresh() when discoverMoviesUseCase returns a success it updates the view movies list`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            every {
                discoverMoviesUseCase.invoke()
            } returns listOf(
                aMovieItemsList.asResourceLoading(),
                aSecondMovieItemsList.asResourceSuccess()
            ).asFlow()
            val viewStateWithMovieList = baseViewState.copy(
                movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
            )

            // Act
            intViewModel(initialViewState = viewStateWithMovieList)

            // Assert
            viewStateSequence(
                viewStateWithMovieList,
                viewStateWithMovieList.copy(isRefreshing = true),
                viewStateWithMovieList.copy(
                    isRefreshing = false,
                    movies = aSecondMovieItemsList.map(MovieListUiItem.Factory::create)
                )
            )
        }

    @Test
    fun `refresh() when discoverMoviesUseCase returns a failure and the view has a movie list it triggers an error message`() =
        testCoroutineRule.runBlockingTest {
            // Arrange
            every {
                discoverMoviesUseCase.invoke()
            } returns listOf(
                aMovieItemsList.asResourceLoading(),
                anError.asResourceError(aMovieItemsList)
            ).asFlow()
            val viewStateWithMovieList = baseViewState.copy(
                movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
            )

            // Act
            intViewModel(initialViewState = viewStateWithMovieList)

            // Assert
            viewStateSequence(
                viewStateWithMovieList,
                viewStateWithMovieList.copy(isRefreshing = true),
                viewStateWithMovieList.copy(isRefreshing = false)

            )
            hadEffect(
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
            viewStateSequence(
                baseViewState,
                baseViewState.copy(isLoading = true),
                baseViewState.copy(
                    isLoading = false,
                    movies = aMovieItemsList.map(MovieListUiItem.Factory::create)
                )
            )
            hadEffect(
                DiscoverMoviesViewEffect.NavigateToDetail(aMovieId)
            )
        }

    private fun hadEffect(effect: DiscoverMoviesViewEffect) {
        verifySequence {
            viewEffectObserver.onChanged(effect.asEvent())
        }
    }

    private fun viewStateSequence(vararg viewStates: DiscoverMoviesViewState) {
        verifySequence {
            viewStates.forEach { viewStateObserver.onChanged(it) }
        }
    }
}