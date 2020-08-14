package com.danhdueexoictif.statemachine.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.danhdueexoictif.statemachine.R
import com.danhdueexoictif.statemachine.navigation.NavigationDispatcher
import com.danhdueexoictif.statemachine.navigation.NavigationDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
interface NavigationModule {
    @get:[Binds ActivityScoped]
    val NavigationDispatcherImpl.navigationDispatcher: NavigationDispatcher

    companion object {
        @[Provides ActivityScoped]
        fun provideNavController(activity: Activity): NavController =
            activity.findNavController(R.id.mainNavHostFragment)
    }
}