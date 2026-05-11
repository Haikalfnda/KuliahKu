package com.haikal0045.kuliahku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.haikal0045.kuliahku.navigation.SetupNavGraph
import com.haikal0045.kuliahku.ui.theme.KuliahKuTheme
import com.haikal0045.kuliahku.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settingsDataStore = remember {
                SettingsDataStore(applicationContext)
            }

            val themeColor by settingsDataStore.themeColorFlow.collectAsState(initial = 0)

            KuliahKuTheme(themeColor = themeColor) {
                SetupNavGraph()
            }
        }
    }
}