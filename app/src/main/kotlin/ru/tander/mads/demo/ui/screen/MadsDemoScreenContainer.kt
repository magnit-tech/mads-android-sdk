package ru.tander.mads.demo.ui.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import ru.tander.mads.demo.ui.screen.formats.AdFormatsScreen
import ru.tander.mads.demo.ui.screen.inapp.InAppAdDemoScreen
import ru.tander.mads.demo.ui.screen.inline.InLineAdDemoScreen

@Composable
fun MadsDemoScreenContainer() {
    val backStack = rememberNavBackStack(
        Screen.AdFormatsScreen,
    )
    val entryProvider = entryProvider<NavKey> {
        entry<Screen.AdFormatsScreen> {
            AdFormatsScreen(
                onInAppAdFormatClick = { navigateToInAppAdDemoScreen(backStack) },
                onInLineAdFormatClick = { navigateToInLineAdDemoScreen(backStack) }
            )
        }
        entry<Screen.InAppAdDemoScreen> {
            InAppAdDemoScreen(
                onBackPressed = { navigateBack(backStack) },
            )
        }
        entry<Screen.InLineAdDemoScreen> {
            InLineAdDemoScreen(
                onBackPressed = { navigateBack(backStack) },
            )
        }
    }
    NavDisplay(
        backStack = backStack,
        onBack = { navigateBack(backStack) },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider,
    )
}

private fun navigateBack(backStack: NavBackStack<NavKey>) {
    backStack.removeLastOrNull()
}

private fun navigateToInAppAdDemoScreen(backStack: NavBackStack<NavKey>) {
    backStack.add(Screen.InAppAdDemoScreen)
}

private fun navigateToInLineAdDemoScreen(backStack: NavBackStack<NavKey>) {
    backStack.add(Screen.InLineAdDemoScreen)
}

private sealed interface Screen : NavKey {

    @Serializable
    data object AdFormatsScreen : Screen

    @Serializable
    data object InAppAdDemoScreen : Screen

    @Serializable
    data object InLineAdDemoScreen : Screen
}
