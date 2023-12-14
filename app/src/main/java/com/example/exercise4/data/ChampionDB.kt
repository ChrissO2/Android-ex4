package com.example.exercise4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.exercise4.livedata.ListDao

@Database(entities = [Champion::class], version = 1, exportSchema = false)
abstract class ChampionDB : RoomDatabase() {
    abstract fun championDao(): ChampionDao?

    abstract fun listDao(): ListDao
    companion object {

        @Volatile
        private var DB_INSTANCE: ChampionDB? = null
//        fun getDatabase(context: Context): ChampionDB? {
//            if (DB_INSTANCE == null) {
//                DB_INSTANCE = databaseBuilder(
//                    context.applicationContext,
//                    ChampionDB::class.java,
//                    "champion_database"
//                )
//                    .allowMainThreadQueries()
//                    .build()
//            }
//            return DB_INSTANCE
//        }
        fun getDatabase(context: Context): ChampionDB {
            return DB_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ChampionDB::class.java,
                    "app_database.db"
                )
                    .allowMainThreadQueries()
                    .build()
                DB_INSTANCE = instance

                instance
            }
        }
    }
}

//@Database(entities = [DatabaseItem::class], version = 1)
//abstract class AppDatabase: RoomDatabase() {
//    abstract fun lstDao(): ListDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context,
//                    AppDatabase::class.java,
//                    "app_database.db"
//                )
//                    .allowMainThreadQueries()
//                    .build()
//                INSTANCE = instance
//
//                instance
//            }
//        }
//    }
//}