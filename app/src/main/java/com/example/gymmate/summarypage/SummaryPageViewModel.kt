package com.example.gymmate.summarypage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymmate.data.exercisedata.Exercise
import com.example.gymmate.data.exercisedata.ExerciseRepository
import com.example.gymmate.homepage.HomePageUiState
import com.example.gymmate.homepage.HomepageViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class SummaryPageViewModel(exerciseRepository: ExerciseRepository): ViewModel() {

    val summaryPageUiState: StateFlow<SummaryPageUiState> =

        exerciseRepository.getAllExercise().map { SummaryPageUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(SummaryPageViewModel.TIMEOUT_MILLIS),
                initialValue = SummaryPageUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    var openThemeSheet by mutableStateOf(false)
    var openStaticsSheet by mutableStateOf(false)
    var edgeToEdgeEnabled by mutableStateOf(false)
}

data class SummaryPageUiState(val exerciseList: List<Exercise> = listOf()) {
}