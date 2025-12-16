package ru.tander.mads.demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import ru.tander.mads.demo.ui.MadsDemo

class MadsDemoActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MadsDemo() }
    }
}
