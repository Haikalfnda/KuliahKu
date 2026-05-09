package com.haikal0045.kuliahku.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.haikal0045.kuliahku.R
import com.haikal0045.kuliahku.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    id: Long? = null
) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var mataKuliah by remember { mutableStateOf("") }
    var judul by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var tanggalDibuat by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id != null) {
            val tugas = viewModel.getTugas(id)

            if (tugas != null) {
                mataKuliah = tugas.mataKuliah
                judul = tugas.judul
                deadline = tugas.deadline
                deskripsi = tugas.deskripsi
                tanggalDibuat = tugas.tanggalDibuat
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            if (id == null) {
                                R.string.tambah_tugas
                            } else {
                                R.string.ubah_tugas
                            }
                        )
                    )
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (
                                mataKuliah.isBlank() ||
                                judul.isBlank() ||
                                deadline.isBlank() ||
                                deskripsi.isBlank()
                            ) {
                                Toast.makeText(
                                    context,
                                    R.string.invalid,
                                    Toast.LENGTH_LONG
                                ).show()
                                return@IconButton
                            }

                            if (id == null) {
                                viewModel.insert(
                                    mataKuliah = mataKuliah,
                                    judul = judul,
                                    deadline = deadline,
                                    deskripsi = deskripsi
                                )
                            } else {
                                viewModel.update(
                                    id = id,
                                    mataKuliah = mataKuliah,
                                    judul = judul,
                                    deadline = deadline,
                                    deskripsi = deskripsi,
                                    tanggalDibuat = tanggalDibuat
                                )
                            }

                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        FormTugas(
            mataKuliah = mataKuliah,
            onMataKuliahChange = { mataKuliah = it },
            judul = judul,
            onJudulChange = { judul = it },
            deadline = deadline,
            onDeadlineChange = { deadline = it },
            deskripsi = deskripsi,
            onDeskripsiChange = { deskripsi = it },
            modifier = Modifier.padding(innerPadding)
        )

        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                onConfirmation = {
                    showDialog = false
                    viewModel.delete(id)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun FormTugas(
    mataKuliah: String,
    onMataKuliahChange: (String) -> Unit,
    judul: String,
    onJudulChange: (String) -> Unit,
    deadline: String,
    onDeadlineChange: (String) -> Unit,
    deskripsi: String,
    onDeskripsiChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = mataKuliah,
            onValueChange = onMataKuliahChange,
            label = {
                Text(text = stringResource(R.string.mata_kuliah))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = judul,
            onValueChange = onJudulChange,
            label = {
                Text(text = stringResource(R.string.judul_tugas))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = deadline,
            onValueChange = onDeadlineChange,
            label = {
                Text(text = stringResource(R.string.deadline))
            },
            singleLine = true,
            placeholder = {
                Text(text = "Contoh: 2026-05-20 23:59")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = onDeskripsiChange,
            label = {
                Text(text = stringResource(R.string.deskripsi))
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            minLines = 6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.opsi_lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }
    ) {
        DropdownMenuItem(
            text = {
                Text(text = stringResource(R.string.hapus_tugas))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            },
            onClick = {
                expanded = false
                onDeleteClick()
            }
        )
    }
}