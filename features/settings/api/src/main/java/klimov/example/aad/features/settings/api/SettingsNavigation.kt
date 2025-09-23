package klimov.example.aad.features.settings.api

import androidx.compose.runtime.Composable

interface SettingsNavigation {
    fun getScreen(): @Composable () -> Unit
}