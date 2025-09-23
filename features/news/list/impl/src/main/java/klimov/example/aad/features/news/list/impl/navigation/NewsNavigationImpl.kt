package klimov.example.aad.features.news.list.impl.navigation

import androidx.compose.runtime.Composable
import klimov.example.aad.features.news.list.impl.ui.NewsScreen
import klimov.example.aad.news.list.api.NewsNavigation

internal class NewsNavigationImpl : NewsNavigation {
    override fun getScreen(): @Composable (() -> Unit) = {
        NewsScreen()
    }
}