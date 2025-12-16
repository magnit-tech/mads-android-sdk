package ru.tander.mads.demo.ui.component.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FormSwitchField(
    model: FormSwitchFieldModel,
    modifier: Modifier = Modifier,
) = ListItem(
    headlineContent = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(model.labelRes),
                modifier = Modifier.weight(1f),
            )
            Switch(
                checked = model.value.collectAsStateWithLifecycle().value,
                onCheckedChange = model::update,
            )
        }
    },
    modifier = modifier.clickable(onClick = model::switch),
)
