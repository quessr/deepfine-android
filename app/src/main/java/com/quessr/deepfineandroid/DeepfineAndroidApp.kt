package com.quessr.deepfineandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DeepfineAndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        net.sqlcipher.database.SQLiteDatabase.loadLibs(this)
    }
}