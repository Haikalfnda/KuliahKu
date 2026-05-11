package com.haikal0045.kuliahku.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haikal0045.kuliahku.database.KategoriDao
import com.haikal0045.kuliahku.database.TugasDao
import com.haikal0045.kuliahku.model.Kategori
import com.haikal0045.kuliahku.model.Tugas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val tugasDao: TugasDao,
    private val kategoriDao: KategoriDao
) : ViewModel() {

    val data: StateFlow<List<Tugas>> = tugasDao.getTugasAktif().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val kategori: StateFlow<List<Kategori>> = kategoriDao.getKategori().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        insertKategoriAwal()
    }

    private fun insertKategoriAwal() {
        viewModelScope.launch(Dispatchers.IO) {
            if (kategoriDao.count() == 0) {
                kategoriDao.insertAll(
                    listOf(
                        Kategori(nama = "Tugas Individu"),
                        Kategori(nama = "Tugas Kelompok"),
                        Kategori(nama = "Quiz"),
                        Kategori(nama = "Ujian"),
                        Kategori(nama = "Praktikum")
                    )
                )
            }
        }
    }
}