package com.example.exercise4.livedata

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.exercise4.data.Champion
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Upsert
    suspend fun upsertChampion(champion: Champion)

    @Delete
    suspend fun deleteChampion(champion: Champion)

    @Query("DELETE FROM champion_table WHERE id = :id")
    suspend fun deleteChampionWithId(id: Int)

    @Query("SELECT * FROM champion_table")
    suspend fun getChampions(): MutableList<Champion>?

    @Query("UPDATE champion_table SET name = :name, description = :description, lane = :lane, rating = :rating WHERE id = :id")
    suspend fun updateChampion(id: Int, name: String, description: String, lane: Int, rating: Float)

    @Update
    suspend fun update(champion: Champion) : Int {
        Log.d("ListDao", "update: $champion")
        return 0
    }
}