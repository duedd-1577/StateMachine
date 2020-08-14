package com.danhdueexoictif.statemachine.di

import com.danhdueexoictif.statemachine.navigation.NavigationDispatcher
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface AppComponent {
    val navigationDispatcher: NavigationDispatcher
}