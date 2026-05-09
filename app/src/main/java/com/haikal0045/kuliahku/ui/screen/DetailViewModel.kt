package com.haikal0045.kuliahku.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haikal0045.kuliahku.database.TugasDao
import com.haikal0045.kuliahku.model.Tugas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: TugasDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    suspend fun getTugas(id: Long): Tugas? {
        return dao.getTugasById(id)
    }

    fun insert(
        mataKuliah: String,
        judul: String,
        deadline: String,
        deskripsi: String
    ) {
        val tugas = Tugas(
            mataKuliah = mataKuliah,
            judul = judul,
            deadline = deadline,
            deskripsi = deskripsi,
            tanggalDibuat = formatter.format(Date())
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(tugas)
        }
    }

    fun update(
        id: Long,
        mataKuliah: String,
        judul: String,
        deadline: String,
        deskripsi: String,
        tanggalDibuat: String
    ) {
        val tugas = Tugas(
            id = id,
            mataKuliah = mataKuliah,
            judul = judul,
            deadline = deadline,
            deskripsi = deskripsi,
            tanggalDibuat = tanggalDibuat.ifBlank {
                formatter.format(Date())
            }
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(tugas)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}