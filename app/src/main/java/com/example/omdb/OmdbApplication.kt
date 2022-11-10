package com.example.omdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OmdbApplication : Application() {


    override fun onCreate() {
        super.onCreate()
    }

}