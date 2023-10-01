package com.example.gymmatekotlin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymmate.GymmateRoute
import com.example.gymmate.R
import com.example.gymmatekotlin.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController) {
    val checked = remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Enter your email") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 100.dp)


    ) {
        Image(
            painter = painterResource(id = R.drawable.gymmate_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = "GymMate",
            fontSize = 32.sp
        )
        Text(
            text = "Your Personal Fitness Companion",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Sign in or create new account",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = text,
            onValueChange = { text = it },

            )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = checked.value,
                onCheckedChange = { isChecked -> checked.value = isChecked },
                modifier = Modifier.padding(end = 8.dp) // Adjust padding as needed
            )

            Text(
                text = "Remember me",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(1f)) // This will push the "Sign in" button to the right end of the row

            Button(onClick = {
                navController.navigate(GymmateRoute.CALORIES)
            }) {
                Text(text = "Sign in", fontSize = 24.sp)

            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "or", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.navigate(GymmateRoute.REGISTER)
        }) {
            Text(text = "Sign up", fontSize = 24.sp)

        }

    }
}
