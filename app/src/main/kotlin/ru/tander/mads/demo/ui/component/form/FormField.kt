package ru.tander.mads.demo.ui.component.form

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormField(
    model: FormFieldModel<*>,
    modifier: Modifier = Modifier,
) = when (model) {
    is FormSwitchFieldModel -> FormSwitchField(
        model = model,
        modifier = modifier,
    )
    is FormTextFieldModel -> FormTextField(
        model = model,
        modifier = modifier,
    )
}
