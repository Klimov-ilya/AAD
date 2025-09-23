package klimov.example.aad.data.work_manager

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import klimov.example.aad.features.settings.api.SettingsContainer
import klimov.example.aad.features.settings.api.SettingsRepository
import klimov.example.aad.ui.contract.WorkManagerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WorkManagerServiceImpl(
    private val context: Context,
    private val settingsRepository: SettingsRepository,
    private val isScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)
) : WorkManagerService {
    private var period: Long = SettingsContainer.DEFAULT_REFRESH_PERIOD_IN_MINUTE
    private var delayed: Long = SettingsContainer.FIRST_LAUNCH_DELAY

    init {
        isScope.launch {
            settingsRepository.state.collect { settings ->
                period = settings.periodic
                delayed = settings.delayed

                launchRefreshWork()
            }
        }
    }

    override fun launchRefreshWork() {
        val request = createRequest()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            uniqueWorkName = REFRESH_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            request = request
        )
    }

    override fun cancelRefreshWork() {
        WorkManager.getInstance(context).cancelUniqueWork(uniqueWorkName = REFRESH_WORK_NAME)
    }

    private fun createConstraints(): Constraints =
        Constraints
            .Builder()
            .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
            .build()

    private fun createRequest(): PeriodicWorkRequest {
        val constraints = createConstraints()
        val settingsContainer = settingsRepository.state.value

        return PeriodicWorkRequestBuilder<RefreshWorker>(
            repeatInterval = settingsContainer.periodic,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                timeUnit = TimeUnit.SECONDS,
                backoffPolicy = BackoffPolicy.LINEAR,
                backoffDelay = settingsContainer.delayed
            )
            .build()
    }

    companion object {
        const val REFRESH_WORK_NAME = "Refresh_work"
    }
}