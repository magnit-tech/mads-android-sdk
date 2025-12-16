package ru.tander.mads.demo.ui.screen.configuration

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.form
import ru.tander.mads.demo.ui.component.form.formButton
import ru.tander.mads.demo.ui.screen.MadsDemoScreenContentContainer

@Composable
fun ConfigurationScreen(
    onBackPressed: () -> Unit,
    viewModel: ConfigurationViewModel = viewModel(),
) = MadsDemoScreenContentContainer(
    labelRes = R.string.configuration,
    onBackPressed = onBackPressed,
) { paddingValues ->
    LazyColumn(Modifier.padding(paddingValues)) {
        form(
            fieldsModels = viewModel.formFieldsModels,
            itemModifier = { Modifier.fillMaxWidth() },
        )
        formButton(
            onClick = viewModel::onApplyPressed,
            labelRes = R.string.action_apply,
            iconRes = R.drawable.ic_action_apply,
            modifier = { Modifier.fillMaxWidth() },
        )
    }
}
