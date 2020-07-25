package com.juawapps.whatstowatch.movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.juawapps.whatstowatch.R
import com.juawapps.whatstowatch.common.ui.observeEffects
import com.juawapps.whatstowatch.databinding.FragmentMovieDetailsBinding
import kotlinx.android.synthetic.main.fragment_discover_movies.*
import javax.inject.Inject

class MovieDetailsFragment @Inject constructor(
    private val viewModelFactory: MovieDetailsViewModel.Factory
) : Fragment() {

    private val fragmentArgs: MovieDetailsFragmentArgs by navArgs()
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels {
        viewModelFactory.create(fragmentArgs.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMovieDetailsBinding.inflate(inflater, container, false).apply {
        bindView(movieDetailsViewModel)
        observeEffects(movieDetailsViewModel, ::viewEffectsActions)
    }.root

    private fun FragmentMovieDetailsBinding.bindView(
        movieDetailsViewModel: MovieDetailsViewModel
    ) {
        lifecycleOwner = viewLifecycleOwner
        viewActions = movieDetailsViewModel
        viewModel = movieDetailsViewModel
        topAppBar.setUpToolbar()
    }

    private fun viewEffectsActions(effect: MovieDetailsViewEffect) {
        when (effect) {
            is MovieDetailsViewEffect.ShowErrorMessage -> showErrorSnackBar()
        }
    }

    private fun Toolbar.setUpToolbar() {
        val navController = findNavController()
        setupWithNavController(navController)
    }

    private fun showErrorSnackBar() {
        Snackbar.make(topAppBar, R.string.error_message, BaseTransientBottomBar.LENGTH_SHORT).show()
    }
}