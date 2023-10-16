package com.example.gymmate.data.dailytrack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_track")
data class DailyTrack (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var timestamp: Long = 0L,
    var weight: Float = 0f,
    var calories: Int = 0,
    val recipe: String = "",
)