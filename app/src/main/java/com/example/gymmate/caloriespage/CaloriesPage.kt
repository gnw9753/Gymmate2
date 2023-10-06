package com.example.gymmate.caloriespage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.ui.theme.Typography
import com.example.gymmatekotlin.BasicPieChart
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriesPage(
    //viewModel: CaloriesPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var openWeightBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        PieCard()
        LatestCard()
        QuickAddCard()
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Button(onClick = {
                openBottomSheet = !openBottomSheet
            }) {
                Text(text = "Add Food")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                openWeightBottomSheet = !openWeightBottomSheet
            }) {
                Text(text = "Add Weight")
            }

        }
    }
    if (openBottomSheet) {
        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                    // you must additionally handle intended state cleanup, if any.
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                openBottomSheet = false
                            }
                        }
                    }
                ) {
                    Text("Hide Bottom Sheet")
                }
            }
            var text by remember { mutableStateOf("") }
            OutlinedTextField(value = text, onValueChange = { text = it })
            LazyColumn {
                items(50) {
                    ListItem(
                        headlineContent = { Text("Item $it") },
                        leadingContent = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    )
                }
            }
        }
    }
    if (openWeightBottomSheet) {
        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { openWeightBottomSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), horizontalArrangement = Arrangement.Center
            ) {
                var sliderPosition by remember { mutableIntStateOf(0) } // Change mutableIntStateOf to mutableStateOf
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                    modifier = Modifier.fillMaxWidth() // Fill the maximum width
                ) {
                    Text(
                        text = "Your weight is"
                    )
                    Slider(
                        value = sliderPosition.toFloat(), // Cast sliderPosition to Float for Slider
                        onValueChange = {
                            sliderPosition = it.toInt()
                        }, // Cast it to Int when updating sliderPosition
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.secondary,
                            activeTrackColor = MaterialTheme.colorScheme.secondary,
                            inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        valueRange = 0f..100f
                    )
                    Text(
                        text = "$sliderPosition kg",
                        textAlign = TextAlign.Center // Center the text horizontally
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center, // Center-align buttons horizontally
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    openWeightBottomSheet = false
                                }
                            }
                        }) {
                            Text(text = "Cancel")
                        }
                        Spacer(modifier = Modifier.width(16.dp)) // Add space between buttons
                        Button(onClick = {}) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PieCard(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
        ) {
            BasicPieChart()
            PieCardText()
        }

    }
}

///////////////////////
// Start of Pie Card //
///////////////////////

@Composable
fun PieCardText(
    //user: User
) {
    val gender = "Male"
    val age = 60
    val goal = "Gain Muscle"
    if (gender != null && age != null && goal != null) {

        var calorieValue = 0;
        if (gender == "Male") {
            if (age <= 14) {
                if (goal == "Gain Muscle") {
                    calorieValue = (2500 * 1.1).toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (2500 - 500)
                }
            } else if (age <= 18) {
                if (goal == "Gain Muscle") {
                    calorieValue = (3000 * 1.1).toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (3000 - 500)
                }
            } else if (age <= 24) {
                if (goal == "Gain Muscle") {
                    calorieValue = (2900 * 1.1).toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (2900 - 500)
                }
            } else if (age <= 50) {
                if (goal == "Gain Muscle") {
                    calorieValue = 2900 * 1.1.toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (2900 - 500)
                }
            } else if (age >= 51) {
                if (goal == "Gain Muscle") {
                    calorieValue = 3000 * 1.1.toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (3000 - 500)
                }
            }
        } else {
            if (age <= 14) {
                if (goal == "Gain Muscle") {
                    calorieValue = 2200 * 1.1.toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (2200 - 500)
                }
            } else if (age <= 18) {
                if (goal == "Gain Muscle") {
                    calorieValue = 2200 * 1.1.toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (2200 - 500)
                }
            } else if (age <= 24) {
                if (goal == "Gain Muscle") {
                    calorieValue = 2200 * 1.1.toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (2200 - 500)
                }
            } else if (age <= 50) {
                if (goal == "Gain Muscle") {
                    calorieValue = 2200 * 1.1.toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (2200 - 500)
                }
            } else if (age >= 51) {
                if (goal == "Gain Muscle") {
                    calorieValue = 1900 * 1.1.toInt()
                } else if (goal == "Lose Weight") {
                    calorieValue = (1900 - 500)
                }

            }
        }
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            // Header
            DisplayText(displayText = "Calories Needed", typography = Typography.headlineSmall)
            DisplayText(displayText = calorieValue.toString(), typography = Typography.bodyMedium)
            DisplayText(displayText = "Macros", typography = Typography.headlineSmall)
            DisplayText(
                displayText = "10~30% " + calculateValue(
                    calorieValue,
                    10,
                    4
                ).toString() + " ~ " + calculateValue(calorieValue, 30, 4).toString() + "g Protein",
                typography = Typography.bodyMedium
            )
            DisplayText(
                displayText = "45~65% " + calculateValue(
                    calorieValue,
                    45,
                    4
                ).toString() + " ~ " + calculateValue(calorieValue, 65, 4).toString() + "g Carbs",
                typography = Typography.bodyMedium
            )
            if (age >= 51) {
                DisplayText(
                    displayText = "20~35% " + calculateValue(
                        calorieValue,
                        20,
                        9
                    ).toString() + " ~ " + calculateValue(calorieValue, 35, 9).toString() + "g Fat",
                    typography = Typography.bodyMedium
                )
            } else {
                DisplayText(
                    displayText = "20~35% " + calculateValue(
                        calorieValue,
                        20,
                        9
                    ).toString() + " ~ " + calculateValue(calorieValue, 35, 9).toString() + "g Fat",
                    typography = Typography.bodyMedium
                )

            }
        }
    }
}
/////////////////////
// End of Pie card //
/////////////////////

//////////////////////
// Latest Food Card //
//////////////////////

@Composable
fun LatestCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Title
            Text(
                text = "Latest",
                style = Typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()

            )
            var foodList = listOf("burger", "big burger", "cheese burger")
            DisplayLatestFood(foodList = foodList)
        }
    }
}

@Composable
fun DisplayLatestFood(foodList: List<String>, modifier: Modifier = Modifier) {
    var count = 0
    for (food in foodList) {
        if (count > 4) {
            break
        }
        Row() {
            food?.let {
                DisplayText(
                    displayText = " • " + it,
                    typography = Typography.displaySmall,
                    modifier.padding(3.dp)
                )
                count++
            }
        }
    }
}

/////////////////////////////
// End of Latest food card //
/////////////////////////////

/////////////////////////////
// Start of Quick add Card //
/////////////////////////////
@Composable
fun QuickAddCard(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        // Title
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Quick Add",
                style = Typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            var foodList = listOf("burger", "big burger", "cheese burger")
            var count = 0
            for (food in foodList) {
                if (count > 2) break
                food?.let {
                    QuickAddRow(food = it)
                }
            }
        }
    }
}

@Composable
fun QuickAddRow(food: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = food,
            style = Typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(5.dp)
        ) {
            Text(text = "Add")
        }
    }
}

///////////////////////////
// End of Quick add card //
///////////////////////////


@Composable
fun DisplayText(displayText: String, typography: TextStyle, modifier: Modifier = Modifier) {
    Text(
        text = displayText,
        style = typography,
        modifier = modifier
    )
}

fun calculateValue(doubleValue: Int, rate: Int, divide: Int): Int {
    // Calculate the result
    return (rate * doubleValue / 100 / divide)
}