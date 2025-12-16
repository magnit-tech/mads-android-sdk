package ru.tander.mads.demo.ui.screen.inapp.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.tander.mads.demo.R
import ru.tander.mads.inapp.model.InAppAd

@Composable
fun InAppAdLoading(
    model: InAppAdLoadingModel,
    showLoadedAd: (InAppAd) -> Unit,
    modifier: Modifier = Modifier,
) {
    val status = model.status.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier,
        color = when (status.value) {
            is InAppAdLoadingModel.Status.InProgress -> MaterialTheme.colorScheme.surfaceVariant
            is InAppAdLoadingModel.Status.Success -> MaterialTheme.colorScheme.surfaceVariant
            is InAppAdLoadingModel.Status.Failure -> MaterialTheme.colorScheme.errorContainer
        },
        contentColor = when (status.value) {
            is InAppAdLoadingModel.Status.InProgress -> MaterialTheme.colorScheme.onSurfaceVariant
            is InAppAdLoadingModel.Status.Success -> MaterialTheme.colorScheme.onSurfaceVariant
            is InAppAdLoadingModel.Status.Failure -> MaterialTheme.colorScheme.onErrorContainer
        },
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    .height(1.dp)
                    .background(LocalContentColor.current),
            )
            Spacer(
                modifier = Modifier.height(16.dp),
            )
            when (val status = status.value) {
                is InAppAdLoadingModel.Status.InProgress -> {
                    CircularProgressIndicator()
                }
                is InAppAdLoadingModel.Status.Success -> {
                    InAppAdLoadingStatus(
                        descriptionRes = R.string.in_app_ad_loading_success,
                        iconRes = R.drawable.ic_status_success,
                        trailingContent = {
                            Button(
                                onClick = { showLoadedAd(status.loadedAd) },
                                content = { Text(stringResource(R.string.in_app_show_loaded_ad)) }
                            )
                        }
                    )
                }
                is InAppAdLoadingModel.Status.Failure -> {
                    InAppAdLoadingStatus(
                        descriptionRes = R.string.in_app_ad_loading_failure,
                        iconRes = R.drawable.ic_status_failure,
                    )
                }
            }
        }
    }
}

@Composable
private fun InAppAdLoadingStatus(
    @StringRes descriptionRes: Int,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    trailingContent: @Composable (() -> Unit)? = null,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
) {
    Box(
        modifier = Modifier.weight(1f),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = stringResource(descriptionRes),
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(descriptionRes),
        )
        trailingContent?.let {
            Spacer(
                modifier = Modifier.height(8.dp),
            )
            trailingContent()
        }
    }
    Spacer(
        modifier = Modifier.weight(1f),
    )
}
