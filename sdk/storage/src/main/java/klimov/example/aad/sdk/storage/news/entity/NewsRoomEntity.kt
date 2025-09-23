package klimov.example.aad.sdk.storage.news.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val details: String,
    var page: Int
)