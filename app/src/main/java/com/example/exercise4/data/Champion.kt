package com.example.exercise4.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "champion_table")
class Champion : Serializable {
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

//    constructor(id: Int, name: String, description: String, lane: Int, rating: Float) {
//        this.id = id
//        this.name = name
//        this.description = description
//        this.lane = lane
//        this.rating = rating
//    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Champion

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (lane != other.lane) return false
        if (rating != other.rating) return false

        return true
    }
}
