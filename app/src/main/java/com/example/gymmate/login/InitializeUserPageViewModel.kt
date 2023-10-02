package com.example.gymmate.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymmate.data.exercisedata.Exercise
import com.example.gymmate.data.exercisedata.ExerciseDay
import com.example.gymmate.data.exercisedata.ExerciseRepository
import com.example.gymmate.data.userdata.User
import com.example.gymmate.data.userdata.UserEntity
import com.example.gymmate.data.userdata.UserEntityRepository
import com.example.gymmate.data.userdata.UserInstance
import com.example.gymmate.homepage.HomePageUiState
import com.example.gymmate.homepage.HomepageViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InitializeUserPageViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val userEntityRepository: UserEntityRepository
) : ViewModel() {

    /*
    private val userEntity: Flow<UserEntity>? = UserInstance.currentUser?.let {
        userEntityRepository.getUserByEmail(
            it.user_email
        )
    }*/

    private val userEntity: Flow<UserEntity>? = UserInstance.currentUser?.let {
        userEntityRepository.getUserByEmail(
            it.user_email)
    }


    val exerciseEntity: StateFlow<ExerciseEntity>? =
        UserInstance.currentUser?.let { it ->
            exerciseRepository.getAllExerciseById(it.user_id).map { ExerciseEntity(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = ExerciseEntity()
                )
        }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private fun exerciseListToExerciseDay(): List<ExerciseDay> {
        val exerciseList = exerciseEntity?.value?.exericseList
        var exerciseDayList: MutableList<ExerciseDay> = mutableListOf()
        val dayString =
            listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        // most inefficient algo???
        // Should delete the exercises when i add them to the temp list
        // So i dont have to check the whole list every time
        // But im low on time
        // Should learn how to use map as well
        for (day in dayString) {
            var tempExerciseList: MutableList<Exercise> = mutableListOf()
            if (exerciseList != null) {
                for (exercise in exerciseList) {
                    println(exercise.exerciseName)
                    if (exercise.day.equals(day, ignoreCase = true)) {
                        tempExerciseList.add(exercise)
                    }
                }
            }
            exerciseDayList.add(ExerciseDay(day, tempExerciseList, tempExerciseList.isNotEmpty()))
        }
        return exerciseDayList.toList()
    }

    private fun createDaysAvailableList(): List<Boolean> {
        val exerciseList = exerciseEntity?.value?.exericseList
        val dayString =
            listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        val availabilityList = mutableListOf<Boolean>()

        for (day in dayString) {
            val hasExerciseOnDay = exerciseList?.any { exercise ->
                exercise.day.equals(day, ignoreCase = true)
            }
            if (hasExerciseOnDay != null) {
                availabilityList.add(hasExerciseOnDay)
            }
        }
        return availabilityList.toList()
    }


    suspend fun initializeUserProfile() {
        viewModelScope.launch {
            val userEntity = userEntity?.firstOrNull()
            if (userEntity != null) {
                val tempExerciseList = exerciseListToExerciseDay()
                val tempDaysAvailableList = createDaysAvailableList()
                val user = User(
                    user_id = userEntity.id,
                    user_email = userEntity.email,
                    user_name = userEntity.name,
                    user_gender = userEntity.gender,
                    user_age = userEntity.age,
                    user_height = userEntity.height,
                    user_weight = userEntity.weight,
                    user_goal = userEntity.goal,
                    user_days = tempDaysAvailableList,
                    exercise_schedule = tempExerciseList,
                    isInitialized = true
                )
                UserInstance.currentUser = user
            }
        }
    }
}

data class ExerciseEntity(val exericseList: List<Exercise> = listOf()) {}