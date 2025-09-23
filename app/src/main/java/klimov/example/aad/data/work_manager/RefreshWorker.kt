package klimov.example.aad.data.work_manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import klimov.example.aad.ui.AppViewModel
import org.koin.core.context.GlobalContext.get

class RefreshWorker(context: Context, workParams: WorkerParameters) : CoroutineWorker(context, workParams) {
    private val appViewModel: AppViewModel by get().inject()

    override suspend fun doWork(): Result {
        appViewModel.refreshData()
        return Result.success()
    }
}