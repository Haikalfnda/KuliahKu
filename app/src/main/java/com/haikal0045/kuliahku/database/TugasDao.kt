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

    @Query("SELECT * FROM tugas ORDER BY deadline ASC")
    fun getTugas(): Flow<List<Tugas>>

    @Query("SELECT * FROM tugas WHERE id = :id")
    suspend fun getTugasById(id: Long): Tugas?

    @Query("DELETE FROM tugas WHERE id = :id")
    suspend fun deleteById(id: Long)
}