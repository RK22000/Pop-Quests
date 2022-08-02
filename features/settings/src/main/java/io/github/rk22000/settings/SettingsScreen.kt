package io.github.rk22000.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.rk22000.data.QuestViewModel
import io.github.rk22000.design_systems.ui.UnderConstructionScreen
import io.github.rk22000.navigation.SettingsScreenDestination


fun NavGraphBuilder.settingsGraph(
    navController: NavController,
    viewModel: QuestViewModel,
) {
    composable(SettingsScreenDestination) {
        SettingsScreen()
    }
}

@Composable
fun SettingsScreen() {
    UnderConstructionScreen()
}