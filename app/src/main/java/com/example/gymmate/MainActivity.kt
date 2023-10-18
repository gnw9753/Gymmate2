@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gymmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gymmate.summarypage.CalendarApp
import com.example.gymmate.ui.theme.Theme
import com.example.gymmate.ui.theme.appThemeName


class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appThemeName.value="auto"
        setContent {
            Theme(darkTheme = false,
                dynamicColor = true,
                ) {
                //CalendarApp()
                GymmateApp()
            }
        }
    }
}

class ThemeViewModel : ViewModel() {
    private val _theme = MutableLiveData("default")
    val theme: LiveData<String> = _theme

    fun onThemeChanged(newTheme: String) {
        when (newTheme) {
            "Auto" -> _theme.value = "Light"
            "Light" -> _theme.value = "Dark"
            "Dark" -> _theme.value = "Auto"
        }
    }
}