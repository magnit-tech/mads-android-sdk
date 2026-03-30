package ru.tander.mads.demo.ui.screen.inline

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.screen.inline.model.InLineAdLoadingModel
import ru.tander.mads.inline.loading.integration_public.InLineAdResponse
import ru.tander.mads.inline.multiformat.integration_public.events.MultiformatAdEvents

@Composable
fun InLineAdLoadingItem(
    item: InLineAdLoadingModel,
    modifier: Modifier = Modifier,
) {
    if (item.result is InLineAdResponse.Loaded) {
        LaunchedEffect(item.result) {
            item.result.content.events.collect { event ->
                when (event) {
                    is MultiformatAdEvents.OnBlockView -> {}
                    is MultiformatAdEvents.OnCreativeView -> {}
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF3DEDE))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "#${item.id}",
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                text = buildString {
                    append("padId=")
                    append(item.padId)
                    if (item.isDebug) append(" (debug)")
                },
                style = MaterialTheme.typography.titleMedium,
            )
        }

        HorizontalDivider(
            color = Color(0xFF6E3A3A),
            thickness = 1.dp,
        )

        when (item.result) {
            is InLineAdResponse.Failure -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_status_failure),
                        contentDescription = null,
                    )
                    Text("Loading failed!")
                }
            }

            InLineAdResponse.NoContent -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                    )
                    Text("Loading...")
                }
            }

            is InLineAdResponse.Loaded -> {
                item.result.content.show()
            }
        }
    }
}