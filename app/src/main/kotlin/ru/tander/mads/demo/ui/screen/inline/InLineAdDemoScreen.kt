import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.form
import ru.tander.mads.demo.ui.component.form.formButton
import ru.tander.mads.demo.ui.screen.MadsDemoScreenContentContainer
import ru.tander.mads.demo.ui.screen.inline.InLineAdDemoViewModel
import ru.tander.mads.demo.ui.screen.inline.InLineAdLoadingItem

@Composable
fun InLineAdDemoScreen(
    onBackPressed: () -> Unit,
    onConfigurationClick: () -> Unit,
    viewModel: InLineAdDemoViewModel = viewModel(),
) = MadsDemoScreenContentContainer(
    labelRes = R.string.ad_format_in_line,
    onBackPressed = onBackPressed,
    onConfigurationClick = onConfigurationClick,
) { paddingValues ->
    val adLoadings by viewModel.adLoadings.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.padding(paddingValues),

    ) {
        form(
            fieldsModels = viewModel.formFieldsModels,
            itemModifier = { Modifier.fillMaxWidth() },
        )

        formButton(
            onClick = viewModel::onLoadAdPressed,
            labelRes = R.string.in_app_load_ad,
            modifier = { Modifier.fillMaxWidth() },
        )

        items(
            items = adLoadings,
            key = { it.id },
        ) { item ->
            InLineAdLoadingItem(
                item = item
            )
        }
    }
}