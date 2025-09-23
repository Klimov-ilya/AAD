package klimov.example.aad.sdk.storage.news.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import klimov.example.aad.sdk.storage.news.entity.NewsRoomEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsRoomEntity>)

    @Query("Select * from newsroomentity Order by page")
    fun getNews(): PagingSource<Int, NewsRoomEntity>

    @Query("Delete from newsroomentity")
    suspend fun clearAllNews()
}