package ru.tander.mads.demo.ui.component.form

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

inline fun LazyListScope.form(
    fieldsModels: ImmutableList<FormFieldModel<*>>,
    noinline itemKey: (FormFieldModel<*>) -> Any = { item -> item.fieldKey },
    noinline itemContentType: (FormFieldModel<*>) -> Any = { item -> item::class },
    crossinline itemModifier: @Composable LazyItemScope.() -> Modifier = { Modifier },
) = items(
    items = fieldsModels,
    key = itemKey,
    contentType = itemContentType,
    itemContent = { fieldModel ->
        FormField(
            model = fieldModel,
            modifier = itemModifier(),
        )
    },
)

inline fun LazyListScope.formButton(
    noinline onClick: () -> Unit,
    @StringRes labelRes: Int,
    @DrawableRes iconRes: Int? = null,
    key: Any? = null,
    contentType: Any? = null,
    crossinline modifier: @Composable LazyItemScope.() -> Modifier = { Modifier },
) = item(
    key = key,
    contentType = contentType,
    content = {
        ListItem(
            headlineContent = {
                Button(
                    onClick = onClick,
                    modifier = modifier(),
                ) {
                    iconRes?.let {
                        Icon(
                            painter = painterResource(iconRes),
                            contentDescription = stringResource(labelRes),
                        )
                    }
                    Text(stringResource(labelRes))
                }
            },
        )
    }
)

fun formFieldsModels(
    vararg fieldsModels: FormFieldModel<*>,
): ImmutableList<FormFieldModel<*>> = persistentListOf(
    *fieldsModels
)
