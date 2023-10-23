package com.example.gymmate

import android.app.Application
import com.example.gymmate.data.AppContainer
import com.example.gymmate.data.AppDataContainer
import com.example.gymmate.data.userdata.User
import com.example.gymmate.data.userdata.UserInstance

class GymmateApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        UserInstance.currentUser = User(
            id = 0,
            email = "",
            name = "",
            gender = "",
            age = 0,
            height = 0f,
            weight = 0f,
            goal = "",
            days = emptyList(),
            exerciseSchedule = emptyList(),
            isInitialized = false
        )
    }
}