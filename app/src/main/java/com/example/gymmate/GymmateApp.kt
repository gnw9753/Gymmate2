package com.example.gymmate

import AlarmScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymmate.caloriespage.CaloriesPage
import com.example.gymmate.caloriespage.SearchFoodPage
import com.example.gymmate.homepage.Homepage
import com.example.gymmate.login.InitializeUserPage
import com.example.gymmate.login.LoginPage
import com.example.gymmate.questionpage.questions.EmailPage
import com.example.gymmate.questionpage.questions.NamePage
import com.example.gymmate.questionpage.QuestionPage
import com.example.gymmate.summarypage.ChangeUserInfoScreen
import com.example.gymmate.summarypage.SummaryPage
import com.google.android.material.bottomappbar.BottomAppBar


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GymmateApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        /*
        GymmateNavigationBar(
            selectedDestination = GymmateRoute.HOME,
            navigateToTopLevelDestination = navigationActions::navigateTo
        )*/

        GymmateNavHost(navController = navController, modifier = Modifier.weight(1f))
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
private fun GymmateNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    NavHost(
        navController = navController,
        startDestination = GymmateRoute.LOGIN,
        modifier = modifier,
    ) {

        composable(route = GymmateRoute.LOGIN) {
            LoginPage(
                navigateToQuestion = { navController.navigate(GymmateRoute.QUESTION) },
                navigateToInitializeUser = { navController.navigate(GymmateRoute.INITIALIZEUSER) })
        }
        composable(route = GymmateRoute.QUESTION) {
            QuestionPage(
                navigateToHomePage = { navController.navigate(GymmateRoute.INITIALIZEUSER) },
                navigateBack = { navController.navigate(GymmateRoute.LOGIN) }
            )
        }
        composable(route = GymmateRoute.INITIALIZEUSER) {
            InitializeUserPage(navigateToHomePage = { navController.navigate(GymmateRoute.HOME) })
        }
        composable(route = GymmateRoute.HOME) {
            Homepage(navigationActions)
        }
        composable(route = GymmateRoute.CALORIES) {
            CaloriesPage(navigationActions)
        }
        composable(route = GymmateRoute.SUMMARY) {
            SummaryPage(navigationActions = navigationActions)
        }
        composable(route = GymmateRoute.ALARM_PAGE) {
            AlarmScreen(navigationActions)
        }
        composable(route = GymmateRoute.CHANGE_USER_INFO) {
            ChangeUserInfoScreen(navigationActions)
        }
    }
}

