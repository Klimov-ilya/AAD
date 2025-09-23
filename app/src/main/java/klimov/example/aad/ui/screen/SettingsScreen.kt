package klimov.example.aad.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import klimov.example.aad.R
import klimov.example.aad.features.settings.api.SettingsContainer
import klimov.example.aad.ui.AppViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = koinViewModel()
) {
    val currentSetting = appViewModel.getCurrentSettings()
    var periodic by rememberSaveable { mutableStateOf(currentSetting.periodic.toString()) }
    var delayed by rememberSaveable { mutableStateOf(currentSetting.delayed.toString()) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 4.dp, end = 4.dp, bottom = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(top = 32.dp))
        TextField(
            modifier = Modifier
                .padding(all = 8.dp),
            value = periodic,
            onValueChange = {
                periodic = it
            },
            label = { Text(stringResource(R.string.periodic_refresh_minutes)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(Modifier.padding(top = 16.dp))
        TextField(
            modifier = Modifier
                .padding(all = 8.dp),
            value = delayed,
            onValueChange = {
                delayed = it
            },
            label = { Text(stringResource(R.string.delay_first_launch_sec)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(Modifier.padding(top = 16.dp))
        Button(onClick = {
            val periodicValue = periodic.toLongOrNull() ?: SettingsContainer.DEFAULT_REFRESH_PERIOD_IN_MINUTE
            val delayValue = delayed.toLongOrNull() ?: SettingsContainer.FIRST_LAUNCH_DELAY
            appViewModel.saveSetting(periodic = periodicValue, delayed = delayValue)
        }) {
            Text(stringResource(R.string.save_setting))
        }
    }
}