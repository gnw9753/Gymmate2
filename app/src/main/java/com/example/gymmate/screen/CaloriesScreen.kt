package com.example.gymmatekotlin.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun PieCardText(
    //user: User
) {
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        // Header
        displayText(displayText = "Calories Needed")
        displayText(displayText = "2200cal")
        displayText(displayText = "Macros")
        displayText(displayText = "Protein")
        displayText(displayText = "Carbs")
        displayText(displayText = "Fat")
    }
}

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
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()

            )
            val foodList = listOf("burger", "big buger", "cheese bugerg")
            displayText(displayText = foodListOutput(foodList))
        }

    }
}

fun foodListOutput(foodList: List<String>): String {
    var foodListString: String = ""

    var count = 0
    for (item in foodList) {
        if (count > 4) {
            break
        }
        item?.let {
            foodListString += "- $it\n"
            count++
        }
    }
    return foodListString
}

@Composable
fun displayText(displayText: String, modifier: Modifier = Modifier) {
    Text(
        text = displayText,
        modifier = modifier
    )
}

@Preview
@Composable
fun CaloriesPreview() {
    CaloriesScreen()
}
