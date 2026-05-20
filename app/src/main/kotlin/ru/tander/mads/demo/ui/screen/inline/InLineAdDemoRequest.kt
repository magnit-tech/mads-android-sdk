package ru.tander.mads.demo.ui.screen.inline

import ru.tander.mads.inline.model.InLineAdRequest
import ru.tander.mads.inline.model.InLineAdSlot

val inLineAdDemoRequest = InLineAdRequest(
    slot = InLineAdSlot(
        padId = "46",
        position = 1,
    ),
    isDebugCreative = true,
    targetings = mapOf(),
)
