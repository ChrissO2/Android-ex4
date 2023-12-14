package com.example.exercise4.livedata

import android.content.Context
import android.util.Log
import com.example.exercise4.data.Champion
import com.example.exercise4.data.ChampionDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRepository (context: Context) {
    var dataList: MutableList<Champion>? = null
    lateinit var myDao: ListDao
    lateinit var myDatabase: ChampionDB

    init {
        myDatabase = ChampionDB.getDatabase(context)!!
        myDao = myDatabase.listDao()!!
    }

    companion object {
        private var INSTANCE: ListRepository? = null
        fun getInstance(context: Context): ListRepository {
            if (INSTANCE == null) {
                INSTANCE = ListRepository(context)
            }
            return INSTANCE as ListRepository
        }
    }

    suspend fun upsetChampion(item: Champion) = withContext(Dispatchers.IO) {
        myDao.upsertChampion(item)
    }

    suspend fun deleteChampion(item: Champion) = withContext(Dispatchers.IO) {
        myDao.deleteChampion(item)
    }

    suspend fun deleteChampionWithId(id: Int) = withContext(Dispatchers.IO) {
        myDao.deleteChampionWithId(id)
    }

    suspend fun getChampions(): MutableList<Champion>? = withContext(Dispatchers.IO) {
        dataList = myDao.getChampions()
        dataList
    }

    suspend fun getList() = withContext(Dispatchers.IO) {
        dataList = myDao.getChampions()
    }

    suspend fun updateChampion(id: Int, name: String, description: String, lane: Int, rating: Float) = withContext(Dispatchers.IO) {
        Log.d("ListRepository", "updateChampion: $id, $name, $description, $lane, $rating")
        myDao.updateChampion(id, name, description, lane, rating)
    }

    suspend fun update(champion: Champion) = withContext(Dispatchers.IO) {
        myDao.update(champion)
    }


}