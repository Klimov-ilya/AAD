package klimov.example.aad.features.settings.api

data class SettingsContainer(val periodic: Long, val delayed: Long) {
    companion object {
        const val DEFAULT_REFRESH_PERIOD_IN_MINUTE: Long = 15
        const val FIRST_LAUNCH_DELAY: Long = 10

        val initial: SettingsContainer = SettingsContainer(
            periodic = DEFAULT_REFRESH_PERIOD_IN_MINUTE,
            delayed = FIRST_LAUNCH_DELAY
        )
    }
}