package com.example.gymmate.caloriespage

import org.junit.Assert.*

import org.junit.Test

class CaloriesPageKtTest {
    @Test
    fun CaloriesTest() {
        var gender = ""
        var age = 0
        var goal = ""
        var expectedValue = 0
        val calorieValue = calculateCalorieValue(gender, age, goal)
        assertEquals(expectedValue, calorieValue)
    }

    @Test
    fun testCalculateCalorieValueForFemaleOver50GainMuscle() {
        val calorieValue = calculateCalorieValue("Female", 55, "Gain Muscle")
        assertEquals(2090, calorieValue)
    }

    @Test
    fun testCalculateCalorieValueForMaleUnder14GainMuscle() {
        val calorieValue = calculateCalorieValue("Male", 12, "Gain Muscle")
        assertEquals(2750, calorieValue)
    }

    @Test
    fun testCalculateCalorieValueForMale30() {
        val calorieValue = calculateCalorieValue("Male", 30, "Gain Muscle")
        assertEquals(3190, calorieValue)
    }

//    @Test
//    fun testCalculateCalorieValueWithInvalidGender() {
//        // Test with an invalid gender (not "Male" or "Female")
//        val calorieValue = calculateCalorieValue("Other", 30, "Gain Muscle")
//        assertEquals(-1, calorieValue) // // Intentional failure
//    }
//
//    @Test
//    fun testCalculateCalorieValueWithNegativeAge() {
//        // Test with a negative age
//        val calorieValue = calculateCalorieValue("Male", -5, "Lose Weight")
//        assertEquals(0, calorieValue) // // Intentional failure
//    }
//
//    @Test
//    fun testCalculateCalorieValueWithInvalidGoal() {
//        // Test with an invalid goal (not "Gain Muscle" or "Lose Weight")
//        val calorieValue = calculateCalorieValue("Female", 40, "Maintain Weight")
//        assertEquals(-1, calorieValue) // // Intentional failure
//    }

}