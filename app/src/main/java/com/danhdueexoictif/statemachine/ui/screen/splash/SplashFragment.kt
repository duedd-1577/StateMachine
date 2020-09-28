package com.danhdueexoictif.statemachine.ui.screen.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.danhdueexoictif.statemachine.R
import com.danhdueexoictif.statemachine.databinding.SplashFragmentBinding
import com.danhdueexoictif.statemachine.ui.base.BaseFragment
import com.danhdueexoictif.statemachine.utils.d
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {

    override val layoutId: Int = R.layout.splash_fragment

    override val viewModel: SplashViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.onEach { state -> handleState(state) }.launchIn(lifecycleScope)
        setupUIs()
    }

    private fun setupUIs() {
        lifecycleScope.launch {
//            butLogin?.clicks()?.collect {
//                viewModel.intentChannel.safeOffer(SplashViewModel.Intent.CheckUserLogin)
//            }
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
