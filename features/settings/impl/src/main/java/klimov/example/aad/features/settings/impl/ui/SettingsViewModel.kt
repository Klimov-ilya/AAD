package klimov.example.aad.features.settings.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import klimov.example.aad.features.settings.api.SettingsContainer
import klimov.example.aad.features.settings.api.SettingsRepository
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {
    fun saveSetting(periodic: Long, delayed: Long) {
        viewModelScope.launch {
            repository.saveSettings(periodic, delayed)
        }
    }

    fun getCurrentSettings(): SettingsContainer {
        return repository.state.value
    }
}