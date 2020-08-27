package com.danhdueexoictif.statemachine.ui.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.danhdueexoictif.statemachine.R
import com.danhdueexoictif.statemachine.extension.safeOffer
import com.danhdueexoictif.statemachine.utils.d
import kotlinx.android.synthetic.main.splash_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks

class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        viewModel.state.onEach { state -> handleState(state) }.launchIn(lifecycleScope)
        setupUIs()
    }

    private fun setupUIs() {
        lifecycleScope.launch {
            butLogin?.clicks()?.collect {
                viewModel.intentChannel.safeOffer(SplashViewModel.Intent.CheckUserLogin)
            }
        }
    }

    private fun handleState(state: SplashViewModel.State) {
        d { "handleState(state: $state)" }
        when (state) {
            SplashViewModel.State.Idle -> Unit
            SplashViewModel.State.UserLoggedIn -> navigateToMainFragment()
            SplashViewModel.State.UserLoggedOut -> navigateToLoginFragment()
        }
    }

    private fun navigateToLoginFragment() {
        d { "navigateToLoginFragment()" }
    }

    private fun navigateToMainFragment() {
        d { "navigateToMainFragment()" }
    }


}
