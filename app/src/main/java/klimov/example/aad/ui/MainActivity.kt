package klimov.example.aad.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import klimov.example.aad.features.settings.api.SettingsNavigation
import klimov.example.aad.news.list.api.NewsNavigation
import klimov.example.aad.sdk.ui.AADTheme
import klimov.example.aad.ui.navigation.BottomBar
import klimov.example.aad.ui.navigation.BottomBarNavGraph
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val newsNavigation: NewsNavigation by inject<NewsNavigation>()
    private val settingsNavigation: SettingsNavigation by inject<SettingsNavigation>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AADTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(navController)
                    }
                ) {
                    BottomBarNavGraph(
                        newsNavigation = newsNavigation,
                        settingsNavigation = settingsNavigation,
                        navController = navController
                    )
                }
            }
        }
    }
}