package klimov.example.aad.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id")
    val newsId: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo("created_at")
    val createdAt: Long = System.currentTimeMillis()
)