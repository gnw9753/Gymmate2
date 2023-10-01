@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gymmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavHostController
import com.example.gymmate.caloriespage.CaloriesPreview
import com.example.gymmate.data.GenerateWorkout
import com.example.gymmate.data.ReadExerciseCSV
import com.example.gymmate.data.exercisedata.ExerciseDay
import com.example.gymmate.data.userdata.User
import com.example.gymmate.ui.theme.Theme
import com.example.gymmatekotlin.screen.SignInScreen


class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme(darkTheme = false) {
                SignInScreen(navController = navController)
            }
        }


    }
}