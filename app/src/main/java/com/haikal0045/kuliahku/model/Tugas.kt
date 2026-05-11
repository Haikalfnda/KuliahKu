package com.haikal0045.kuliahku.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tugas",
    indices = [Index(value = ["kategoriId"])]
)
data class Tugas(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val kategoriId: Long = 1L,
    val mataKuliah: String,
    val judul: String,
    val deadline: String,
    val deskripsi: String,
    val tanggalDibuat: String,
    val isDeleted: Boolean = false,
    val deletedAt: String? = null
)