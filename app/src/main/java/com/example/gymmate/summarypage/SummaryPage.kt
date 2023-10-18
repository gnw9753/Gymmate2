package com.example.gymmate.summarypage


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.GymmateNavigationBar
import com.example.gymmate.GymmateRoute
import com.example.gymmate.NavigationActions
import com.example.gymmate.R
import com.example.gymmate.data.userdata.UserInstance
import com.example.gymmate.ui.theme.appThemeName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun SummaryPage(
    navigationActions: NavigationActions,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {

    var exerciseDayList = UserInstance.currentUser?.exercise_schedule
    Log.d("Summary", exerciseDayList?.size.toString());
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top horizontal slider // Carousel
        // Logic to decide to display homepage or exercise video page
        Column(
            modifier = modifier
                .weight(1f)
        ) {

            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    CalendarApp()
                }

            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Button(onClick = {
                    viewModel.openThemeSheet = !viewModel.openThemeSheet
                }) {
                    Text(text = "Change Theme")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    viewModel.openStaticsSheet = !viewModel.openStaticsSheet
                }) {
                    Text(text = "Weight Statistics")
                }
            }
            BottomSheetTheme(viewModel)
            BottomSheetStatics(viewModel)

        }
        GymmateNavigationBar(
            selectedDestination = GymmateRoute.SUMMARY,
            navigateToTopLevelDestination = navigationActions::navigateTo
        )
    }
}

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
                Color.LightGray
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
            if(date.hasExercise) {
                Text(
                    text = "exercise",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}


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
        val windowInsets = if (viewModel.edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        var themeList = listOf<String>("red", "yellow", "blue")
        //var appTheme by remember { mutableStateOf(AppTheme.System) }
        var selectedItem by remember { mutableStateOf("") }


        ModalBottomSheet(
            onDismissRequest = { viewModel.openThemeSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Text(text = "Click box to change theme")
            }
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp),
                verticalAlignment=
                Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {

                items(items = themeList) { item ->

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp)
                            .background(
                                when (item) {
                                    "red" -> Color.Red
                                    "yellow" -> Color.Yellow
                                    "blue" -> Color.Blue
                                    else -> Color.Blue
                                }
                            )
                            .selectable(
                                selected = selectedItem == item,
                                onClick = {
                                    if (selectedItem != item) {
                                        selectedItem = item
                                        appThemeName.value = item
                                    }
                                })
                        ,
                        contentAlignment =  Alignment.Center
                    ) {
                        Text(text = item, color = Color.White, fontSize = 20.sp)
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetStatics(
    viewModel: SummaryPageViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    if (viewModel.openStaticsSheet) {
        val windowInsets = if (viewModel.edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets


        ModalBottomSheet(
            onDismissRequest = { viewModel.openStaticsSheet = false },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {

            Column(Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "weight statics")
                //var list= listOf(100f,120f,130f)
                BarChart(
                    modifier = Modifier.padding(20.dp),
                    values = barChartInputsPercent
                )
            }

        }
    }
}


