package klimov.example.aad.news.list.api

import klimov.example.aad.sdk.storage.news.entity.NewsRoomEntity

data class NewsResponse(val nextPage: Int?, val news: List<NewsRoomEntity>)