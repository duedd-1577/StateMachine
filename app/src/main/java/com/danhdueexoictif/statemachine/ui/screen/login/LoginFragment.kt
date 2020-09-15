package com.danhdueexoictif.statemachine.ui.screen.login

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.danhdueexoictif.statemachine.R
import com.danhdueexoictif.statemachine.databinding.LoginFragmentBinding
import com.danhdueexoictif.statemachine.ui.base.BaseFragment
import com.danhdueexoictif.statemachine.utils.d

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    override val layoutId: Int = R.layout.login_fragment

    override val viewModel: LoginViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        d { "onActivityCreated" }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}