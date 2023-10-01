package com.example.gymmate.caloriespage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymmate.data.exercisedata.Exercise
import com.example.gymmate.ui.theme.Typography
import com.example.gymmatekotlin.BasicPieChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaloriesScreen(
    //viewModel: CaloriesPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        PieCard()
        LatestCard()
        QuickAddCard()
        BottomSheetCart()
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
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        // Header
        DisplayText(displayText = "Calories Needed", typography = Typography.headlineSmall)
        DisplayText(displayText = "2200cal", typography = Typography.bodyMedium)
        DisplayText(displayText = "Macros", typography = Typography.headlineSmall)
        DisplayText(displayText = "Protein", typography = Typography.bodyMedium)
        DisplayText(displayText = "Carbs", typography = Typography.bodyMedium)
        DisplayText(displayText = "Fat", typography = Typography.bodyMedium)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCart(
) {
    var showBottomSheetScaffold by rememberSaveable {
        mutableStateOf(false)
    }
    BottomSheetScaffold(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    showBottomSheetScaffold = !showBottomSheetScaffold

                }) {
                    Text(text = "Close")
                }
            }
        },
        sheetContentColor = Color.Green,

        ) {
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
            for(food in foodList){
                if(count > 2) break
                food?.let {
                    QuickAddRow(food = it)
                }
            }
        }
    }
}

@Composable
fun QuickAddRow(food: String, modifier: Modifier = Modifier) {
    Row(modifier = Modifier
        .fillMaxWidth()
    ){
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

@Preview
@Composable
fun CaloriesPreview() {
    CaloriesScreen()
}
