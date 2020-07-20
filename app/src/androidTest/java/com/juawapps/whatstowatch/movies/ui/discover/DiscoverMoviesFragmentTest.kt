package com.juawapps.whatstowatch.movies.ui.discover

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.fixtures.MovieListUiItemFixture
import com.juawapps.whatstowatch.screens.DiscoverMoviesScreen
import com.juawapps.whatstowatch.screens.LoadingScreen
import com.juawapps.whatstowatch.util.asEvent
import com.juawapps.whatstowatch.utils.createFactoryWithNavController
import com.juawapps.whatstowatch.utils.createNavControllerMock
import com.juawapps.whatstowatch.utils.toFactory
import com.juawapps.whatstowatch.utils.toLiveData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DiscoverMoviesFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockNavController: NavController = createNavControllerMock()

    private lateinit var fragmentScenario: FragmentScenario<DiscoverMoviesFragment>

    val movieListUiItemFixture = MovieListUiItemFixture()

    @Test
    fun itDisplaysMoviesList() {
        val aMovieListUiItem = movieListUiItemFixture {
            copy(
                title = "Greyhound",
                year = "2020",
                language = "en",
                voteAverage = "7.4"
            )
        }
        val aViewState = DiscoverMoviesViewState(
            movies = listOf(aMovieListUiItem)
        )
        val aViewModel: DiscoverMoviesViewModel = mockk(relaxed = true) {
            every { viewState } returns aViewState.toLiveData()
        }

        startFragment(aViewModel)

        onScreen<DiscoverMoviesScreen> {
            movies {
                firstChild<DiscoverMoviesScreen.MovieListItem> {
                    isVisible()
                    title { hasText("Greyhound") }
                    year { hasText("Year: 2020") }
                    language { hasText("Language: en") }
                    voteAverage { hasText("7.4") }
                }
            }
        }
    }

    @Test
    fun itDisplaysErrorView() {
        val aViewState = DiscoverMoviesViewState(
            errorMessage = R.string.error_message
        )
        val aViewModel: DiscoverMoviesViewModel = mockk(relaxed = true) {
            every { viewState } returns aViewState.toLiveData()
        }

        startFragment(aViewModel)

        onScreen<LoadingScreen> {
          errorIcon.matches { isDisplayed() }
          errorTitle.matches { isDisplayed() }
          errorButton.matches { isDisplayed() }
        }
    }

    @Test
    fun itDisplaysLoadingView() {
        val aViewState = DiscoverMoviesViewState(
            isLoading = true
        )
        val aViewModel: DiscoverMoviesViewModel = mockk(relaxed = true) {
            every { viewState } returns aViewState.toLiveData()
        }

        startFragment(aViewModel)

        onScreen<LoadingScreen> {
            loadingView.matches { isDisplayed() }
        }
    }

    @Test
    fun itForwardsListTapEvent() {
        val aMovieListUiItem = movieListUiItemFixture {
            copy(id = 1)
        }
        val aViewState = DiscoverMoviesViewState(
            movies = listOf(aMovieListUiItem)
        )
        val aViewModel: DiscoverMoviesViewModel = mockk(relaxed = true) {
            every { viewState } returns aViewState.toLiveData()
        }

        startFragment(aViewModel)
        onScreen<DiscoverMoviesScreen> {
            movies {
                firstChild<DiscoverMoviesScreen.MovieListItem> {
                    click()
                }
            }
        }

        verify { aViewModel.tapOnMovie(1) }
    }

    @Test
    fun itNavigatesToDetails() {
        val aViewEffectEvent = DiscoverMoviesViewEffect.NavigateToDetail(1).asEvent()
        val aMovieListUiItem = movieListUiItemFixture()
        val aViewState = DiscoverMoviesViewState(movies = listOf(aMovieListUiItem))
        val aViewModel: DiscoverMoviesViewModel = mockk(relaxed = true) {
            every { viewEffect } returns aViewEffectEvent.toLiveData()
            every { viewState } returns aViewState.toLiveData()
        }
        val expectedAction =
            DiscoverMoviesFragmentDirections.actionDiscoverMoviesFragmentToMovieDetailsFragment(1)

        startFragment(aViewModel)

        verify { mockNavController.navigate(expectedAction) }
    }

    private fun startFragment(viewModel: DiscoverMoviesViewModel) {
        fragmentScenario = launchFragmentInContainer(
            Bundle(),
            themeResId = R.style.Theme_WhatsToWatch,
            factory = createFactoryWithNavController(mockNavController) {
                DiscoverMoviesFragment(viewModel.toFactory())
            }
        )
    }
}
