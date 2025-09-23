package klimov.example.aad.news.list.api

import androidx.compose.runtime.Composable

interface NewsNavigation {
    fun getScreen(): @Composable () -> Unit
}