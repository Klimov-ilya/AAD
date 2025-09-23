package klimov.example.aad.sdk.storage

import androidx.room.Room
import klimov.example.aad.sdk.storage.news.NewsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single<NewsDatabase> {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            "news_database"
        )
            .build()
    }
}