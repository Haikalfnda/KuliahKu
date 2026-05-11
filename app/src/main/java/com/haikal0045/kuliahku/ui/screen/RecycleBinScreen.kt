package com.haikal0045.kuliahku.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.haikal0045.kuliahku.model.Kategori
import com.haikal0045.kuliahku.model.Tugas
import com.haikal0045.kuliahku.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(navController: NavHostController) {
    val factory = ViewModelFactory(androidx.compose.ui.platform.LocalContext.current)
    val viewModel: RecycleBinViewModel = viewModel(factory = factory)

    val data by viewModel.data.collectAsState()
    val kategori by viewModel.kategori.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Recycle Bin")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            text = "←",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (data.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Recycle Bin kosong")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = data,
                    key = { tugas -> tugas.id }
                ) { tugas ->
                    RecycleBinItem(
                        tugas = tugas,
                        namaKategori = getNamaKategoriRecycle(tugas.kategoriId, kategori),
                        onRestore = {
                            viewModel.restore(tugas.id)
                        },
                        onDeletePermanent = {
                            viewModel.deletePermanent(tugas.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecycleBinItem(
    tugas: Tugas,
    namaKategori: String,
    onRestore: () -> Unit,
    onDeletePermanent: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = tugas.judul,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(text = "Kategori: $namaKategori")
            Text(text = "Mata kuliah: ${tugas.mataKuliah}")
            Text(text = "Dihapus: ${tugas.deletedAt ?: "-"}")

            HorizontalDivider()

            Button(
                onClick = onRestore,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Kembalikan")
            }

            OutlinedButton(
                onClick = onDeletePermanent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Hapus Permanen")
            }
        }
    }
}

fun getNamaKategoriRecycle(
    kategoriId: Long,
    kategori: List<Kategori>
): String {
    return kategori.firstOrNull { it.id == kategoriId }?.nama ?: "Tanpa kategori"
}