package klimov.example.aad.features.settings.impl

import klimov.example.aad.features.settings.api.SettingsRepository
import klimov.example.aad.features.settings.impl.data.SettingsRepositoryImpl
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(dataStore = get()) }
}