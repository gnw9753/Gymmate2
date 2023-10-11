package com.example.gymmate.summarypage

import android.graphics.Color
import android.os.Build
import android.widget.Space
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.GymmateNavigationBar
import com.example.gymmate.GymmateRoute
import com.example.gymmate.NavigationActions
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SummaryPage(
    navigationActions: NavigationActions,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
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

@Composable
fun WeightGraphCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
    ) {
        val refreshDataset = remember { mutableIntStateOf(0) }
        val modelProducer = remember { ChartEntryModelProducer() }
        val datasetForModel = remember { mutableListOf(listOf<FloatEntry>()) }
        val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }

        LaunchedEffect(key1 = refreshDataset.intValue) {
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
            for (i in 0..50) {
                dataPoints.add(FloatEntry(xPos, (65..70).random().toFloat()))
                xPos += 1f
            }
            datasetForModel.add(dataPoints)
            modelProducer.setEntries(datasetForModel)
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
                            valueFormatter = { value, _ -> "${value.toInt()}kg" }
                        ),

                        bottomAxis = rememberBottomAxis(
                            title = "Date",
                            valueFormatter = { value, _ -> "${value.toInt()}" }
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun CaloriesGraphCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val refreshDataset = remember { mutableIntStateOf(0) }
        val modelProducer = remember { ChartEntryModelProducer() }
        val datasetForModel = remember { mutableListOf(listOf<FloatEntry>()) }
        val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }

        LaunchedEffect(key1 = refreshDataset.intValue) {
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
            for (i in 0..50) {
                dataPoints.add(FloatEntry(xPos, (1000..3000).random().toFloat()))
                xPos += 1f
            }
            datasetForModel.add(dataPoints)
            modelProducer.setEntries(datasetForModel)
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
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BottomButton(
    navigationActions: NavigationActions,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WorkoutChangeButton()

            Button(
                onClick = {
                    navigationActions.navController.navigate(GymmateRoute.ALARM_PAGE)
                }
            ) {
                Text("Set alarm")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    navigationActions.navController.navigate(GymmateRoute.CHANGE_USER_INFO)
                }
            ) {
                Text("Change User Info")
            }
            Button(
                onClick = {
                    viewModel.setDownload(context)
                }
            ) {
                Text("Download Workout")
            }
        }
    }
}
