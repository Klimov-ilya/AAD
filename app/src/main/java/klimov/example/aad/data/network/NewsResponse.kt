package klimov.example.aad.data.network

import klimov.example.aad.data.database.entity.News

data class NewsResponse(val nextPage: Int?, val news: List<News>)