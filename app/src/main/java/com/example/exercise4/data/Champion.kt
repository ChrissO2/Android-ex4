package com.example.exercise4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "champion_table")
class Champion {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var description: String = ""
    var lane: Int = 0
    var rating: Float = 0.0f

    constructor(name: String, description: String, lane: Int, rating: Float) {
        this.name = name
        this.description = description
        this.lane = lane
        this.rating = rating
    }
}