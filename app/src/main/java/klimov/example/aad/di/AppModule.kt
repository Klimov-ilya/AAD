package klimov.example.aad.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import klimov.example.aad.data.database.NewsDatabase
import klimov.example.aad.data.network.NewsNetworkApiImpl
import klimov.example.aad.data.setting_repository.SettingsRepositoryImpl
import klimov.example.aad.data.work_manager.WorkManagerServiceImpl
import klimov.example.aad.ui.AppViewModel
import klimov.example.aad.ui.contract.NewsNetworkApi
import klimov.example.aad.ui.contract.SettingsRepository
import klimov.example.aad.ui.contract.WorkManagerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single<NewsNetworkApi> {
        NewsNetworkApiImpl(
            application = androidContext(),
            fileName = "news.json"
        )
    }
    single<NewsDatabase> {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            "news_database"
        )
            .build()
    }
    factory<DataStore<Preferences>> {
        provideDataStore(androidContext())
    }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<WorkManagerService> { WorkManagerServiceImpl(androidApplication(), get()) }
    single<AppViewModel> { AppViewModel(get(), get(), get(), get()) }
}

fun provideDataStore(context: Context): DataStore<Preferences> {
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