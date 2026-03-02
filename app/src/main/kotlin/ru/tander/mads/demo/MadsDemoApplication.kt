package ru.tander.mads.demo

import android.app.Application
import ru.tander.mads.Mads
import ru.tander.mads.MadsSdk

class MadsDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MadsSdk.init(this)
        MadsSdk.applyDefaultConfig()
    }

    private fun Mads.applyDefaultConfig() {
        userId = MadsSdkDefaults.USER_ID
    }
}
