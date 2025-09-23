package klimov.example.aad.features.news.list.impl.data

import android.content.Context
import com.google.gson.Gson
import klimov.example.aad.news.list.api.NewsNetworkApi
import klimov.example.aad.news.list.api.NewsResponse
import klimov.example.aad.sdk.storage.news.entity.NewsRoomEntity

internal class NewsNetworkApiImpl(
    application: Context,
    fileName: String
) : NewsNetworkApi {
    private var source: List<NewsRoomEntity>? = null
    private val maxItem = 10
    private val size: Int
        get() =  source?.size ?: 0

    init {
        loadAssetsFromFile(application, fileName)
    }

    override fun fetchData(page: Int): NewsResponse {
        var end = page * maxItem + 1
        val start = end - maxItem - 1

        if (start >= size) return NewsResponse(nextPage = null, news = emptyList())
        if (end > size) end = size - 1

        val responseList = source?.subList(start, end) ?: emptyList()

        return NewsResponse(nextPage = page + 1, news = responseList)
    }

    private fun loadAssetsFromFile(context: Context, fileName: String) {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)

        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, charset = Charsets.UTF_8)
        val gson = Gson()
        source = gson.fromJson(json, Array<NewsRoomEntity>::class.java).toList()
    }
}