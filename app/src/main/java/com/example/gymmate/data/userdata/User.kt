package com.example.gymmate.data.userdata

import com.example.gymmate.data.exercisedata.ExerciseDay

// This is the data class that will be used to initialize all the data

data class User (
    var id: Int,
    var email: String,
    var name: String,
    var gender: String,
    var age: Int,
    var height: Float,
    var weight: Float,
    var goal: String,
    var days: List<Boolean>,
    var exercise_schedule: List<ExerciseDay>,
    var isInitialized: Boolean
    ){
}