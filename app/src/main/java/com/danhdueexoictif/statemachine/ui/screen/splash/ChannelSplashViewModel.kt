package com.danhdueexoictif.statemachine.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ChannelSplashViewModel(
    private val initialState: State
) : ViewModel() {

    val intentChannel = Channel<Intent>(Channel.UNLIMITED)
    val stateChannel = Channel<State>()

    init {
        viewModelScope.launch { handleIntents() }
    }

    private suspend fun handleIntents() {
        var state = initialState

        suspend fun setState(reducer: (State) -> State) {
            state = reducer(state)
            stateChannel.send(state)
        }

        intentChannel.consumeEach { intent ->
            when (intent) {
                Intent.CheckUserLogin -> {
                    setState { checkUserLogin() }
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