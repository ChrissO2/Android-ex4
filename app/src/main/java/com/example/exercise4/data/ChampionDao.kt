package com.example.exercise4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChampionDao {
    @Query("SELECT * FROM champion_table")
    fun getAllData(): MutableList<Champion>?

    @Query("DELETE FROM champion_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: Champion) : Long

    @Delete
    fun delete(item: Champion) : Int

    @Query("SELECT * FROM champion_table WHERE id = :itemId")
    fun getItemById(itemId: Int): Champion?

    @Update
    fun update(champion: Champion) : Int
}