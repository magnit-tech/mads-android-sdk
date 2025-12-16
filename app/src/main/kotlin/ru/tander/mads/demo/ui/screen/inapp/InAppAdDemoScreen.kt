package ru.tander.mads.demo.ui.screen.inapp

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tander.mads.Mads
import ru.tander.mads.demo.R
import ru.tander.mads.demo.ui.component.form.form
import ru.tander.mads.demo.ui.component.form.formButton
import ru.tander.mads.demo.ui.screen.MadsDemoScreenContentContainer
import ru.tander.mads.demo.ui.screen.inapp.component.InAppAdLoading
import ru.tander.mads.inapp.model.InAppAd
import ru.tander.mads.inapp.showing.events.InAppAdShowingEventsCallback

private const val AD_SHOWING_TAG = "madsDemoInAppAd"

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

    DisposableEffect(fragmentActivity) {
        val adShowingCallback = DemoInAppAdShowingEventCallback(
            context = fragmentActivity,
        )
        val adShowingCallbackSubscription = Mads.subscribeToInAppAdShowingEvents(
            callback = adShowingCallback,
            tag = AD_SHOWING_TAG,
        )
        onDispose(adShowingCallbackSubscription::cancel)
    }

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
                    showLoadedAd(
                        activity = fragmentActivity,
                        loadedAd = loadedAd,
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private fun showLoadedAd(
    activity: FragmentActivity,
    loadedAd: InAppAd,
): Unit = Mads.showInAppAd(
    activity = activity,
    ad = loadedAd,
    tag = AD_SHOWING_TAG,
)

private class DemoInAppAdShowingEventCallback(
    private val context: Context,
) : InAppAdShowingEventsCallback {

    override fun onAdShown(): Unit = showToast(
        messageRes = R.string.in_app_ad_showing_callback_ad_shown,
    )

    override fun onAdFailedToShow(failure: Throwable): Unit = showToast(
        messageRes = R.string.in_app_ad_showing_callback_ad_failed_to_show,
    )

    override fun onAdDismissed(): Unit = showToast(
        messageRes = R.string.in_app_ad_showing_callback_ad_dismissed,
    )

    override fun onAdDeeplinkButtonClicked(deeplink: String): Unit = showToast(
        messageRes = R.string.in_app_ad_showing_callback_ad_deeplink_button_clicked,
    )

    override fun onAdCopyPromoCodeButtonClicked(promoCode: String): Unit = showToast(
        messageRes = R.string.in_app_ad_showing_callback_ad_promo_code_button_clicked,
    )

    private fun showToast(@StringRes messageRes: Int) {
        Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
    }
}
