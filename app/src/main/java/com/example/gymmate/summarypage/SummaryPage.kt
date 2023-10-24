package com.example.gymmate.summarypage

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.GymmateNavigationBar
import com.example.gymmate.GymmateRoute
import com.example.gymmate.NavigationActions
import com.example.gymmate.R
import com.example.gymmate.data.fooddata.FoodConsumptionEntity
import com.example.gymmate.data.userdata.UserInstance.currentUser
import com.example.gymmate.data.weightdata.WeightEntity
import com.example.gymmate.ui.theme.appThemeName
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SummaryPage(
    modifier: Modifier = Modifier,
    navigationActions: NavigationActions,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val foodEntityUiState by viewModel.foodEntityUiState.collectAsState()
    val weightEntityUiState by viewModel.weightEntityUiState.collectAsState()

    if (weightEntityUiState.weightList.isNotEmpty()) viewModel.createWeightList((weightEntityUiState.weightList))
    if (foodEntityUiState.foodConsumptionList.isNotEmpty()) viewModel.createFoodList((foodEntityUiState.foodConsumptionList))


    val context = LocalContext.current
    LaunchedEffect(key1 = currentUser?.id) {
        viewModel.viewModelScope.launch {
            viewModel.getTodayRecipe(context = context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
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
                weightEntityUiState.weightList,
                modifier = Modifier
                    .weight(1f),
                viewModel
            )
            Spacer(modifier = Modifier.weight(.1f))
            CaloriesGraphCard(
                foodEntityUiState.foodConsumptionList,
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
            BottomSheetTheme(viewModel)
            BottomSheetCalendar(viewModel)
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
    weightList: List<WeightEntity>,
    modifier: Modifier = Modifier,
    viewModel: SummaryPageViewModel
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
                /*
                for (dailyTrack in allDailyTracks) {
                    dataPoints.add(FloatEntry(xPos, dailyTrack.weight))
                    xPos += 1f
                }*/

                var count = 1f
                for (weight in viewModel.weightList) {
                    dataPoints.add(
                        FloatEntry(
                            count++,
                            weight.weight
                        )
                    )
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
    foodList: List<FoodConsumptionEntity>,
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
                /*
                for (dailyTrack in allDailyTracks) {
                    dataPoints.add(FloatEntry(xPos, dailyTrack.calories.toFloat()))
                    xPos += 1f
                }*/
                var count = 1f
                for (food in viewModel.foodList) {
                    dataPoints.add(
                        FloatEntry(
                            count++,
                            food.calories
                        )
                    )
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
                    .padding(end = 5.dp)
            )

            Button(
                modifier = Modifier.weight(1f)
                    .padding(start = 5.dp),
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
                modifier = Modifier.weight(1f)
                    .padding(end = 5.dp),
                onClick = {
                    navigationActions.navController.navigate(GymmateRoute.CHANGE_USER_INFO)
                }
            ) {
                Text("Change User Info")
            }

            Button(
                modifier = Modifier.weight(1f)
                    .padding(start = 5.dp),
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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.weight(1f)
                    .padding(end = 5.dp),
                onClick = {
                    viewModel.openCalendarSheet = !viewModel.openCalendarSheet
                }
            ) {
                Text("Show Calendar")
            }
            Button(
                modifier = Modifier.weight(1f)
                    .padding(start = 5.dp),
                onClick = {
                    viewModel.openThemeSheet = !viewModel.openThemeSheet
                }
            ) {
                Text("Change theme")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarApp(
    modifier: Modifier = Modifier,
) {
    val dataSource = CalendarDataSource()
    var data by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }
    Column(modifier = modifier.fillMaxWidth()) {
        Header(
            data = data,
            onPrevClickListener = { startDate ->
                val finalStartDate = startDate.minusDays(1)
                data = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = data.selectedDate.date
                )
            },
            onNextClickListener = { endDate ->
                val finalStartDate = endDate.plusDays(2)
                data = dataSource.getData(
                    startDate = finalStartDate,
                    lastSelectedDate = data.selectedDate.date
                )
            }
        )
        Content(data = data) { date ->
            data = data.copy(
                selectedDate = date,
                visibleDates = data.visibleDates.map {
                    it.copy(
                        isSelected = it.date.isEqual(date.date)
                    )
                }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(
    data: CalendarUiModel,
    onPrevClickListener: (LocalDate) -> Unit,
    onNextClickListener: (LocalDate) -> Unit,
) {
    Row {
        Text(
            text = if (data.selectedDate.isToday) {
                "Today"
            } else {
                data.selectedDate.date.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                )
            },
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {
            onPrevClickListener(data.startDate.date)
        }) {
            Icon(
                imageVector = Icons.Filled.ChevronLeft,
                contentDescription = "Back"
            )
        }
        IconButton(onClick = {
            onNextClickListener(data.endDate.date)
        }) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Next"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(
    data: CalendarUiModel,
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 80.dp)) {
        items(data.visibleDates.size) { index ->
            ContentItem(
                date = data.visibleDates[index],
                onDateClickListener
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentItem(
    date: CalendarUiModel.Date,
    onClickListener: (CalendarUiModel.Date) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable {
                onClickListener(date)
            },
        colors = CardDefaults.cardColors(
            containerColor = if (date.hasExercise) {
                colorResource(id = R.color.purple_200)
            } else {
                androidx.compose.ui.graphics.Color.LightGray
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .padding(4.dp)
        ) {
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
            )
            if (date.hasExercise) {
                Text(
                    text = "exercise",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetTheme(
    viewModel: SummaryPageViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    if (viewModel.openThemeSheet) {
        val windowInsets = BottomSheetDefaults.windowInsets

        var themeList = listOf<String>("red", "yellow", "blue")
        //var appTheme by remember { mutableStateOf(AppTheme.System) }
        var selectedItem by remember { mutableStateOf("") }


        ModalBottomSheet(
            onDismissRequest = { viewModel.openThemeSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Click box to change theme")
            }
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp),
                verticalAlignment =
                Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                items(items = themeList) { item ->

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                            .background(
                                when (item) {
                                    "red" -> androidx.compose.ui.graphics.Color.Red
                                    "yellow" -> androidx.compose.ui.graphics.Color.Yellow
                                    "blue" -> androidx.compose.ui.graphics.Color.Blue
                                    else -> androidx.compose.ui.graphics.Color.Blue
                                }
                            )
                            .selectable(
                                selected = selectedItem == item,
                                onClick = {
                                    if (selectedItem != item) {
                                        selectedItem = item
                                        appThemeName.value = item
                                    }
                                }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item,
                            color = androidx.compose.ui.graphics.Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCalendar(
    viewModel: SummaryPageViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    if (viewModel.openCalendarSheet) {
        val windowInsets = BottomSheetDefaults.windowInsets


        ModalBottomSheet(
            onDismissRequest = { viewModel.openCalendarSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {

            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Calandar")
                CalendarApp()
            }

        }
    }
}

