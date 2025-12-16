package ru.tander.mads.demo.ui.screen.configuration

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.tander.mads.Mads
import ru.tander.mads.demo.MadsSdkDefaults
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.FormTextFieldModel
import ru.tander.mads.demo.ui.component.form.formFieldsModels

class ConfigurationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val userIdField = FormTextFieldModel(
        labelRes = R.string.configuration_user_id,
        savedStateHandle = savedStateHandle,
        viewModelScope = viewModelScope,
        fieldKey = KEY_USER_ID,
        initialValue = Mads.userId.orEmpty(),
        defaultValue = MadsSdkDefaults.USER_ID,
    )

    val formFieldsModels = formFieldsModels(
        userIdField,
    )

    fun onApplyPressed() {
        Mads.userId = userIdField.value.value
    }

    private companion object {

        const val KEY_USER_ID = "userId"
    }
}
