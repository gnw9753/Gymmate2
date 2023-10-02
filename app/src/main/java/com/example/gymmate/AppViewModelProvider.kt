package com.example.gymmate

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gymmate.homepage.HomepageViewModel
import com.example.gymmate.questionpage.QuestionPageViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.example.gymmate.caloriespage.CaloriesPageViewModel
<<<<<<< Updated upstream
=======
import com.example.gymmate.login.InitializeUserPageViewModel
import com.example.gymmate.login.LoginPageViewModel
>>>>>>> Stashed changes


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
<<<<<<< Updated upstream
            HomepageViewModel(gymmateApplication().container.exerciseRepository)
        }
        initializer {
            QuestionPageViewModel(gymmateApplication().container.exerciseRepository)
=======
            LoginPageViewModel(gymmateApplication().container.userEntityRepository)
        }
        initializer {
            QuestionPageViewModel(gymmateApplication().container.exerciseRepository, gymmateApplication().container.userEntityRepository)
        }
        initializer {
            InitializeUserPageViewModel(gymmateApplication().container.exerciseRepository, gymmateApplication().container.userEntityRepository)
        }
        initializer {
            HomepageViewModel(gymmateApplication().container.exerciseRepository)
>>>>>>> Stashed changes
        }
        initializer {
            CaloriesPageViewModel(gymmateApplication().container.exerciseRepository)
        }
    }
}

fun CreationExtras.gymmateApplication(): GymmateApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GymmateApplication)
