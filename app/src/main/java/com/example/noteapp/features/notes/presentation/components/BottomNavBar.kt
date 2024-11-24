package com.example.noteapp.features.notes.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.noteapp.features.core.ui.theme.ubuntuFontFamily
import com.example.noteapp.navigation.navList

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    ) {
        val navBackStackEntry by
                navController.currentBackStackEntryAsState()

        val currentDestination = navBackStackEntry?.destination

        navList.forEach { tab ->
            val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(
                        route = tab.route
                    ) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = tab.label,
                        fontFamily = ubuntuFontFamily,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = if (selected) FontWeight.Bold else null
                    )
                }
            )
        }
    }
}