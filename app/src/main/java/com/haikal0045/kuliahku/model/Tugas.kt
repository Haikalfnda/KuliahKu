package com.haikal0045.kuliahku.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tugas")
data class Tugas(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val mataKuliah: String,
    val judul: String,
    val deadline: String,
    val deskripsi: String,
    val tanggalDibuat: String
)