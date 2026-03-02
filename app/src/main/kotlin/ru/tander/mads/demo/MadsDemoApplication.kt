package ru.tander.mads.demo

import android.app.Application
import ru.tander.mads.Mads
import ru.tander.mads.MadsSdk

class MadsDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Mads.init(this)
        Mads.applyDefaultConfig()
    }

    private fun MadsSdk.applyDefaultConfig() {
        userId = MadsSdkDefaults.USER_ID
    }
}
