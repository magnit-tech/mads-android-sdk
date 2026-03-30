package ru.tander.mads.demo.ui.screen.inline

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tander.mads.Mads
import ru.tander.mads.demo.MadsSdkDefaults
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.FormSwitchFieldModel
import ru.tander.mads.demo.ui.component.form.FormTextFieldModel
import ru.tander.mads.demo.ui.component.form.formFieldsModels
import ru.tander.mads.demo.ui.screen.inline.model.InLineAdLoadingModel
import ru.tander.mads.inline.loading.integration_public.InLineAdResponse
import ru.tander.mads.inline.model.InLineAdRequest
import ru.tander.mads.inline.model.InLineAdSlot
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch

class InLineAdDemoViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val adLoader = Mads.inLine

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

    private val mutableAdLoadings =
        MutableStateFlow<PersistentList<InLineAdLoadingModel>>(persistentListOf())

    val adLoadings: StateFlow<PersistentList<InLineAdLoadingModel>> =
        mutableAdLoadings.asStateFlow()

    @OptIn(ExperimentalAtomicApi::class)
    private val adLoadingsCounter = AtomicInt(0)

    fun onLoadAdPressed() {
        @OptIn(ExperimentalAtomicApi::class)
        val id = adLoadingsCounter.incrementAndFetch()

        mutableAdLoadings.update { adLoadings ->
            adLoadings.add(
                InLineAdLoadingModel(
                    id = id,
                    padId = padIdFieldModel.value.value,
                    isDebug = debugCreativeFieldModel.value.value,
                    result = InLineAdResponse.NoContent
                )
            )
        }

        viewModelScope.launch {
            val result = adLoader.load(
                InLineAdRequest(
                    slot = InLineAdSlot(
                        padId = padIdFieldModel.value.value,
                        position = id,
                    ),
                    isDebugCreative = debugCreativeFieldModel.value.value,
                    targetings = mapOf(),
                )
            )

            mutableAdLoadings.update { list ->
                list.map { item ->
                    if (item.id == id) item.copy(result = result) else item
                }.toPersistentList()
            }
        }
    }

    private companion object {
        const val KEY_PAD_ID = "padId"
        const val KEY_DEBUG_CREATIVE = "debugCreative"
    }
}