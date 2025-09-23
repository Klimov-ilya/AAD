package klimov.example.aad.features.news.worker.api

import klimov.example.aad.features.news.worker.impl.WorkManagerServiceImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val newsWorkManagerModule = module {
    single<WorkManagerService> { WorkManagerServiceImpl(androidApplication(), get()) }
}