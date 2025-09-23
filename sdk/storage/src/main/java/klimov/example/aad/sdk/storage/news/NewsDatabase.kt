package klimov.example.aad.sdk.storage.news

import androidx.room.Database
import androidx.room.RoomDatabase
import klimov.example.aad.sdk.storage.news.dao.NewsDao
import klimov.example.aad.sdk.storage.news.dao.RemoteKeysDao
import klimov.example.aad.sdk.storage.news.entity.NewsRoomEntity
import klimov.example.aad.sdk.storage.news.entity.RemoteKeysRoomEntity

@Database(
    entities = [NewsRoomEntity::class, RemoteKeysRoomEntity::class],
    version = 1
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}