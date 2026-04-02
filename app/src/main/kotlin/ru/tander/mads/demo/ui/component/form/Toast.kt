package ru.tander.mads.demo.ui.component.form

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun showToast(
    context: Context,
    @StringRes messageRes: Int,
): Unit = Toast
    .makeText(context, messageRes, Toast.LENGTH_SHORT)
    .show()