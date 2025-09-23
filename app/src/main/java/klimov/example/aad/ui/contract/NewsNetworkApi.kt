package klimov.example.aad.ui.contract

import klimov.example.aad.data.network.NewsResponse

interface NewsNetworkApi {
    fun fetchData(page: Int): NewsResponse
}