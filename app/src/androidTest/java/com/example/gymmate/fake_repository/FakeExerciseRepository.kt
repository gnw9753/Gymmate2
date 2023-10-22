package com.example.gymmate.fake_repository

import com.example.gymmate.data.exercisedata.Exercise
import com.example.gymmate.data.exercisedata.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class FakeExerciseRepository: ExerciseRepository {
    override fun getAllExerciseById(id: Int): Flow<List<Exercise>> {
        TODO("Not yet implemented")
    }

    override fun getAllExercise(): Flow<List<Exercise>> {
        TODO("Not yet implemented")
    }

    override fun deleteAllExercise() {
        TODO("Not yet implemented")
    }

    override fun deleteAllExerciseById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun deleteAllExerciseByIDAndDay(id: Int, day: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

}