package com.haikal0045.kuliahku.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.haikal0045.kuliahku.R
import com.haikal0045.kuliahku.model.Tugas
import com.haikal0045.kuliahku.navigation.Screen
import com.haikal0045.kuliahku.util.SettingsDataStore
import com.haikal0045.kuliahku.util.ViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    val settingsDataStore = remember {
        SettingsDataStore(context)
    }

    val showList by settingsDataStore.layoutFlow.collectAsState(initial = true)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                settingsDataStore.saveLayout(!showList)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (showList) {
                                Icons.Default.GridView
                            } else {
                                Icons.Default.ViewList
                            },
                            contentDescription = stringResource(
                                if (showList) R.string.grid else R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.tambah_tugas)
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(
            data = data,
            showList = showList,
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenContent(
    data: List<Tugas>,
    showList: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.list_kosong))
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) { tugas ->
                    TugasListItem(
                        tugas = tugas,
                        onClick = {
                            navController.navigate(Screen.FormUbah.withId(tugas.id))
                        }
                    )
                    HorizontalDivider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = modifier.fillMaxSize(),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 84.dp
                )
            ) {
                items(data) { tugas ->
                    TugasGridItem(
                        tugas = tugas,
                        onClick = {
                            navController.navigate(Screen.FormUbah.withId(tugas.id))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TugasListItem(
    tugas: Tugas,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = tugas.judul,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Mata kuliah: ${tugas.mataKuliah}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "Deadline: ${tugas.deadline}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = tugas.deskripsi,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "Dibuat: ${tugas.tanggalDibuat}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TugasGridItem(
    tugas: Tugas,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = tugas.judul,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = tugas.mataKuliah,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Deadline: ${tugas.deadline}",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = tugas.deskripsi,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}