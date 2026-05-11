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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Palette
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.haikal0045.kuliahku.R
import com.haikal0045.kuliahku.model.Kategori
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
    val kategori by viewModel.kategori.collectAsState()

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
                colors = TopAppBarDefaults.topAppBarColors(
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
                            painter = painterResource(
                                id = if (showList) {
                                    R.drawable.baseline_grid_view_24
                                } else {
                                    R.drawable.baseline_view_list_24
                                }
                            ),
                            contentDescription = stringResource(
                                if (showList) {
                                    R.string.grid
                                } else {
                                    R.string.list
                                }
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(Screen.RecycleBin.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Recycle Bin",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Tema.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Pilih Tema",
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
            kategori = kategori,
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
    kategori: List<Kategori>,
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
                items(
                    items = data,
                    key = { tugas -> tugas.id }
                ) { tugas ->
                    TugasListItem(
                        tugas = tugas,
                        namaKategori = getNamaKategori(tugas.kategoriId, kategori),
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
                items(
                    items = data,
                    key = { tugas -> tugas.id }
                ) { tugas ->
                    TugasGridItem(
                        tugas = tugas,
                        namaKategori = getNamaKategori(tugas.kategoriId, kategori),
                        onClick = {
                            navController.navigate(Screen.FormUbah.withId(tugas.id))
                        }
                    )
                }
            }
        }
    }
}

fun getNamaKategori(
    kategoriId: Long,
    kategori: List<Kategori>
): String {
    return kategori.firstOrNull { it.id == kategoriId }?.nama ?: "Tanpa kategori"
}

@Composable
fun TugasListItem(
    tugas: Tugas,
    namaKategori: String,
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
            text = "Kategori: $namaKategori",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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
    namaKategori: String,
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
                text = namaKategori,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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