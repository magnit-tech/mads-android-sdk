package ru.tander.mads.demo.ui.screen.inline

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.tander.mads.demo.MadsSdkDefaults
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.FormSwitchFieldModel
import ru.tander.mads.demo.ui.component.form.FormTextFieldModel
import ru.tander.mads.demo.ui.component.form.formFieldsModels
import ru.tander.mads.demo.ui.screen.inline.component.InLineAdLoadingModel
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch

class InLineAdDemoViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val padIdFieldModel = FormTextFieldModel(
        labelRes = R.string.in_line_pad_id,
        savedStateHandle = savedStateHandle,
        viewModelScope = viewModelScope,
        fieldKey = KEY_PAD_ID,
        initialValue = MadsSdkDefaults.InLine.PAD_ID,
        defaultValue = MadsSdkDefaults.InLine.PAD_ID,
    )

    private val debugCreativeFieldModel = FormSwitchFieldModel(
        labelRes = R.string.in_line_debug_creative,
        savedStateHandle = savedStateHandle,
        viewModelScope = viewModelScope,
        fieldKey = KEY_DEBUG_CREATIVE,
        initialValue = MadsSdkDefaults.InLine.DEBUG_CREATIVE,
        defaultValue = MadsSdkDefaults.InLine.DEBUG_CREATIVE,
    )

    val formFieldsModels = formFieldsModels(
        padIdFieldModel,
        debugCreativeFieldModel,
    )

    private val mutableAdLoadings: MutableStateFlow<ImmutableList<InLineAdLoadingModel>> =
        MutableStateFlow(persistentListOf())

    val adLoadings: StateFlow<ImmutableList<InLineAdLoadingModel>> = mutableAdLoadings.asStateFlow()

    @OptIn(ExperimentalAtomicApi::class)
    private val adLoadingsCounter = AtomicInt(0)

    fun onLoadAdPressed() {
        @OptIn(ExperimentalAtomicApi::class)
        mutableAdLoadings.update { adLoadings ->
            val newLoading = InLineAdLoadingModel(
                padId = padIdFieldModel.value.value,
                debugCreative = debugCreativeFieldModel.value.value,
                ordinalNumber = adLoadingsCounter.incrementAndFetch(),
                coroutineScope = viewModelScope,
            )
            adLoadings.toPersistentList().add(newLoading)
        }
    }

    private companion object {
        const val KEY_PAD_ID = "padId"
        const val KEY_DEBUG_CREATIVE = "debugCreative"
    }
}
