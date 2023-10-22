package com.example.gymmate.data.dailytrack

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.gymmate.summarypage.Recipe
import kotlinx.coroutines.flow.firstOrNull
import java.time.ZoneId
import java.util.Date

class OfflineDailyTrackRepository(private val dailyTrackDao: DailyTrackDao) : DailyTrackRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertDailyTrack(weight: Float, recipe: Recipe) {
        val timestamp = getTodayTimestamp()
        Log.i("DailyTrackRepository", "insertDailyTrack: $timestamp")
        val dailyTrack = DailyTrack(
            timestamp = timestamp,
            weight = weight,
            calories = recipe.calories,
            recipe = recipe.recipe
        )
        val dt = dailyTrackDao.getDailyTrackByTimestamp(dailyTrack.timestamp).firstOrNull()?.firstOrNull()
        if (dt != null) {
            dailyTrack.id = dt.id
        }
        dailyTrackDao.insertDailyTrack(dailyTrack)
    }

    override suspend fun getDailyAllTracks(): List<DailyTrack> {
        return dailyTrackDao.getAllDailyTracks().firstOrNull() ?: listOf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTodayTrack(): DailyTrack? {
        val timestamp = getTodayTimestamp()
        Log.i("DailyTrackRepository", "getTodayTrack: $timestamp")
        return dailyTrackDao.getDailyTrackByTimestamp(timestamp).firstOrNull()?.firstOrNull()
    }

    override suspend fun update(dailyTrack: DailyTrack) {
        return dailyTrackDao.update(dailyTrack)
    }
}
