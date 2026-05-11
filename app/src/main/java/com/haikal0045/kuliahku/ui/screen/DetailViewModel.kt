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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(
    private val tugasDao: TugasDao,
    private val kategoriDao: KategoriDao
) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

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

    suspend fun getTugas(id: Long): Tugas? {
        return tugasDao.getTugasById(id)
    }

    fun insert(
        kategoriId: Long,
        mataKuliah: String,
        judul: String,
        deadline: String,
        deskripsi: String
    ) {
        val tugas = Tugas(
            kategoriId = kategoriId,
            mataKuliah = mataKuliah,
            judul = judul,
            deadline = deadline,
            deskripsi = deskripsi,
            tanggalDibuat = formatter.format(Date())
        )

        viewModelScope.launch(Dispatchers.IO) {
            tugasDao.insert(tugas)
        }
    }

    fun update(
        id: Long,
        kategoriId: Long,
        mataKuliah: String,
        judul: String,
        deadline: String,
        deskripsi: String,
        tanggalDibuat: String
    ) {
        val tugas = Tugas(
            id = id,
            kategoriId = kategoriId,
            mataKuliah = mataKuliah,
            judul = judul,
            deadline = deadline,
            deskripsi = deskripsi,
            tanggalDibuat = tanggalDibuat.ifBlank {
                formatter.format(Date())
            }
        )

        viewModelScope.launch(Dispatchers.IO) {
            tugasDao.update(tugas)
        }
    }

    fun moveToRecycleBin(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tugasDao.moveToRecycleBin(
                id = id,
                deletedAt = formatter.format(Date())
            )
        }
    }
}