package ru.tander.mads.demo.ui.screen.inline.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tander.mads.demo.R
import ru.tander.mads.inline.model.InLineAdAction
import ru.tander.mads.inline.model.InLineAdContent
import ru.tander.mads.inline.model.InLineAdEvent

@Composable
fun InLineAdLoading(
    model: InLineAdLoadingModel,
    handleAdAction: (InLineAdAction) -> Unit,
    handleAdEvent: (InLineAdEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val status = model.status.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    Surface(
        modifier = modifier,
        color = when (status.value) {
            is InLineAdLoadingModel.Status.InProgress -> MaterialTheme.colorScheme.surfaceVariant
            is InLineAdLoadingModel.Status.Success -> MaterialTheme.colorScheme.surfaceVariant
            is InLineAdLoadingModel.Status.Failure -> MaterialTheme.colorScheme.errorContainer
        },
        contentColor = when (status.value) {
            is InLineAdLoadingModel.Status.InProgress -> MaterialTheme.colorScheme.onSurfaceVariant
            is InLineAdLoadingModel.Status.Success -> MaterialTheme.colorScheme.onSurfaceVariant
            is InLineAdLoadingModel.Status.Failure -> MaterialTheme.colorScheme.onErrorContainer
        },
    ) {
        Column(
            modifier = modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "#${model.ordinalNumber}",
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = model.description,
                )
                Spacer(
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(1.dp)
                    .background(LocalContentColor.current),
            )
            Spacer(
                modifier = Modifier.height(16.dp),
            )
            when (val status = status.value) {
                is InLineAdLoadingModel.Status.InProgress -> {
                    CircularProgressIndicator()
                }
                is InLineAdLoadingModel.Status.Success -> {
                    LaunchedEffect(status, lifecycleOwner, handleAdAction) {
                        status.loadedAdContent.actions
                            .flowWithLifecycle(
                                lifecycle = lifecycleOwner.lifecycle,
                                minActiveState = Lifecycle.State.STARTED,
                            )
                            .onEach(handleAdAction)
                            .launchIn(lifecycleOwner.lifecycleScope)
                    }

                    LaunchedEffect(status, lifecycleOwner, handleAdEvent) {
                        status.loadedAdContent.events
                            .flowWithLifecycle(
                                lifecycle = lifecycleOwner.lifecycle,
                                minActiveState = Lifecycle.State.STARTED,
                            )
                            .onEach(handleAdEvent)
                            .launchIn(lifecycleOwner.lifecycleScope)
                    }
                    when (status.loadedAdContent.type) {
                        is InLineAdContent.Type.Empty -> {
                            // nothing to show, no-op
                        }
                        is InLineAdContent.Type.Banner.Carousel -> {
                            // default carousel appearance looks good to us, show as is
                            status.loadedAdContent.show()
                        }
                        is InLineAdContent.Type.Banner.SingleItem -> {
                            // slightly adjust single item appearance by adding horizontal paddings
                            status.loadedAdContent.show(Modifier.padding(horizontal = 12.dp))
                        }
                        else -> {
                            // unknown content type, show as is
                            status.loadedAdContent.show()
                        }
                    }
                }
                is InLineAdLoadingModel.Status.Failure -> {
                    Row(
                        modifier = modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_status_failure),
                                contentDescription = stringResource(R.string.in_line_ad_loading_failure),
                            )
                        }
                        Text(
                            text = stringResource(R.string.in_line_ad_loading_failure),
                        )
                        Spacer(
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}
