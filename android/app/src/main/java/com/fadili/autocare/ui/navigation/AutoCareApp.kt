
package com.fadili.autocare.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.fadili.autocare.ui.dashboard.DashboardScreen
import com.fadili.autocare.ui.records.RecordsScreen
import com.fadili.autocare.ui.driving.DrivingScreen
import com.fadili.autocare.ui.parking.ParkingScreen
import com.fadili.autocare.ui.settings.SettingsScreen

private enum class MainTab(val title: String, val symbol: String) {
    DASHBOARD("Dashboard", "⌂"),
    RECORDS("Records", "▤"),
    DRIVING("Driving", "◉"),
    PARKING("Find Car", "⌖"),
    SETTINGS("Settings", "⚙")
}

@Composable
fun AutoCareApp() {
    var selectedTab by rememberSaveable {
        mutableStateOf(MainTab.DASHBOARD)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                MainTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Text(tab.symbol) },
                        label = { Text(tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                MainTab.DASHBOARD -> DashboardScreen()
                MainTab.RECORDS -> RecordsScreen()
                MainTab.DRIVING -> DrivingScreen()
                MainTab.PARKING -> ParkingScreen()
                MainTab.SETTINGS -> SettingsScreen()
            }
        }
    }
}
