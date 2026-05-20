package ru.tander.mads.demo.ui.screen.inline

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.tander.mads.Mads
import ru.tander.mads.inline.loading.integration_public.InLineAdResponse
import ru.tander.mads.inline.model.InLineAdAction
import ru.tander.mads.inline.model.InLineAdContent
import ru.tander.mads.inline.model.InLineAdEvent
import ru.tander.mads.inline.multiformat.integration_public.events.MultiformatAdActions
import ru.tander.mads.inline.multiformat.integration_public.events.MultiformatAdEvents

class InLineAdDemoViewModel : ViewModel() {

    private val mutableViewState = MutableStateFlow<ViewState>(ViewState.InProgress())

    val viewState: StateFlow<ViewState> = mutableViewState.asStateFlow()

    init {
        viewModelScope.launch {
            val loadingResult = Mads.inLine.load(
                adRequest = inLineAdDemoRequest,
            )
            when (loadingResult) {
                is InLineAdResponse.Success -> {
                    loadingResult.content.actions
                        .onEach(::handleInLineAdShowingAction)
                        .launchIn(viewModelScope)
                    loadingResult.content.events
                        .onEach(::handleInLineAdShowingEvent)
                        .launchIn(viewModelScope)
                    mutableViewState.update {
                        ViewState.Success(
                            contentType = loadingResult.content.type,
                            showContent = { modifier -> loadingResult.content.show(modifier) },
                        )
                    }
                }
                is InLineAdResponse.NoContent -> { // <- optional NoContent state handling
                    mutableViewState.update {
                        ViewState.Failure()
                    }
                }
                is InLineAdResponse.Failure -> { // <- optional Failure state handling
                    mutableViewState.update {
                        ViewState.Failure()
                    }
                }
            }
        }
    }

    private fun handleInLineAdShowingAction(action: InLineAdAction) {
        when (action) {
            is MultiformatAdActions.OnUrlClicked -> {
                // implement url opening
            }
            else -> {
                // unknown action, log error
            }
        }
    }

    private fun handleInLineAdShowingEvent(event: InLineAdEvent) {
        when (event) {
            is MultiformatAdEvents.OnBlockView -> {
                // block is just shown
            }
            is MultiformatAdEvents.OnCreativeView -> {
                // creative is just shown
            }
            else -> {
                // unknown event, no-op
            }
        }
    }

    sealed interface ViewState {

        class InProgress : ViewState

        class Success(
            val contentType: InLineAdContent.Type,
            val showContent: @Composable (modifier: Modifier) -> Unit,
        ) : ViewState

        class Failure : ViewState
    }
}
