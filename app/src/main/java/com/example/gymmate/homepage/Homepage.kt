package com.example.gymmate.homepage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
<<<<<<< Updated upstream
=======
import com.example.gymmate.data.userdata.UserInstance
>>>>>>> Stashed changes

@Composable
fun Homepage(
    viewModel: HomepageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val homePageUiState by viewModel.homePageUiState.collectAsState()
    homePageUiState.exerciseList
    var exerciseDayList = viewModel.exerciseListToExerciseDay()

<<<<<<< Updated upstream
=======
    var userDayList = UserInstance.currentUser?.exercise_schedule
    if (userDayList != null) {
        for (day in userDayList) {
            println(day)
            for (exercise in day.exerciseList) {
                println(exercise.exerciseName)
            }
        }
    }

>>>>>>> Stashed changes
    // Top horizontal slider // Carousel

    // Logic to decide to display homepage or exercise video page
    LazyColumn(modifier = modifier) {
        items(exerciseDayList) { exercise ->
            DateCardRow(
                day = exercise.day,
                exerciseDay = exercise,
            )
        }
    }
}
