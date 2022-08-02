package io.github.rk22000.settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.rk22000.data.QuestViewModel
import io.github.rk22000.data.SampleData
import io.github.rk22000.design_systems.ui.ListItem
import io.github.rk22000.design_systems.ui.UnderConstructionScreen
import io.github.rk22000.navigation.SettingsScreenDestination


fun NavGraphBuilder.settingsGraph(
    navController: NavController,
    viewModel: QuestViewModel,
) {
    composable(SettingsScreenDestination) {
        SettingsScreen(viewModel)
    }
}

@Composable
fun SettingsScreen(
    viewModel: QuestViewModel
) {
    val settingsItems: List<@Composable () -> Unit> = listOf(
        {
            Button(onClick = { viewModel.saveQuests(SampleData.getSampleQuests()) }) {
                Text(text = "Refill DB")
            }
        },
    )
    LazyColumn {
        itemsIndexed(settingsItems) { index, settingsItem ->
            ListItem(
                firstItem = { index == 0 },
                settingsItem
            )
        }
    }
}

