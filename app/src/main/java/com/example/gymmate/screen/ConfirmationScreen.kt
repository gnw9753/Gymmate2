package com.example.gymmatekotlin.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymmatekotlin.UserViewModel

@Composable
fun ConfirmationScreen(userViewModel: UserViewModel, navController: NavController) {
    val userEmail = userViewModel.email
    val userName = userViewModel.name
    val userGender = userViewModel.gender
    val userAge = userViewModel.age
    val userHeight = userViewModel.height
    val userWeight = userViewModel.weight
    val userGoal = userViewModel.goal
    val userDays = userViewModel.day


    Column {
        Text(
            text = "Confirmation",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Entered Email:${userEmail}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Entered name: ${userName}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Entered gender: ${userGender}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Entered age: ${userAge}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Entered height: ${userHeight}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Entered weight: ${userWeight}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Entered goal: ${userGoal}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Entered day: ${userDays}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    navController.navigate("regi")

                },
                modifier = Modifier.width(200.dp)
            ) {
                Text(text = "Previous", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                },
                modifier = Modifier.width(200.dp)

            ) {
                Text(text = "Confirm", fontSize = 24.sp)
            }
        }
    }
}

