package klimov.example.aad.features.settings.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import klimov.example.aad.features.settings.api.SettingsNavigation
import klimov.example.aad.features.settings.api.SettingsRepository
import klimov.example.aad.features.settings.impl.data.SettingsRepositoryImpl
import klimov.example.aad.features.settings.impl.navigation.SettingsNavigationImpl
import klimov.example.aad.features.settings.impl.ui.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.io.File

val settingsModule = module {
    factory<DataStore<Preferences>> {
        provideDataStore(androidContext())
    }
    single<SettingsNavigation> { SettingsNavigationImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl(dataStore = get()) }
    viewModel { SettingsViewModel(repository = get()) }
}

private fun provideDataStore(context: Context): DataStore<Preferences> {
    val name = "Application setting"
    val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        corruptionHandler = null,
        migrations = emptyList(),
        scope = CoroutineScope(Job() + Dispatchers.IO)
    ) {
        File(context.filesDir, "datastore/$name.preferences_pb")
    }
    return dataStore
}