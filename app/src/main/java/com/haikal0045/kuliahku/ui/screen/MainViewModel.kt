package com.haikal0045.kuliahku.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haikal0045.kuliahku.database.TugasDao
import com.haikal0045.kuliahku.model.Tugas
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: TugasDao) : ViewModel() {

    val data: StateFlow<List<Tugas>> = dao.getTugas().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}