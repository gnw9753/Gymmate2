package com.example.gymmate.data.dailytrack

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyTrackDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertDailyTrack(dailyTrack: DailyTrack)
    @Delete
    suspend fun deleteDailyTrack(dailyTrack: DailyTrack)
    @Query("SELECT * FROM daily_track WHERE timestamp = :timestamp")
    fun getDailyTrackByTimestamp(timestamp: Long): Flow<List<DailyTrack>>

    @Query("SELECT * FROM daily_track")
    fun getAllDailyTracks(): Flow<List<DailyTrack>>

    @Query("SELECT * FROM daily_track WHERE id = :id")
    fun getDailyTrackById(id: Int): Flow<List<DailyTrack>>

    @Update
    suspend fun update(dailyTrack: DailyTrack)
}