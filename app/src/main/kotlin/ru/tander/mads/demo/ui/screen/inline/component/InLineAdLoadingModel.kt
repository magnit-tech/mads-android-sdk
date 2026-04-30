package ru.tander.mads.demo.ui.screen.inline.component

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tander.mads.Mads
import ru.tander.mads.inline.loading.integration_public.InLineAdResponse
import ru.tander.mads.inline.model.InLineAdContent
import ru.tander.mads.inline.model.InLineAdRequest
import ru.tander.mads.inline.model.InLineAdSlot

class InLineAdLoadingModel(
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
            val loadingResult = Mads.inLine.load(
                adRequest = InLineAdRequest(
                    slot = InLineAdSlot(
                        padId = padId,
                        position = ordinalNumber,
                    ),
                    isDebugCreative = debugCreative,
                    targetings = mapOf(),
                )
            )
            mutableStatus.update {
                when (loadingResult) {
                    is InLineAdResponse.Success -> {
                        Status.Success(loadingResult.content)
                    }
                    is InLineAdResponse.NoContent -> {
                        Status.Failure() // <- optional NoContent state handling
                    }
                    is InLineAdResponse.Failure -> {
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

        class Success(val loadedAdContent: InLineAdContent) : Status

        class Failure : Status
    }
}
