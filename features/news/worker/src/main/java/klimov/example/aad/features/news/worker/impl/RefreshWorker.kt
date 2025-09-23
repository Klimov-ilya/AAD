package klimov.example.aad.features.news.worker.impl

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import klimov.example.aad.news.list.api.NewsRefreshProvider
import org.koin.java.KoinJavaComponent.inject
import kotlin.getValue

internal class RefreshWorker(context: Context, workParams: WorkerParameters) : CoroutineWorker(context, workParams) {
    private val refreshProvider: NewsRefreshProvider by inject<NewsRefreshProvider>(NewsRefreshProvider::class.java)

    override suspend fun doWork(): Result {
        refreshProvider.refreshData()
        return Result.success()
    }
}