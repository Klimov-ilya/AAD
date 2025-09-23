package klimov.example.aad

import android.app.Application
import klimov.example.aad.di.appModule
import klimov.example.aad.features.settings.impl.settingsModule
import klimov.example.aad.sdk.storage.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, settingsModule, storageModule)
        }
    }
}