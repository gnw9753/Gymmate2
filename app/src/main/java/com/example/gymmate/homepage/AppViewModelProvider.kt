package com.example.gymmate.homepage

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gymmate.homepage.HomepageViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.example.gymmate.GymmateApplication


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomepageViewModel(gymmateApplication().container.exerciseRepository)
        }
    }
}

fun CreationExtras.gymmateApplication(): GymmateApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GymmateApplication)
