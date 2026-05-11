package com.haikal0045.kuliahku.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.haikal0045.kuliahku.model.Kategori
import kotlinx.coroutines.flow.Flow

@Dao
interface KategoriDao {

    @Insert
    suspend fun insert(kategori: Kategori)

    @Insert
    suspend fun insertAll(kategori: List<Kategori>)

    @Query("SELECT * FROM kategori ORDER BY nama ASC")
    fun getKategori(): Flow<List<Kategori>>

    @Query("SELECT COUNT(*) FROM kategori")
    suspend fun count(): Int
}