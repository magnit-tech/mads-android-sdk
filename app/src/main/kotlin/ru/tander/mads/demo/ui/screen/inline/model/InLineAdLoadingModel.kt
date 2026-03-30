package ru.tander.mads.demo.ui.screen.inline.model

import ru.tander.mads.inline.loading.integration_public.InLineAdResponse

data class InLineAdLoadingModel(
    val id: Int,
    val padId: String,
    val isDebug: Boolean,
    val result: InLineAdResponse,
)