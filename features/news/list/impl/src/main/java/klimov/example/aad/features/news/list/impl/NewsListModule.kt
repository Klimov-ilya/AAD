package klimov.example.aad.features.news.list.impl

import klimov.example.aad.features.news.list.impl.data.NewsNetworkApiImpl
import klimov.example.aad.features.news.list.impl.navigation.NewsNavigationImpl
import klimov.example.aad.features.news.list.impl.ui.NewsViewModel
import klimov.example.aad.news.list.api.NewsNavigation
import klimov.example.aad.news.list.api.NewsNetworkApi
import klimov.example.aad.news.list.api.NewsRefreshProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val newsListModule = module {
    single<NewsNetworkApi> {
        NewsNetworkApiImpl(application = androidContext(), fileName = "news.json")
    }
    single { NewsViewModel(get(), get(), get()) } bind NewsViewModel::class bind NewsRefreshProvider::class
    single<NewsNavigation> { NewsNavigationImpl() }
}