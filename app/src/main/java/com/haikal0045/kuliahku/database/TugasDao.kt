package com.haikal0045.kuliahku.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.haikal0045.kuliahku.model.Tugas
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {

    @Insert
    suspend fun insert(tugas: Tugas)

    @Update
    suspend fun update(tugas: Tugas)

    @Query("SELECT * FROM tugas WHERE isDeleted = 0 ORDER BY deadline ASC")
    fun getTugasAktif(): Flow<List<Tugas>>

    @Query("SELECT * FROM tugas WHERE isDeleted = 1 ORDER BY deletedAt DESC")
    fun getTugasTerhapus(): Flow<List<Tugas>>

    @Query("SELECT * FROM tugas WHERE id = :id")
    suspend fun getTugasById(id: Long): Tugas?

    @Query("UPDATE tugas SET isDeleted = 1, deletedAt = :deletedAt WHERE id = :id")
    suspend fun moveToRecycleBin(id: Long, deletedAt: String)

    @Query("UPDATE tugas SET isDeleted = 0, deletedAt = NULL WHERE id = :id")
    suspend fun restoreFromRecycleBin(id: Long)

    @Query("DELETE FROM tugas WHERE id = :id")
    suspend fun deletePermanent(id: Long)
}