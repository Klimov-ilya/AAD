package klimov.example.aad.ui.contract

import klimov.example.aad.data.setting_repository.SettingContainer
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    val state: StateFlow<SettingContainer>
    suspend fun saveSettings(periodic: Long, delayed: Long)
    suspend fun readSettings()
}