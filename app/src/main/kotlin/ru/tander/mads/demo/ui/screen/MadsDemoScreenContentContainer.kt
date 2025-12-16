package ru.tander.mads.demo.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.tander.mads.demo.R

@Composable
fun MadsDemoScreenContentContainer(
    @StringRes labelRes: Int,
    onBackPressed: (() -> Unit)? = null,
    onConfigurationClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) = Scaffold(
    topBar = {
        @OptIn(ExperimentalMaterial3Api::class)
        TopAppBar(
            title = {
                Text(stringResource(labelRes))
            },
            navigationIcon = {
                onBackPressed?.let {
                    IconButton(onBackPressed) {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_navigate_back),
                            contentDescription = stringResource(R.string.action_navigate_back),
                        )
                    }
                }
            },
            actions = {
                onConfigurationClick?.let {
                    IconButton(onClick = onConfigurationClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_navigate_to_configuration),
                            contentDescription = stringResource(R.string.configuration),
                        )
                    }
                }
            },
        )
    },
    content = content,
)
