package klimov.example.aad.data.setting_repository

data class SettingContainer(val periodic: Long, val delayed: Long) {
    companion object {
        const val DEFAULT_REFRESH_PERIOD_IN_MINUTE: Long = 15
        const val FIRST_LAUNCH_DELAY: Long = 10

        val initial: SettingContainer = SettingContainer(
            periodic = DEFAULT_REFRESH_PERIOD_IN_MINUTE,
            delayed = FIRST_LAUNCH_DELAY
        )
    }
}