package com.example.gymmate.fake_repository

import com.example.gymmate.data.dailytrack.DailyTrack
import com.example.gymmate.data.dailytrack.DailyTrackRepository
import com.example.gymmate.summarypage.Recipe

class FakeDailyTrackRepository: DailyTrackRepository {
    override suspend fun insertDailyTrack(weight: Float, recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyAllTracks(): List<DailyTrack> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayTrack(): DailyTrack? {
        TODO("Not yet implemented")
    }

    override suspend fun update(dailyTrack: DailyTrack) {
        TODO("Not yet implemented")
    }
}