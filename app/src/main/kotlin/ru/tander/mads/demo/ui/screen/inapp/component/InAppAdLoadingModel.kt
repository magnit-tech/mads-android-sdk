package ru.tander.mads.demo.ui.screen.inapp.component

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tander.mads.Mads
import ru.tander.mads.inapp.loading.InAppAdRequest
import ru.tander.mads.inapp.loading.InAppAdResponse
import ru.tander.mads.inapp.showing.InAppAdContent

class InAppAdLoadingModel(
    padId: String,
    debugCreative: Boolean,
    val ordinalNumber: Int,
    coroutineScope: CoroutineScope,
) {
    val description = if (debugCreative) {
        "padId=$padId (debug)"
    } else {
        "padId=$padId"
    }

    private val mutableStatus = MutableStateFlow<Status>(Status.InProgress())

    val status: StateFlow<Status> = mutableStatus.asStateFlow()

    init {
        coroutineScope.launch {
            val loadingResult = Mads.inApp.load(
                adRequest = InAppAdRequest(
                    padId = padId,
                    debugCreative = debugCreative,
                ),
            )
            mutableStatus.update {
                when (loadingResult) {
                    is InAppAdResponse.Success -> {
                        Status.Success(loadingResult.content)
                    }
                    is InAppAdResponse.NoContent -> {
                        Status.Failure() // <- optional NoContent state handling
                    }
                    is InAppAdResponse.Failure -> {
                        Status.Failure() // <- optional Failure state handling
                    }
                    else -> {
                        Status.Failure() // <- required else branch
                    }
                }
            }
        }
    }

    sealed interface Status {

        class InProgress : Status

        class Success(val loadedAdContent: InAppAdContent) : Status

        class Failure : Status
    }
}
