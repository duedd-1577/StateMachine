package com.danhdueexoictif.statemachine.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.danhdueexoictif.statemachine.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModel : BaseViewModel() {

    val intentChannel = Channel<Intent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            handleIntents()
            checkUserLogin()
        }
    }

    private suspend fun handleIntents() {
        intentChannel.consumeAsFlow().collect { intent ->
            when (intent) {
                Intent.CheckUserLogin -> {
                    val userState = checkUserLogin()
                    _state.value = userState
                }
            }
        }
    }

    private fun checkUserLogin(): State =
        if (userIsLoggedIn()) State.UserLoggedIn else State.UserLoggedOut

    private fun userIsLoggedIn(): Boolean {
        return false
    }

    sealed class Intent {
        object CheckUserLogin : Intent()
    }

    sealed class State {
        object Idle : State()
        object UserLoggedIn : State()
        object UserLoggedOut : State()
    }
}
