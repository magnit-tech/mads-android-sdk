package ru.tander.mads.demo

import android.app.Application
import android.content.Intent
import androidx.core.net.toUri
import ru.tander.mads.Mads

class MadsDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Mads.init(this, ::openLink)
        Mads.applyDefaultConfig()
    }

    private fun openLink(link: String) {
        Intent(Intent.ACTION_VIEW, link.toUri())
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .let(::startActivity)
    }

    private fun Mads.applyDefaultConfig() {
        userId = MadsSdkDefaults.USER_ID
    }
}
