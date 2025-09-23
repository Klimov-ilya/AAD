package klimov.example.aad.sdk.storage.news.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import klimov.example.aad.sdk.storage.news.entity.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<News>)

    @Query("Select * from news Order by page")
    fun getNews(): PagingSource<Int, News>

    @Query("Delete from news")
    suspend fun clearAllNews()
}