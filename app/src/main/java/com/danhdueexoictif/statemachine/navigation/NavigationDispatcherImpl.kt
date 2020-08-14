package com.danhdueexoictif.statemachine.navigation

import androidx.navigation.NavController
import javax.inject.Inject

class NavigationDispatcherImpl @Inject constructor(private val navController: NavController) :
    NavigationDispatcher {

    override fun goBack() {
        navController.navigateUp()
    }

    companion object {
        const val MAIN_ARG = "MAIN_ARG"
    }
}