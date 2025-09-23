package klimov.example.aad.news.list.api

import klimov.example.aad.sdk.storage.news.entity.News

data class NewsResponse(val nextPage: Int?, val news: List<News>)