package com.example.gymmate.homepage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.GymmateNavigationBar
import com.example.gymmate.GymmateRoute
import com.example.gymmate.NavigationActions
import com.example.gymmate.data.userdata.UserInstance

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Homepage(
    navigationActions: NavigationActions,
    viewModel: HomepageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    var exerciseDayList = UserInstance.currentUser?.exerciseSchedule
   Column(
       modifier = Modifier
           .fillMaxSize()
   ) {
       // Top horizontal slider // Carousel

       // Logic to decide to display homepage or exercise video page
       LazyColumn(modifier = modifier
           .weight(1f)
       ) {
           items(exerciseDayList ?: emptyList()) { exercise ->
               DateCardRow(
                   day = exercise.day,
                   exerciseDay = exercise,
               )
           }
       }
       GymmateNavigationBar(
           selectedDestination = GymmateRoute.HOME,
           navigateToTopLevelDestination = navigationActions::navigateTo
       )
   }
}
