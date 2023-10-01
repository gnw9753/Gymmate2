package com.example.gymmate

import androidx.lifecycle.ViewModel
import com.example.gymmatekotlin.screen.DaysOfWeek
import com.example.gymmatekotlin.data.User

class UserViewModel : ViewModel() {

    var email: String = ""
    var name: String = ""
    var gender: String = ""
    var age: Int = 0
    var height: Int = 0
    var weight: Int = 0
    var goal: String = ""
    var day: List<DaysOfWeek> = emptyList()

    var user: User = User(
        email,
        name,
        gender,
        age,
        height,
        weight,
        goal,
        day
    )
    fun registerUser(user: User) {
        // Implement user registration logic here
    }

    fun signInUser(email: String, password: String) {
        // Implement user sign-in logic here
    }
}