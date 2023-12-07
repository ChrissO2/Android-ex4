package com.example.exercise4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Champion::class], version = 1, exportSchema = false)
abstract class ChampionDB : RoomDatabase() {
    abstract fun championDao(): ChampionDao?
    companion object {
        private var DB_INSTANCE: ChampionDB? = null
        @Synchronized
        open fun getDatabase(context: Context): ChampionDB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = databaseBuilder(
                    context.applicationContext,
                    ChampionDB::class.java,
                    "champion_database"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE
        }
    }
}