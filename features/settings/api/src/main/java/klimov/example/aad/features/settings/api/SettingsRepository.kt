package klimov.example.aad.features.settings.api

import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    val state: StateFlow<SettingsContainer>
    suspend fun saveSettings(periodic: Long, delayed: Long)
    suspend fun readSettings()
}