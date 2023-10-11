package com.example.gymmate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun GymmateNavigationBar(
    selectedDestination: String?,
    navigateToTopLevelDestination: (GymmateTopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier
    ) {
        NAV_BAR_DESTINATIONS.forEach { gymmateDestination ->
            NavigationBarItem(
                selected = selectedDestination == gymmateDestination.route,
                onClick = { navigateToTopLevelDestination(gymmateDestination)},
                icon = {
                    Icon(
                        imageVector =  gymmateDestination.selectedIcon,
                        contentDescription = gymmateDestination.route,
                    )
                },
                label = { Text(text = gymmateDestination.route)}


            )

        }
    }
}

@Composable
fun ReplyBottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (GymmateTopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        NAV_BAR_DESTINATIONS.forEach { gymmateDestination ->
            NavigationBarItem(
                selected = selectedDestination == gymmateDestination.route,
                onClick = { navigateToTopLevelDestination(gymmateDestination) },
                icon = {
                    Icon(
                        imageVector = gymmateDestination.selectedIcon,
                        contentDescription = stringResource(id = gymmateDestination.iconTextId)
                    )
                }
            )
        }
    }
}