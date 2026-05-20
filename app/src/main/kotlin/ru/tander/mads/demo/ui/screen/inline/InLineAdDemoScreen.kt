package ru.tander.mads.demo.ui.screen.inline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.screen.MadsDemoScreenContentContainer
import ru.tander.mads.inline.model.InLineAdContent

@Composable
fun InLineAdDemoScreen(
    onBackPressed: () -> Unit,
    viewModel: InLineAdDemoViewModel = viewModel(),
) = MadsDemoScreenContentContainer(
    labelRes = R.string.ad_format_in_line,
    onBackPressed = onBackPressed,
) { paddingValues ->

    val viewStateState = viewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (val viewState = viewStateState.value) {
            is InLineAdDemoViewModel.ViewState.InProgress -> {
                CircularProgressIndicator()
            }
            is InLineAdDemoViewModel.ViewState.Success -> {
                when (viewState.contentType) {
                    is InLineAdContent.Type.Empty -> {
                        // nothing to show, no-op
                    }
                    is InLineAdContent.Type.Banner.Carousel -> {
                        // default carousel appearance looks good to us, show as is
                        viewState.showContent(Modifier)
                    }
                    is InLineAdContent.Type.Banner.SingleItem -> {
                        // slightly adjust single item appearance by adding horizontal paddings
                        viewState.showContent(Modifier.padding(horizontal = 12.dp))
                    }
                    else -> {
                        // unknown content type, show as is
                        viewState.showContent(Modifier)
                    }
                }
            }
            is InLineAdDemoViewModel.ViewState.Failure -> {
                Text(stringResource(R.string.in_line_ad_loading_failure))
            }
        }
    }
}
