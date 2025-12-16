package ru.tander.mads.demo.ui.screen.formats

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.screen.MadsDemoScreenContentContainer

@Composable
fun AdFormatsScreen(
    onConfigurationClick: () -> Unit,
    onInAppAdFormatClick: () -> Unit,
) = MadsDemoScreenContentContainer(
    labelRes = R.string.application_name,
    onConfigurationClick = onConfigurationClick,
) { paddingValues ->
    LazyColumn(Modifier.padding(paddingValues)) {
        adFormat(
            nameRes = R.string.ad_format_in_app,
            onClick = onInAppAdFormatClick,
        )
    }
}

private fun LazyListScope.adFormat(
    @StringRes nameRes: Int,
    onClick: () -> Unit,
) = item {
    ListItem(
        headlineContent = { Text(stringResource(nameRes)) },
        modifier = Modifier.clickable(onClick = onClick),
    )
}
