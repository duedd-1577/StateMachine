package com.danhdueexoictif.statemachine

import android.app.Application
import android.util.Log
import androidx.annotation.Nullable
import com.danhdueexoictif.statemachine.utils.Timber.DebugTree
import com.danhdueexoictif.statemachine.utils.Timber.plant
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        // Init Timber
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        } else {
            plant(CrashReportingTree())
        }
    }
}

/** A tree which logs important information for crash reporting.  */
private class CrashReportingTree : Timber.Tree() {

    override fun log(
        priority: Int,
        @Nullable tag: String?,
        @Nullable message: String,
        @Nullable t: Throwable?
    ) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }
        FirebaseCrashlytics.getInstance().setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)
        FirebaseCrashlytics.getInstance()
            .setCustomKey(CRASHLYTICS_KEY_TAG, tag ?: FIREBASE_CRASHLYTICS_DEFAULT_TAG)
        FirebaseCrashlytics.getInstance().setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
        t?.let { FirebaseCrashlytics.getInstance().recordException(Exception(message)) } ?: t?.let {
            FirebaseCrashlytics.getInstance().recordException(it)
        }
    }

    companion object {
        const val CRASHLYTICS_KEY_PRIORITY = "priority"
        const val CRASHLYTICS_KEY_TAG = "tag"
        const val CRASHLYTICS_KEY_MESSAGE = "message"
        const val FIREBASE_CRASHLYTICS_DEFAULT_TAG = "FirebaseCrashlytics"
    }
}
