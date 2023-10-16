package com.example.gymmate.summarypage

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymmate.AppViewModelProvider
import com.example.gymmate.NavigationActions

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChangeUserInfoScreen(
    navigationActions: NavigationActions,
    viewModel: SummaryPageViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "User Info",
            fontSize = 36.sp,
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Name: ", Modifier.width(60.dp), textAlign = TextAlign.End)
            TextField(
                value = viewModel.name,
                onValueChange = {
                    viewModel.name = it
                    Toast.makeText(context, viewModel.name, Toast.LENGTH_SHORT).show()
                })
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Email: ", Modifier.width(60.dp), textAlign = TextAlign.End)
            TextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it })
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Weight: ", Modifier.width(60.dp), textAlign = TextAlign.End)
            TextField(
                value = viewModel.weight.toString(),
                onValueChange = {
                    viewModel.weight = try {
                        it.toFloat()
                    } catch (e: Exception) {
                        viewModel.weight
                    }
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Height: ", Modifier.width(60.dp), textAlign = TextAlign.End)
            TextField(
                value = viewModel.height.toString(),
                onValueChange = {
                    viewModel.height = try {
                        it.toFloat()
                    } catch (e: Exception) {
                        viewModel.height
                    }
                }
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {
            if (viewModel.changeUserInfo()) {
                Toast.makeText(
                    context,
                    "User info updated",
                    Toast.LENGTH_SHORT
                ).show()
                navigationActions.navController.popBackStack()
            }
        }) {
            Text("Confirm")
        }
    }
}