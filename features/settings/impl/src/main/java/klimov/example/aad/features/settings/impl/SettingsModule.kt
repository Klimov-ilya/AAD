package klimov.example.aad.features.settings.impl

import klimov.example.aad.features.settings.api.SettingsNavigation
import klimov.example.aad.features.settings.api.SettingsRepository
import klimov.example.aad.features.settings.impl.data.SettingsRepositoryImpl
import klimov.example.aad.features.settings.impl.navigation.SettingsNavigationImpl
import klimov.example.aad.features.settings.impl.ui.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsNavigation> { SettingsNavigationImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl(dataStore = get()) }
    viewModel { SettingsViewModel(repository = get()) }
}