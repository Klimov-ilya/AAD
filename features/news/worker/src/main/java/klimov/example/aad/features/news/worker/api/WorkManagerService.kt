package klimov.example.aad.features.news.worker.api

interface WorkManagerService {
    fun launchRefreshWork()
    fun cancelRefreshWork()
}