package klimov.example.aad.features.news.list.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import klimov.example.aad.features.news.list.impl.data.NewsRemoteMediator
import klimov.example.aad.features.news.worker.api.WorkManagerService
import klimov.example.aad.news.list.api.NewsNetworkApi
import klimov.example.aad.news.list.api.NewsRefreshProvider
import klimov.example.aad.sdk.storage.news.NewsDatabase
import klimov.example.aad.sdk.storage.news.entity.NewsRoomEntity
import kotlinx.coroutines.flow.Flow

internal class NewsViewModel(
    private val newsNetworkApi: NewsNetworkApi,
    private val newsDatabase: NewsDatabase,
    private val workManagerService: WorkManagerService
)  : ViewModel(), NewsRefreshProvider {
    private var pagingItems: LazyPagingItems<NewsRoomEntity>? = null

    @OptIn(ExperimentalPagingApi::class)
    fun getNews(): Flow<PagingData<NewsRoomEntity>> {
        val mediator = NewsRemoteMediator(newsNetworkApi, newsDatabase)

        val pager = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            remoteMediator = mediator
        ) {
            newsDatabase.getNewsDao().getNews()
        }

        return pager
            .flow
            .cachedIn(viewModelScope)
    }

    fun attachPagingItems(paging: LazyPagingItems<NewsRoomEntity>?) {
        pagingItems = paging
    }

    fun detachPagingItems() {
        pagingItems = null
    }

    override fun refreshData() {
        pagingItems?.refresh()
    }

    fun launchPeriodicRefresh() {
        workManagerService.launchRefreshWork()
    }

    fun cancelPeriodicRefresh() {
        workManagerService.cancelRefreshWork()
    }

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}