package klimov.example.aad.news.list.api

interface NewsNetworkApi {
    fun fetchData(page: Int): NewsResponse
}