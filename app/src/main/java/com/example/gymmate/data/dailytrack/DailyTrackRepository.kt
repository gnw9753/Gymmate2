package com.example.gymmate.data.dailytrack

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gymmate.summarypage.Recipe
import java.time.ZoneId
import java.util.Date

interface DailyTrackRepository {
    suspend fun insertDailyTrack(weight: Float, recipe: Recipe)
    suspend fun getDailyAllTracks(): List<DailyTrack>
    suspend fun getTodayTrack(): DailyTrack?
    suspend fun update(dailyTrack: DailyTrack)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayTimestamp(): Long {
        // 去除时分秒
        val localDate = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        // 转换为时间戳
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}