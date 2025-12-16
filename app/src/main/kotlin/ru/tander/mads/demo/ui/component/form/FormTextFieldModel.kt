package ru.tander.mads.demo.ui.component.form

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope

class FormTextFieldModel(
    @StringRes val labelRes: Int,
    savedStateHandle: SavedStateHandle,
    viewModelScope: CoroutineScope,
    fieldKey: String,
    initialValue: String,
    defaultValue: String,
) : FormFieldModel<String>(
    savedStateHandle = savedStateHandle,
    viewModelScope = viewModelScope,
    fieldKey = fieldKey,
    initialValue = initialValue,
    defaultValue = defaultValue,
)
