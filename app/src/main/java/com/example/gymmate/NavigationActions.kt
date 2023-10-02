package com.example.gymmate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

enum class GymmateApp() {
    Homepage,
    QuestionPage,
    SummaryPage,
    AddFoodPage,
    ChangeWorkoutPage, ;
}

object GymmateRoute {
    const val HOME = "Home"
    const val QUESTION = "Question"
    const val SUMMARY = "Summary"
    const val CALORIES = "Calories"
    const val CHANGEWORKOUT = "ChangeWorkout"

<<<<<<< Updated upstream
    // Question Pages
    const val NAME = "Name"
    const val EMAIL = "Email"
    const val AGE = "Age"
    const val GENDER = "Gender"
    const val GOAL = "Goal"
    const val WEIGHT = "Weight"
    const val HEIGHT = "Height"
    const val DAY = "Day"
    const val CONFIRM = "Confirmation"
=======
    const val LOGIN = "Login"
    const val INITIALIZEUSER = "InitializeUser"
>>>>>>> Stashed changes

}

data class GymmateTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: GymmateTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    GymmateTopLevelDestination(
        route = GymmateRoute.HOME,
        selectedIcon = Icons.Default.FitnessCenter,
        unselectedIcon = Icons.Default.FitnessCenter,
        iconTextId = R.string.home
    ),
    GymmateTopLevelDestination(
        route = GymmateRoute.QUESTION,
        selectedIcon = Icons.Default.QuestionMark,
        unselectedIcon =Icons.Default.QuestionMark,
        iconTextId = R.string.question
    ),
    GymmateTopLevelDestination(
        route = GymmateRoute.SUMMARY,
        selectedIcon = Icons.Default.BarChart,
        unselectedIcon = Icons.Default.BarChart,
        iconTextId = R.string.summary
    ),
    GymmateTopLevelDestination(
        route = GymmateRoute.CALORIES,
        selectedIcon = Icons.Default.PieChart,
        unselectedIcon = Icons.Default.PieChart,
        iconTextId = R.string.update
    ),
    GymmateTopLevelDestination(
        route = GymmateRoute.CHANGEWORKOUT,
        selectedIcon = Icons.Default.Add,
        unselectedIcon = Icons.Default.Add,
        iconTextId = R.string.change_workout
    ),
)

val NAV_BAR_DESTINATIONS = listOf(
    TOP_LEVEL_DESTINATIONS[0],
    TOP_LEVEL_DESTINATIONS[3],
    TOP_LEVEL_DESTINATIONS[2]
)