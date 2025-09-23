package klimov.example.aad.features.news.list.impl.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import klimov.example.aad.news.list.api.NewsNetworkApi
import klimov.example.aad.sdk.storage.news.NewsDatabase
import klimov.example.aad.sdk.storage.news.entity.NewsRoomEntity
import klimov.example.aad.sdk.storage.news.entity.RemoteKeysRoomEntity
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
internal class NewsRemoteMediator(
    private val networkApi: NewsNetworkApi,
    private val storageApi: NewsDatabase
) : RemoteMediator<Int, NewsRoomEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (storageApi.getRemoteKeysDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsRoomEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosesToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey

                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey

                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = networkApi.fetchData(page = page)

            val news = apiResponse.news
            val endOfPaginationReached = news.isEmpty()

            storageApi.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    storageApi.getRemoteKeysDao().clearRemoteKeys()
                    storageApi.getNewsDao().clearAllNews()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page +1

                val remoteKeys = news.map { new ->
                    RemoteKeysRoomEntity(
                        newsId = new.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }
                storageApi.getRemoteKeysDao().insertAll(remoteKeys)
                storageApi.getNewsDao().insertAll(
                    news.onEachIndexed { _, movie -> movie.page = page }
                )
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosesToCurrentPosition(state: PagingState<Int, NewsRoomEntity>): RemoteKeysRoomEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                storageApi.getRemoteKeysDao().getRemoteKeyByNewsId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, NewsRoomEntity>): RemoteKeysRoomEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            storageApi.getRemoteKeysDao().getRemoteKeyByNewsId(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NewsRoomEntity>): RemoteKeysRoomEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            storageApi.getRemoteKeysDao().getRemoteKeyByNewsId(movie.id)
        }
    }
}