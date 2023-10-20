package com.example.gymmate.summarypage

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.ui.theme.md_theme_light_background
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
    state: MaterialDialogState,
) {
    val context = LocalContext.current

    var recipe by remember {
        mutableStateOf("None")
    }
    var calories by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = "") {
        viewModel.viewModelScope.launch {
            val track = withContext(Dispatchers.IO) {
                viewModel.getTodayRecipe(context)
            }
            recipe = track.recipe
            calories = track.calories
        }
    }

    MaterialDialog(
        dialogState = state,
        buttons = {
            positiveButton("ok")
        },
        backgroundColor = md_theme_light_background,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Recommend recipes today",
                    textAlign = TextAlign.Center, fontSize = 20.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text("${recipe}\nTotal calories: $calories cal")
            }
        }
    }
}