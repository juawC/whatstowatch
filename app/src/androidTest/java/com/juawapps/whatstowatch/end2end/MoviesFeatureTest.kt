package com.juawapps.whatstowatch.end2end

import androidx.test.core.app.ActivityScenario
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.juawapps.whatstowatch.MainActivity
import com.juawapps.whatstowatch.di.ApiConfigsModule
import com.juawapps.whatstowatch.screens.DiscoverMoviesScreen
import com.juawapps.whatstowatch.screens.MoviesDetailsScreen
import com.juawapps.whatstowatch.utils.mockserver.MockServerTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(ApiConfigsModule::class)
class MoviesFeatureTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mockServerTestRule = MockServerTestRule()

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @After
    fun cleanup() {
        activityScenario.close()
    }

    @Test
    fun itDisplaysMoviesList() {
        launchActivity()

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
    fun itOpensMovieDetail() {
        launchActivity()

        onScreen<DiscoverMoviesScreen> {
            movies {
                firstChild<DiscoverMoviesScreen.MovieListItem> {
                   click()
                }
            }
        }

        onScreen<MoviesDetailsScreen> {
            title { hasText("Greyhound") }
            year { hasText("Year: 2020") }
            language { hasText("Language: en") }
            voteAverage { hasText("7.5") }
            votes { hasText("419 votes") }
            overviewText { hasText(containsString("A first-time captain leads a convoy")) }
        }
    }

    private fun launchActivity() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }
}
