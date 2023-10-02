package com.example.gymmate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
<<<<<<< Updated upstream
=======
import com.example.gymmate.caloriespage.CaloriesPage
import com.example.gymmate.homepage.Homepage
import com.example.gymmate.login.InitializeUserPage
import com.example.gymmate.login.LoginPage
>>>>>>> Stashed changes
import com.example.gymmate.questionpage.questions.EmailPage
import com.example.gymmate.questionpage.questions.NamePage
import com.example.gymmate.questionpage.QuestionPage
import com.google.android.material.bottomappbar.BottomAppBar


@Composable
fun GymmateApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GymmateNavHost(navController = navController, modifier = Modifier.weight(1f))
        if (navController.currentDestination.toString() == GymmateRoute.HOME ||
            navController.currentDestination.toString() == GymmateRoute.CALORIES ||
            navController.currentDestination.toString() == GymmateRoute.SUMMARY
            ) {
            GymmateNavigationBar(
                selectedDestination = GymmateRoute.HOME,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }
    }
}

@Composable
private fun GymmateNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
<<<<<<< Updated upstream
        startDestination = GymmateRoute.QUESTION,
        modifier = modifier,
    ) {
        /*
        composable(route = GymmateRoute.HOME) {
            Homepage()
        }*/
        composable(route = GymmateRoute.QUESTION) {
            QuestionPage(navigateToHomePage = { navController.navigate(GymmateRoute.HOME) })
        }

        /*
        composable(route = GymmateRoute.CALORIES) {
            CaloriesPage()
        }*/
=======
        startDestination = GymmateRoute.LOGIN,
        modifier = modifier,
    ) {

        composable(route = GymmateRoute.LOGIN) {
            LoginPage(navigateToQuestion = {navController.navigate(GymmateRoute.QUESTION)}, navigateToInitializeUser =  {navController.navigate(GymmateRoute.INITIALIZEUSER)})
        }

        composable(route = GymmateRoute.QUESTION) {
            QuestionPage(navigateToHomePage = { navController.navigate(GymmateRoute.INITIALIZEUSER) })
        }

        composable(route = GymmateRoute.INITIALIZEUSER) {
            InitializeUserPage(navigateToHomePage = {navController.navigate(GymmateRoute.HOME)})
        }
        composable(route = GymmateRoute.HOME) {
            Homepage()
        }
        composable(route = GymmateRoute.CALORIES) {
            CaloriesPage()
        }
>>>>>>> Stashed changes
    }
}

