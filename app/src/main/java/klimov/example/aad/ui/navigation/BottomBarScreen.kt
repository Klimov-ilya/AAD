package klimov.example.aad.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    var route: String,
    var icon: ImageVector,
    var title: String
) {
    object News : BottomBarScreen(
        route = "news_screen",
        icon = Icons.Default.Home,
        title = "Новости"
    )

    object Settings : BottomBarScreen(
        route = "settings_screen",
        icon = Icons.Default.Settings,
        title = "Настройки"
    )
}