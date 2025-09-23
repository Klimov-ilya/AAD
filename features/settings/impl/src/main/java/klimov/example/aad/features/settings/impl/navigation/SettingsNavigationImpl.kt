package klimov.example.aad.features.settings.impl.navigation

import androidx.compose.runtime.Composable
import klimov.example.aad.features.settings.api.SettingsNavigation
import klimov.example.aad.features.settings.impl.ui.SettingsScreen

internal class SettingsNavigationImpl : SettingsNavigation {
    override fun getScreen(): @Composable (() -> Unit) = {
        SettingsScreen()
    }
}