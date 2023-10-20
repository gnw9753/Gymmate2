package com.example.gymmate.summarypage

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.GymmateNavigationBar
import com.example.gymmate.GymmateRoute
import com.example.gymmate.NavigationActions
import com.example.gymmate.data.userdata.UserInstance.currentUser
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SummaryPage(
    modifier: Modifier = Modifier,
    navigationActions: NavigationActions,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = currentUser?.id) {
        viewModel.viewModelScope.launch {
            viewModel.getTodayRecipe(context = context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(modifier = Modifier.weight(.1f))
            WeightGraphCard(
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.weight(.1f))
            CaloriesGraphCard(
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.weight(.1f))
            BottomButton(
                navigationActions = navigationActions,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.weight(.1f))
        }

        GymmateNavigationBar(
            selectedDestination = GymmateRoute.SUMMARY,
            navigateToTopLevelDestination = navigationActions::navigateTo
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeightGraphCard(
    modifier: Modifier = Modifier,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Card(
        modifier = modifier
            .fillMaxSize()
    ) {
        val refreshDataset = remember { mutableIntStateOf(0) }
        val modelProducer = remember { ChartEntryModelProducer() }
        val datasetForModel = remember { mutableListOf(listOf<FloatEntry>()) }
        val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }

        LaunchedEffect(key1 = refreshDataset.intValue) {
            viewModel.viewModelScope.launch {
                // TODO rebuild dataset
                datasetForModel.clear()
                datasetLineSpec.clear()
                var xPos = 0f
                val dataPoints = arrayListOf<FloatEntry>()
                datasetLineSpec.add(
                    LineChart.LineSpec(
                        lineColor = Color.BLUE,
                    )
                )

                val allDailyTracks = viewModel.getAllDailyTracks()
                for (dailyTrack in allDailyTracks) {
                    dataPoints.add(FloatEntry(xPos, dailyTrack.weight))
                    xPos += 1f
                }

                datasetForModel.add(dataPoints)
                modelProducer.setEntries(datasetForModel)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (datasetForModel.isNotEmpty()) {
                ProvideChartStyle {
                    Chart(
                        modifier = Modifier.fillMaxSize(),
                        chart = LineChart(
                            lines = datasetLineSpec
                        ),
                        chartModelProducer = modelProducer,

                        startAxis = rememberStartAxis(
                            title = "Weight",
                            valueFormatter = { value, _ -> "${value.toInt()}kg" },
                            itemPlacer = AxisItemPlacer.Vertical.default(
                                maxItemCount = 6
                            )
                        ),

                        bottomAxis = rememberBottomAxis(
                            title = "Date",
                            valueFormatter = { value, _ -> "${value.toInt()}" }
                        ),

                        marker = rememberMarker()
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CaloriesGraphCard(
    modifier: Modifier = Modifier,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val refreshDataset = remember { mutableIntStateOf(0) }
        val modelProducer = remember { ChartEntryModelProducer() }
        val datasetForModel = remember { mutableListOf(listOf<FloatEntry>()) }
        val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }

        LaunchedEffect(key1 = "") {
            viewModel.viewModelScope.launch {
                // TODO rebuild dataset
                datasetForModel.clear()
                datasetLineSpec.clear()
                var xPos = 0f
                val dataPoints = arrayListOf<FloatEntry>()
                datasetLineSpec.add(
                    LineChart.LineSpec(
                        lineColor = Color.BLUE,
                    )
                )

                val allDailyTracks = viewModel.getAllDailyTracks()
                for (dailyTrack in allDailyTracks) {
                    dataPoints.add(FloatEntry(xPos, dailyTrack.calories.toFloat()))
                    xPos += 1f
                }

                datasetForModel.add(dataPoints)
                modelProducer.setEntries(datasetForModel)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (datasetForModel.isNotEmpty()) {
                ProvideChartStyle {
                    Chart(
                        modifier = Modifier.fillMaxSize(),
                        chart = LineChart(
                            lines = datasetLineSpec
                        ),
                        chartModelProducer = modelProducer,

                        startAxis = rememberStartAxis(
                            title = "Calories",
                            valueFormatter = { value, _ -> "${value.toInt()}cal" }
                        ),

                        bottomAxis = rememberBottomAxis(
                            title = "Date",
                            valueFormatter = { value, _ -> "${value.toInt()}" }
                        ),

                        marker = rememberMarker()
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    navigationActions: NavigationActions,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val context = LocalContext.current

    val state = rememberMaterialDialogState()
    RecipeScreen(state = state)

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WorkoutChangeButton(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    navigationActions.navController.navigate(GymmateRoute.ALARM_PAGE)
                }
            ) {
                Text("Set alarm")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    navigationActions.navController.navigate(GymmateRoute.CHANGE_USER_INFO)
                }
            ) {
                Text("Change User Info")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setDownload(context)
                }
            ) {
                Text("Download Workout")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    state.show()
                }
            ) {
                Text("Show Today Recipe")
            }
        }
    }
}


