package com.example.exercise4

data class ChampionModel(val name: String, val description: String, val lane: Int, val rating: Float): java.io.Serializable

class Lane {
    companion object {
        const val TOP = 0
        const val JUNGLE = 1
        const val MID = 2
        const val BOTTOM = 3
        const val SUPPORT = 4
    }
}