package klimov.example.aad.ui.contract

interface WorkManagerService {
    fun launchRefreshWork()
    fun cancelRefreshWork()
}