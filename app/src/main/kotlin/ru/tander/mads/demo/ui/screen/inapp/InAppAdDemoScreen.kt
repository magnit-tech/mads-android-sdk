package ru.tander.mads.demo.ui.screen.inapp

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.form
import ru.tander.mads.demo.ui.component.form.formButton
import ru.tander.mads.demo.ui.component.form.showToast
import ru.tander.mads.demo.ui.screen.MadsDemoScreenContentContainer
import ru.tander.mads.demo.ui.screen.inapp.component.InAppAdLoading
import ru.tander.mads.inapp.showing.InAppAdShowingAction
import ru.tander.mads.inapp.showing.InAppAdShowingEvent

@Composable
fun InAppAdDemoScreen(
    onBackPressed: () -> Unit,
    onConfigurationClick: () -> Unit,
    viewModel: InAppAdDemoViewModel = viewModel(),
) = MadsDemoScreenContentContainer(
    labelRes = R.string.ad_format_in_app,
    onBackPressed = onBackPressed,
    onConfigurationClick = onConfigurationClick,
) { paddingValues ->

    val fragmentActivity = (LocalActivity.current as FragmentActivity)

    val adLoadings = viewModel.adLoadings.collectAsStateWithLifecycle()

    LazyColumn(Modifier.padding(paddingValues)) {
        form(
            fieldsModels = viewModel.formFieldsModels,
            itemModifier = { Modifier.fillMaxWidth() },
        )
        formButton(
            onClick = viewModel::onLoadAdPressed,
            labelRes = R.string.in_app_load_ad,
            modifier = { Modifier.fillMaxWidth() },
        )
        items(adLoadings.value) { adLoading ->
            Spacer(
                modifier = Modifier.height(8.dp),
            )
            InAppAdLoading(
                model = adLoading,
                showLoadedAd = { loadedAd ->
                    loadedAd.show(fragmentActivity)
                },
                handleAdAction = { action ->
                    when (action) {
                        is InAppAdShowingAction.OnUrlClicked -> showToast(
                            context = fragmentActivity,
                            messageRes = R.string.in_app_ad_showing_callback_ad_deeplink_button_clicked,
                        )
                        is InAppAdShowingAction.OnPromocodeCopy -> showToast(
                            context = fragmentActivity,
                            messageRes = R.string.in_app_ad_showing_callback_ad_promo_code_button_clicked,
                        )
                        else -> showToast(
                            context = fragmentActivity,
                            messageRes = R.string.in_app_ad_showing_callback_unknown,
                        )
                    }
                },
                handleAdEvent = { event ->
                    when (event) {
                        is InAppAdShowingEvent.OnCreativeView -> showToast(
                            context = fragmentActivity,
                            messageRes = R.string.in_app_ad_showing_callback_ad_shown,
                        )
                        is InAppAdShowingEvent.OnCreativeDismissed -> showToast(
                            context = fragmentActivity,
                            messageRes = R.string.in_app_ad_showing_callback_ad_dismissed,
                        )
                        else -> showToast(
                            context = fragmentActivity,
                            messageRes = R.string.in_app_ad_showing_callback_unknown,
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
