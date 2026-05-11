package com.haikal0045.kuliahku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.haikal0045.kuliahku.model.Kategori
import com.haikal0045.kuliahku.model.Tugas

@Database(
    entities = [Tugas::class, Kategori::class],
    version = 2,
    exportSchema = false
)
abstract class TugasDb : RoomDatabase() {

    abstract fun tugasDao(): TugasDao

    abstract fun kategoriDao(): KategoriDao

    companion object {
        @Volatile
        private var INSTANCE: TugasDb? = null

        fun getInstance(context: Context): TugasDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TugasDb::class.java,
                        "kuliahku.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}