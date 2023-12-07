package com.example.exercise4.data

import android.content.Context
import com.example.exercise4.Constants.addChampion
import com.example.exercise4.Lane

class ChampionRepository(conext: Context) {
    private var dataList: MutableList<Champion>? = null
    private var db: ChampionDB? = null
    private var championDao: ChampionDao? = null

    companion object {
        private var INSTANCE: ChampionRepository? = null
        fun getInstance(context: Context): ChampionRepository? {
            if (INSTANCE == null) {
                synchronized(ChampionRepository::class) {
                    INSTANCE = ChampionRepository(context)
                }
            }
            return INSTANCE as ChampionRepository
            // v2 bez instrukcji
            // return INSTANCE
        }
    }

    fun getAllData(): MutableList<Champion>? {
        dataList = championDao?.getAllData()
        return dataList
    }

    fun addItem(item: Champion) : Boolean {
        championDao?.insert(item)
        return true
    }

    fun deleteItem(item: Champion) : Boolean {
        championDao?.delete(item)
        return true
    }

    fun updateItem(item: Champion) : Boolean {
        championDao?.update(item)
        return true
    }

    fun getItemById(itemId: Int): Champion? {
        return championDao?.getItemById(itemId)
    }

    init {
        db = ChampionDB.getDatabase(conext)!!
        championDao = db?.championDao()!!
        dataList = championDao?.getAllData()
//        addItem(Champion("Aatrox", "The Darkin Blade", Lane.TOP, 3.0f))
//        addItem(Champion("Ahri", "The Nine-Tailed Fox", Lane.MID, 3.0f))
//        addItem(Champion("Akali", "The Rogue Assassin", Lane.MID, 3.0f))
//        addItem(Champion("Alistar", "The Minotaur", Lane.SUPPORT, 3.0f))
//        addItem(Champion("Amumu", "The Sad Mummy", Lane.JUNGLE, 3.0f))
//        addItem(Champion("Anivia", "The Cryophoenix", Lane.MID, 3.0f))
//        addItem(Champion("Annie", "The Dark Child", Lane.MID, 3.0f))
    }

}