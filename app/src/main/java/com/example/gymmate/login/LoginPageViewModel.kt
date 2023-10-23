package com.example.gymmate.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymmate.data.userdata.User
import com.example.gymmate.data.userdata.UserEntityRepository
import com.example.gymmate.data.userdata.UserInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginPageViewModel(
    private val userEntityRepository: UserEntityRepository
) : ViewModel() {

    var isError by mutableStateOf(false) // Initialize isError here
    var emailEntered by mutableStateOf("")
    var emailFound by mutableStateOf(false)

    suspend fun checkEmailInDatabase() :Boolean{
        val enteredEmail = emailEntered.trim()
        val userEntity = userEntityRepository.getUserByEmail(enteredEmail)
        viewModelScope.launch {
            val user = userEntity.firstOrNull()
            emailFound = (user != null)
            println(emailFound)
            if (user != null) {
                UserInstance.currentUser = User(
                    id = user.id,
                    email = user.email,
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
            else{
                isError = true
            }
        }
        return emailFound
    }

}