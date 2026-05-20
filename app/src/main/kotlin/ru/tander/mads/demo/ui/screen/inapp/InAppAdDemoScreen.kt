package ru.tander.mads.demo.ui.screen.inapp

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.screen.MadsDemoScreenContentContainer

@Composable
fun InAppAdDemoScreen(
    onBackPressed: () -> Unit,
    viewModel: InAppAdDemoViewModel = viewModel(),
) = MadsDemoScreenContentContainer(
    labelRes = R.string.ad_format_in_app,
    onBackPressed = onBackPressed,
) { paddingValues ->

    val fragmentActivity = LocalActivity.current as FragmentActivity

    val viewStateState = viewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (val viewState = viewStateState.value) {
            is InAppAdDemoViewModel.ViewState.InProgress -> {
                CircularProgressIndicator()
            }
            is InAppAdDemoViewModel.ViewState.Success -> {
                Text(
                    text = stringResource(R.string.in_app_ad_loading_success),
                )
                Spacer(
                    modifier = Modifier.height(8.dp),
                )
                Button(
                    onClick = { viewState.showContent.invoke(fragmentActivity) },
                    content = { Text(stringResource(R.string.in_app_show_loaded_ad)) }
                )
            }
            is InAppAdDemoViewModel.ViewState.Failure -> {
                Text(stringResource(R.string.in_app_ad_loading_failure))
            }
        }
    }
}
