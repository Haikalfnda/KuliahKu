package com.haikal0045.kuliahku.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.haikal0045.kuliahku.R
import com.haikal0045.kuliahku.util.SettingsDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val settingsDataStore = remember {
        SettingsDataStore(context)
    }

    val selectedTheme by settingsDataStore.themeColorFlow.collectAsState(initial = 0)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pilih Tema")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Pilih warna tema aplikasi yang kamu suka.")

            ThemeButton(
                text = "Ungu",
                selected = selectedTheme == 0,
                onClick = {
                    scope.launch {
                        settingsDataStore.saveThemeColor(0)
                    }
                }
            )

            ThemeButton(
                text = "Biru",
                selected = selectedTheme == 1,
                onClick = {
                    scope.launch {
                        settingsDataStore.saveThemeColor(1)
                    }
                }
            )

            ThemeButton(
                text = "Hijau",
                selected = selectedTheme == 2,
                onClick = {
                    scope.launch {
                        settingsDataStore.saveThemeColor(2)
                    }
                }
            )

            ThemeButton(
                text = "Merah",
                selected = selectedTheme == 3,
                onClick = {
                    scope.launch {
                        settingsDataStore.saveThemeColor(3)
                    }
                }
            )
        }
    }
}

@Composable
fun ThemeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    if (selected) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "$text ✓")
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = text)
        }
    }
}
