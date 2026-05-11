package com.haikal0045.kuliahku.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.haikal0045.kuliahku.database.TugasDb
import com.haikal0045.kuliahku.ui.screen.DetailViewModel
import com.haikal0045.kuliahku.ui.screen.MainViewModel
import com.haikal0045.kuliahku.ui.screen.RecycleBinViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    private val db = TugasDb.getInstance(context)
    private val tugasDao = db.tugasDao()
    private val kategoriDao = db.kategoriDao()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(tugasDao, kategoriDao) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(tugasDao, kategoriDao) as T
        }

        if (modelClass.isAssignableFrom(RecycleBinViewModel::class.java)) {
            return RecycleBinViewModel(tugasDao, kategoriDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}