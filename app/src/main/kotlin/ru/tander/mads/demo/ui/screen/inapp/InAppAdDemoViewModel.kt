package ru.tander.mads.demo.ui.screen.inapp

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
import ru.tander.mads.Mads
import ru.tander.mads.demo.MadsSdkDefaults
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.FormSwitchFieldModel
import ru.tander.mads.demo.ui.component.form.FormTextFieldModel
import ru.tander.mads.demo.ui.component.form.formFieldsModels
import ru.tander.mads.demo.ui.screen.inapp.component.InAppAdLoadingModel
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch

class InAppAdDemoViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val adLoader = Mads.inAppAdLoader()

    private val padIdFieldModel = FormTextFieldModel(
        labelRes = R.string.in_app_pad_id,
        savedStateHandle = savedStateHandle,
        viewModelScope = viewModelScope,
        fieldKey = KEY_PAD_ID,
        initialValue = MadsSdkDefaults.InApp.PAD_ID,
        defaultValue = MadsSdkDefaults.InApp.PAD_ID,
    )

    private val debugCreativeFieldModel = FormSwitchFieldModel(
        labelRes = R.string.in_app_debug_creative,
        savedStateHandle = savedStateHandle,
        viewModelScope = viewModelScope,
        fieldKey = KEY_DEBUG_CREATIVE,
        initialValue = MadsSdkDefaults.InApp.DEBUG_CREATIVE,
        defaultValue = MadsSdkDefaults.InApp.DEBUG_CREATIVE,
    )

    val formFieldsModels = formFieldsModels(
        padIdFieldModel,
        debugCreativeFieldModel,
    )

    private val mutableAdLoadings: MutableStateFlow<ImmutableList<InAppAdLoadingModel>> =
        MutableStateFlow(persistentListOf())

    val adLoadings: StateFlow<ImmutableList<InAppAdLoadingModel>> = mutableAdLoadings.asStateFlow()

    @OptIn(ExperimentalAtomicApi::class)
    private val adLoadingsCounter = AtomicInt(0)

    fun onLoadAdPressed() {
        @OptIn(ExperimentalAtomicApi::class)
        mutableAdLoadings.update { adLoadings ->
            val newLoading = InAppAdLoadingModel(
                padId = padIdFieldModel.value.value,
                debugCreative = debugCreativeFieldModel.value.value,
                ordinalNumber = adLoadingsCounter.incrementAndFetch(),
                adLoader = adLoader,
            )
            adLoadings.toPersistentList().add(newLoading)
        }
    }

    override fun onCleared() {
        adLoader.clear()
    }

    private companion object {

        const val KEY_PAD_ID = "padId"
        const val KEY_DEBUG_CREATIVE = "debugCreative"
    }
}
