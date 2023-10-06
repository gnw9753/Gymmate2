package com.example.gymmate.questionpage.questions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.gymmate.questionpage.QuestionPageViewModel
import com.example.gymmate.ui.theme.Typography

// Define a Composable function for the Day Page
@Composable
fun DayPage(viewModel: QuestionPageViewModel, modifier: Modifier = Modifier) {
    // Declare a mutable state variable to track if there is an error
    var isError by rememberSaveable { mutableStateOf(false) }

    // Function to validate user input
    fun validate() {
        isError = viewModel.amountSelected <= 1
    }

    // Create a column layout
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 5.dp)
    ) {
        // Create a row for displaying instructions and a reset button
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Display a text message based on whether there is an error
            Text(
                text = if (isError) "Select days available\nAt minimum 2 days" else "Select days available",
                style = Typography.labelLarge,
                textAlign = TextAlign.Left
            )
            // Create a TextButton with an icon to reset selections
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    viewModel.amountSelected = 0
                    viewModel.monday = false
                    viewModel.tuesday = false
                    viewModel.wednesday = false
                    viewModel.thursday = false
                    viewModel.friday = false
                    viewModel.saturday = false
                    viewModel.sunday = false
                },
            ) {
                Icon(
                    imageVector = Icons.Default.RestartAlt,
                    contentDescription = "Reset"
                )
            }
        }
        // Create buttons for each day of the week using ButtonCreate function
        ButtonCreate("Monday", viewModel)
        ButtonCreate("Tuesday", viewModel)
        ButtonCreate("Wednesday", viewModel)
        ButtonCreate("Thursday", viewModel)
        ButtonCreate("Friday", viewModel)
        ButtonCreate("Saturday", viewModel)
        ButtonCreate("Sunday", viewModel)

        // Create a TextButton for proceeding to the next page
        TextButton(
            onClick = {
                validate()
                if (!isError) viewModel.increasePageIndex()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Next")
        }
    }
}

// Composable function to create a button for each day
@Composable
fun ButtonCreate(day: String, viewModel: QuestionPageViewModel, modifier: Modifier = Modifier) {
    // Create a Button with an onClick listener
    Button(
        onClick = {
            // Toggle the selected state of the day and update the count of selected days
            when (day) {
                "Monday" -> viewModel.monday = !viewModel.monday
                "Tuesday" -> viewModel.tuesday = !viewModel.tuesday
                "Wednesday" -> viewModel.wednesday = !viewModel.wednesday
                "Thursday" -> viewModel.thursday = !viewModel.thursday
                "Friday" -> viewModel.friday = !viewModel.friday
                "Saturday" -> viewModel.saturday = !viewModel.saturday
                "Sunday" -> viewModel.sunday = !viewModel.sunday
            }
            viewModel.amountSelected++
        },
        // Enable or disable the button based on the selected state
        enabled = when (day) {
            "Monday" -> !viewModel.monday
            "Tuesday" -> !viewModel.tuesday
            "Wednesday" -> !viewModel.wednesday
            "Thursday" -> !viewModel.thursday
            "Friday" -> !viewModel.friday
            "Saturday" -> !viewModel.saturday
            "Sunday" -> !viewModel.sunday
            else -> false
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp)
    ) {
        // Display the day text on the button
        Text(day)
    }
}
