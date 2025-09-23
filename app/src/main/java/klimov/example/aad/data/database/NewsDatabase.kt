package klimov.example.aad.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import klimov.example.aad.data.database.entity.News
import klimov.example.aad.data.database.entity.RemoteKeys

@Database(
    entities = [News::class, RemoteKeys::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}