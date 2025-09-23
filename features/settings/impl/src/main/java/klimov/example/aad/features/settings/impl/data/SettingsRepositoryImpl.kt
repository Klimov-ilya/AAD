package klimov.example.aad.features.settings.impl.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import klimov.example.aad.features.settings.api.SettingsContainer
import klimov.example.aad.features.settings.api.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SettingsRepository {
    private val REFRESH_PERIOD_KEY = longPreferencesKey("refresh_period")
    private val FIRST_LAUNCH_DELAY_KEY = longPreferencesKey("first_launch_delay")

    private val _state = MutableStateFlow(SettingsContainer.Companion.initial)
    override val state: StateFlow<SettingsContainer> = _state.asStateFlow()

    init {
        CoroutineScope(Job() + ioDispatcher).launch {
            readSettings()
        }
    }

    override suspend fun saveSettings(periodic: Long, delayed: Long) {
        withContext(ioDispatcher) {
            dataStore.edit { preferences: MutablePreferences ->
                preferences[REFRESH_PERIOD_KEY] = periodic
                preferences[FIRST_LAUNCH_DELAY_KEY] = delayed
            }
            updateState(periodic, delayed)
        }
    }

    override suspend fun readSettings() {
        withContext(ioDispatcher) {
            dataStore.data.collect { preferences ->
                val periodic = preferences[REFRESH_PERIOD_KEY]
                    ?: SettingsContainer.Companion.DEFAULT_REFRESH_PERIOD_IN_MINUTE
                val delayed = preferences[FIRST_LAUNCH_DELAY_KEY]
                    ?: SettingsContainer.Companion.FIRST_LAUNCH_DELAY

                updateState(periodic, delayed)
            }
        }
    }

    private fun updateState(periodic: Long, delayed: Long) {
        _state.update { curr -> curr.copy(periodic, delayed) }
    }
}