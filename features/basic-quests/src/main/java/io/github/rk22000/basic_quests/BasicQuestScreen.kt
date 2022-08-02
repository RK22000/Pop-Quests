package io.github.rk22000.basic_quests

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import io.github.rk22000.data.Mood
import io.github.rk22000.data.QuestDeck
import io.github.rk22000.data.QuestViewModel
import io.github.rk22000.data.SampleData
import io.github.rk22000.design_systems.theme.Paddings
import io.github.rk22000.design_systems.theme.fabShape
import io.github.rk22000.design_systems.ui.BasicCardDeck
import io.github.rk22000.design_systems.ui.BasicQuestCard
import io.github.rk22000.design_systems.ui.NewQuestCard
import io.github.rk22000.design_systems.ui.SettingsButton
import io.github.rk22000.navigation.BASIC_QUEST_SCREEN_DESTINATION
import io.github.rk22000.navigation.SettingsScreenDestination


fun NavGraphBuilder.basicQuestGraph(
    navController: NavController,
    viewModel: QuestViewModel
) {
    composable(BASIC_QUEST_SCREEN_DESTINATION) {
        BasicQuestScreen(navController = navController, viewModel = viewModel)
    }
}

@Composable
fun BasicQuestScreen(
    navController: NavController,
    viewModel: QuestViewModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        var creatingNewQuest by remember { mutableStateOf(false) }
        var allQuestVisible by remember { mutableStateOf(false) }
        var currentMood by remember { mutableStateOf(Mood.NORMAL) }
        fun newQuestDialog() {
            creatingNewQuest = true
            allQuestVisible = false
        }

        fun showAllQuests() {
            allQuestVisible = true
            creatingNewQuest = false
        }

        val questDeck by viewModel.questFlow.collectAsState(initial = QuestDeck(emptyList()))
        // The Quest Deck in the scaffold
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MoodBar(
                    currentMood = currentMood,
                    onMoodChanged = { currentMood = it }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { newQuestDialog() },
                    shape = fabShape
                ) {

                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(
                    cutoutShape = fabShape
                ) {
                    Button(onClick = { showAllQuests() }) {
                        Text(text = "Show all Quests")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    SettingsButton { navController.navigate(SettingsScreenDestination) }
                }
            }
        ) {
            BasicCardDeck(questDeck = questDeck.copy(questDeck.quests.filter(currentMood.check)),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = Paddings.default, vertical = Paddings.loose),
                confirmQuestDeleted = {
                    viewModel.saveQuests(questDeck.copy(questDeck.quests - it))
                    true
                }
            )
        }
        // The new Quest Dialog
        if (creatingNewQuest) {
            Dialog(onDismissRequest = { creatingNewQuest = false }) {
                NewQuestCard(draftQuest = viewModel.draftQuest,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = Paddings.loose),
                    confirmDraftCreated = {
                        viewModel.draftQuest = it
                        creatingNewQuest = false
                        true
                    },
                    confirmNewQuestCreated = {
                        viewModel.saveNewQuest(it)
                        creatingNewQuest = false
                        true
                    },
                    confirmDraftChanged = {
                        viewModel.draftQuest = it
                        true
                    }
                )
            }
        }
        // All quest display
        if (allQuestVisible) {
            Dialog(onDismissRequest = { allQuestVisible = false }) {
                LazyColumn {
                    itemsIndexed(
                        questDeck.quests.filter(currentMood.check),
                        key = { _, item -> item.toString() }) { index, item ->
                        BasicQuestCard(
                            quest = item, modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .padding(vertical = Paddings.tight)
                        )
                    }
                }
            }
        }
    }
}