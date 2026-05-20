package ru.tander.mads.demo.ui.screen.inapp

import androidx.fragment.app.FragmentActivity
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
import ru.tander.mads.inapp.loading.InAppAdResponse
import ru.tander.mads.inapp.showing.InAppAdShowingAction
import ru.tander.mads.inapp.showing.InAppAdShowingEvent

class InAppAdDemoViewModel : ViewModel() {

    private val mutableViewState = MutableStateFlow<ViewState>(ViewState.InProgress())

    val viewState: StateFlow<ViewState> = mutableViewState.asStateFlow()

    init {
        viewModelScope.launch {
            val loadingResult = Mads.inApp.load(
                adRequest = inAppAdDemoRequest,
            )
            when (loadingResult) {
                is InAppAdResponse.Success -> {
                    loadingResult.content.actions
                        .onEach(::handleInAppAdShowingAction)
                        .launchIn(viewModelScope)
                    loadingResult.content.events
                        .onEach(::handleInAppAdShowingEvent)
                        .launchIn(viewModelScope)
                    mutableViewState.update {
                        ViewState.Success(loadingResult.content::show)
                    }
                }
                is InAppAdResponse.NoContent -> { // <- optional NoContent state handling
                    mutableViewState.update {
                        ViewState.Failure()
                    }
                }
                is InAppAdResponse.Failure -> { // <- optional Failure state handling
                    mutableViewState.update {
                        ViewState.Failure()
                    }
                }
                else -> {  // <- required else branch
                    mutableViewState.update {
                        ViewState.Failure()
                    }
                }
            }
        }
    }

    private fun handleInAppAdShowingAction(action: InAppAdShowingAction) {
        when (action) {
            is InAppAdShowingAction.OnUrlClicked -> {
                // implement url opening
            }
            is InAppAdShowingAction.OnPromocodeCopy -> {
                // implement promocode copying
            }
            else -> {
                // unknown action, log error
            }
        }
    }

    private fun handleInAppAdShowingEvent(event: InAppAdShowingEvent) {
        when (event) {
            is InAppAdShowingEvent.OnCreativeView -> {
                // ad is just shown
            }
            is InAppAdShowingEvent.OnCreativeDismissed -> {
                // ad is just dismissed
            }
            else -> {
                // unknown event, no-op
            }
        }
    }

    sealed interface ViewState {

        class InProgress : ViewState

        class Success(val showContent: (FragmentActivity) -> Unit) : ViewState

        class Failure : ViewState
    }
}
