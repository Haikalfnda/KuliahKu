package com.haikal0045.kuliahku.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.haikal0045.kuliahku.database.TugasDb
import com.haikal0045.kuliahku.ui.screen.DetailViewModel
import com.haikal0045.kuliahku.ui.screen.MainViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    private val dao = TugasDb.getInstance(context).dao

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}