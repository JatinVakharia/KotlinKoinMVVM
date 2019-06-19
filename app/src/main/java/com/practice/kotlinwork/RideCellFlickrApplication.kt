package com.practice.kotlinwork

import android.app.Application
import com.practice.kotlinwork.di.apiModule
import com.practice.kotlinwork.di.repoModule
import com.practice.kotlinwork.di.uiModule
import org.koin.android.ext.android.startKoin


class RideCellFlickrApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(uiModule, repoModule, apiModule))
    }
}