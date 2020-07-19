package com.juawapps.whatstowatch.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import io.mockk.every
import io.mockk.mockk


fun createNavControllerMock(currentDestinationId: Int = 1) : NavController = mockk(relaxed = true) {
    every { currentDestination } returns NavDestination("").apply { id = currentDestinationId }
    every { graph } returns NavGraph(MockNavigator()).apply {
        startDestination = 1
        addDestination(NavDestination("").apply { id = 1 })
    }
}

fun <T : Fragment> createFactoryWithNavController (
        mockNavController: NavController,
        newFragment: () -> T
): FragmentFactory {
    return object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return newFragment().also { fragment ->
                // In addition to returning a new instance of our Fragment,
                // get a callback whenever the fragment’s view is created
                // or destroyed so that we can set the mock NavController
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        // The fragment’s view has just been created
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }
    }
}

@Navigator.Name("Name")
private class MockNavigator : Navigator<NavGraph>() {
    override fun navigate(destination: NavGraph, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Extras?): NavDestination? {
        return mockk(relaxed = true)
    }

    override fun createDestination(): NavGraph {
        return mockk(relaxed = true)
    }

    override fun popBackStack(): Boolean {
        return false
    }
}
