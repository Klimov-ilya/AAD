package klimov.example.aad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import klimov.example.aad.data.NewsRemoteMediator
import klimov.example.aad.data.database.NewsDatabase
import klimov.example.aad.data.database.entity.News
import klimov.example.aad.features.settings.api.SettingsContainer
import klimov.example.aad.features.settings.api.SettingsRepository
import klimov.example.aad.ui.contract.NewsNetworkApi
import klimov.example.aad.ui.contract.WorkManagerService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppViewModel(
    private val newsNetworkApi: NewsNetworkApi,
    private val newsDatabase: NewsDatabase,
    private val workManagerService: WorkManagerService,
    private val settingsRepository: SettingsRepository
)  : ViewModel() {
    private var pagingItems: LazyPagingItems<News>? = null

    @OptIn(ExperimentalPagingApi::class)
    fun getNews(): Flow<PagingData<News>> {
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

    fun attachPagingItems(paging: LazyPagingItems<News>?) {
        pagingItems = paging
    }

    fun detachPagingItems() {
        pagingItems = null
    }

    fun refreshData() {
        pagingItems?.refresh()
    }

    fun launchPeriodicRefresh() {
        workManagerService.launchRefreshWork()
    }

    fun cancelPeriodicRefresh() {
        workManagerService.cancelRefreshWork()
    }

    fun saveSetting(periodic: Long, delayed: Long) {
        viewModelScope.launch {
            settingsRepository.saveSettings(periodic, delayed)
        }
    }

    fun getCurrentSettings(): SettingsContainer {
        return settingsRepository.state.value
    }

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}