package klimov.example.aad.sdk.storage.news.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import klimov.example.aad.sdk.storage.news.entity.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("Select * from remote_keys where news_id = :id")
    suspend fun getRemoteKeyByNewsId(id: Int): RemoteKeys?

    @Query("Delete from remote_keys")
    suspend fun clearRemoteKeys()

    @Query("Select created_at from remote_keys order by created_at desc limit 1")
    suspend fun getCreationTime(): Long?
}