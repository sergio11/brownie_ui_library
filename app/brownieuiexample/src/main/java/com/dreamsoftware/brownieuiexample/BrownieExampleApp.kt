package com.dreamsoftware.brownieuiexample

import android.app.Application
import com.dreamsoftware.brownie.utils.IBrownieApplicationAware
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BrownieExampleApp: Application(), IBrownieApplicationAware {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}