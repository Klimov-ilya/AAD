package klimov.example.aad.data.network

import klimov.example.aad.sdk.storage.news.entity.News

data class NewsResponse(val nextPage: Int?, val news: List<News>)