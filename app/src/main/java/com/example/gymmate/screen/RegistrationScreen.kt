package com.example.gymmatekotlin.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymmate.GymmateRoute
import com.example.gymmatekotlin.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(userViewModel: UserViewModel, navController: NavController) {
    var email by remember { mutableStateOf(userViewModel.email) }
    var name by remember { mutableStateOf(userViewModel.name) }
    var gender by remember { mutableStateOf(userViewModel.gender) }
    var age by remember { mutableIntStateOf(userViewModel.age) }
    var height by remember { mutableIntStateOf(userViewModel.height) }
    var weight by remember { mutableIntStateOf(userViewModel.weight) }
    var goal by remember { mutableStateOf(userViewModel.goal) }
    var days by remember { mutableStateOf(List(DaysOfWeek.values().size) { false }) }
    var selectedDays by remember { mutableStateOf(emptyList<DaysOfWeek>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = rememberScrollState()),
    ) {
        Text(
            text = "Create Your GymMate Account",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next

            )
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "What is your gender?",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = gender,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    gender = "Male" // Update gender when Male button is clicked

                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Male", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    gender = "Female" // Update gender when Male button is clicked

                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Female", fontSize = 24.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = age.toString(),
            onValueChange = {
                // Update age as an Int when the user enters a valid number
                age = it.toIntOrNull() ?: 0
            },
            label = { Text(text = "Age") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = height.toString(),
            onValueChange = {
                // Update age as an Int when the user enters a valid number
                height = it.toIntOrNull() ?: 0
            },
            label = { Text(text = "Height") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = weight.toString(),
            onValueChange = {
                weight = it.toIntOrNull() ?: 0
            },
            label = { Text(text = "Weight") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "What is your goal?",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = goal,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    goal = "Gaining muscle"

                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Gaining muscle", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    goal = "Losing weight" // Update gender when Male button is clicked

                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Losing weight", fontSize = 24.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Select Workout Days",
                modifier = Modifier.padding(16.dp)
            )

            DaysOfWeek.values().forEachIndexed { index, day ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    val isChecked = selectedDays.contains(day)

                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { newCheckedState ->
                            selectedDays = if (newCheckedState) {
                                selectedDays + day // Add the day to the selected list
                            } else {
                                selectedDays - day // Remove the day from the selected list
                            }
                        },
                        colors = CheckboxDefaults.colors(checkmarkColor = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = day.name)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Handle registration here. You can save the data to your ViewModel or perform
                    // the registration API call here.
                    userViewModel.email = email
                    userViewModel.name = name
                    userViewModel.gender = gender
                    userViewModel.age = age
                    userViewModel.height = height
                    userViewModel.weight = weight
                    userViewModel.goal = goal
                    userViewModel.day = selectedDays
                    navController.navigate(GymmateRoute.CONFIRMATION)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}
enum class DaysOfWeek {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
}