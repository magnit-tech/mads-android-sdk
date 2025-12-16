package ru.tander.mads.demo.ui.component.form

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

sealed class FormFieldModel<T>(
    savedStateHandle: SavedStateHandle,
    viewModelScope: CoroutineScope,
    val fieldKey: String,
    initialValue: T,
    private val defaultValue: T,
) {
    private val mutableValue = savedStateHandle.getMutableStateFlow(fieldKey, initialValue)

    val value: StateFlow<T> = mutableValue.asStateFlow()

    val resetAction: StateFlow<(() -> Unit)?> = runBlocking {
        value
            .map { value ->
                if (value == defaultValue) {
                    null
                } else {
                    { update(defaultValue) }
                }
            }
            .stateIn(viewModelScope)
    }

    fun update(value: T) {
        mutableValue.value = value
    }

    fun update(mutation: (T) -> T) {
        mutableValue.update(mutation)
    }
}
