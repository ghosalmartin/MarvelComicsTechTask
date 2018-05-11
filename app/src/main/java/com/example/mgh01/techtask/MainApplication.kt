package com.example.mgh01.techtask

import android.app.Application
import io.paperdb.Paper

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this.applicationContext)
    }
}