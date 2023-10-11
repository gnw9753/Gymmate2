package com.example.gymmate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gymmate.caloriespage.CaloriesPageViewModel
import com.example.gymmate.homepage.HomepageViewModel
import com.example.gymmate.login.InitializeUserPageViewModel
import com.example.gymmate.login.LoginPageViewModel
import com.example.gymmate.questionpage.QuestionPageViewModel
import com.example.gymmate.summarypage.SummaryPageViewModel


object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        initializer {
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
        }
        initializer {
            CaloriesPageViewModel(gymmateApplication().container.exerciseRepository)
        }
        initializer {
            SummaryPageViewModel(gymmateApplication().container.exerciseRepository, gymmateApplication().container.userEntityRepository)
        }
    }
}

fun CreationExtras.gymmateApplication(): GymmateApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GymmateApplication)
