package ru.tander.mads.demo.ui.screen.inapp.component

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.tander.mads.inapp.loading.InAppAdLoader
import ru.tander.mads.inapp.loading.InAppAdRequest
import ru.tander.mads.inapp.model.InAppAd

class InAppAdLoadingModel(
    padId: String,
    debugCreative: Boolean,
    val ordinalNumber: Int,
    adLoader: InAppAdLoader,
) {
    val description = if (debugCreative) {
        "padId=$padId (debug)"
    } else {
        "padId=$padId"
    }

    private val mutableStatus = MutableStateFlow<Status>(Status.InProgress())

    val status: StateFlow<Status> = mutableStatus.asStateFlow()

    init {
        adLoader.load(
            adRequest = InAppAdRequest(
                padId = padId,
                debugCreative = debugCreative,
            ),
            onSuccess = { loadedAd ->
                mutableStatus.update { Status.Success(loadedAd) }
            },
            onFailure = { _ ->
                mutableStatus.update { Status.Failure() }
            },
        )
    }

    sealed interface Status {

        class InProgress : Status

        class Success(val loadedAd: InAppAd) : Status

        class Failure : Status
    }
}
