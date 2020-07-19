package com.juawapps.whatstowatch

import androidx.fragment.app.Fragment
import com.juawapps.whatstowatch.di.annotation.FragmentKey
import com.juawapps.whatstowatch.movies.ui.detail.MovieDetailsFragment
import com.juawapps.whatstowatch.movies.ui.discover.DiscoverMoviesFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @FragmentKey(DiscoverMoviesFragment::class)
    abstract fun bindDiscoverFragment(fragment: DiscoverMoviesFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(MovieDetailsFragment::class)
    abstract fun bindDetailFragment(fragment: MovieDetailsFragment): Fragment
}