package ru.tander.mads.demo.ui.component.form

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.tander.mads.demo.R

@Composable
fun FormTextField(
    model: FormTextFieldModel,
    modifier: Modifier = Modifier,
) = ListItem(
    headlineContent = {
        TextField(
            value = model.value.collectAsStateWithLifecycle().value,
            onValueChange = model::update,
            modifier = modifier,
            label = {
                Text(stringResource(model.labelRes))
            },
            trailingIcon = model.resetAction.collectAsStateWithLifecycle().value?.let { resetAction ->
                {
                    IconButton(onClick = resetAction) {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_reset),
                            contentDescription = stringResource(R.string.action_reset),
                        )
                    }
                }
            },
        )
    },
)
