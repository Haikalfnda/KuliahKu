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

class RecycleBinViewModel(
    private val tugasDao: TugasDao,
    kategoriDao: KategoriDao
) : ViewModel() {

    val data: StateFlow<List<Tugas>> = tugasDao.getTugasTerhapus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val kategori: StateFlow<List<Kategori>> = kategoriDao.getKategori().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun restore(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tugasDao.restoreFromRecycleBin(id)
        }
    }

    fun deletePermanent(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tugasDao.deletePermanent(id)
        }
    }
}