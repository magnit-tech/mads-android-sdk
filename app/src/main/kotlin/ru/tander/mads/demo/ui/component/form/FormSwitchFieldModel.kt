package ru.tander.mads.demo.ui.component.form

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope

class FormSwitchFieldModel(
    @StringRes val labelRes: Int,
    savedStateHandle: SavedStateHandle,
    viewModelScope: CoroutineScope,
    fieldKey: String,
    initialValue: Boolean,
    defaultValue: Boolean,
) : FormFieldModel<Boolean>(
    savedStateHandle = savedStateHandle,
    viewModelScope = viewModelScope,
    fieldKey = fieldKey,
    initialValue = initialValue,
    defaultValue = defaultValue,
) {
    fun switch() = update(Boolean::not)
}
