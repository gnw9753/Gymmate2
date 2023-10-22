package com.example.gymmate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gymmate.homepage.HomepageViewModel
import com.example.gymmate.questionpage.QuestionPageViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.example.gymmate.caloriespage.CaloriesPageViewModel
import com.example.gymmate.login.InitializeUserPageViewModel
import com.example.gymmate.login.LoginPageViewModel
import com.example.gymmate.summarypage.SummaryPage
import com.example.gymmate.summarypage.SummaryPageViewModel


object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        initializer {
            LoginPageViewModel(gymmateApplication().container.userEntityRepository)
        }
        initializer {
            QuestionPageViewModel(
                gymmateApplication().container.exerciseRepository,
                gymmateApplication().container.userEntityRepository,
                gymmateApplication().container.loginEntityRepository
            )
        }
        initializer {
            InitializeUserPageViewModel(
                gymmateApplication().container.exerciseRepository,
                gymmateApplication().container.userEntityRepository
            )
        }
        initializer {
            HomepageViewModel(
                gymmateApplication().container.exerciseRepository,
                gymmateApplication().container.loginEntityRepository
            )
        }
        initializer {
            CaloriesPageViewModel(
                gymmateApplication().container.foodConsumptionRepository,
                gymmateApplication().container.weightRepository
            )
        }
        initializer {
            SummaryPageViewModel(
                gymmateApplication().container.exerciseRepository,
                gymmateApplication().container.userEntityRepository,
                gymmateApplication().container.dailyTrackRepository
            )
        }
    }
}

fun CreationExtras.gymmateApplication(): GymmateApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GymmateApplication)
