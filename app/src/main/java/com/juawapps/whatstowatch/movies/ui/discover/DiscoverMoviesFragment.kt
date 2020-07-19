package com.juawapps.whatstowatch.movies.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.*
import com.juawapps.whatstowatch.databinding.FragmentDiscoverMoviesBinding
import com.juawapps.whatstowatch.di.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_discover_movies.*
import javax.inject.Inject

class DiscoverMoviesFragment  @Inject constructor(
    private val viewModelFactory: ViewModelFactory<DiscoverMoviesViewModel>
) : Fragment() {

    private val discoverMoviesViewModel: DiscoverMoviesViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDiscoverMoviesBinding.inflate(inflater, container, false).run {
        lifecycleOwner = viewLifecycleOwner
        viewActions = discoverMoviesViewModel
        viewModel = discoverMoviesViewModel
        list.adapter = MoviesListAdapter(discoverMoviesViewModel)
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEffects(discoverMoviesViewModel) {
            when(this) {
                is DiscoverMoviesViewEffect.ShowErrorMessage -> showErrorSnackBar()
                is DiscoverMoviesViewEffect.NavigateToDetail -> navigateToDetail(movieId)
            }
        }
    }

    private fun navigateToDetail(movieId: Long) {
        val action =
            DiscoverMoviesFragmentDirections.actionDiscoverMoviesFragmentToMovieDetailsFragment(movieId)

        findNavController().navigate(action)
    }

    private fun showErrorSnackBar() {
        Snackbar.make(list, R.string.error_message, LENGTH_SHORT).show()
    }
}