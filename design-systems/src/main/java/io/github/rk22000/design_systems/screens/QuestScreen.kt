package io.github.rk22000.design_systems.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.window.Dialog
import io.github.rk22000.data.QuestDeck
import io.github.rk22000.data.QuestViewModel
import io.github.rk22000.data.SampleData
import io.github.rk22000.design_systems.theme.Paddings
import io.github.rk22000.design_systems.theme.fabShape
import io.github.rk22000.design_systems.ui.BasicCardDeck
import io.github.rk22000.design_systems.ui.BasicQuestCard
import io.github.rk22000.design_systems.ui.NewQuestCard
import kotlinx.coroutines.flow.last


@Composable
fun QuestScreen(
    viewModel: QuestViewModel,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        var creatingNewQuest by remember { mutableStateOf(false) }
        DeckScreen(
            viewModel = viewModel,
            onFabClicked = { creatingNewQuest = true }
        )
        if (creatingNewQuest) {
            Dialog(onDismissRequest = { creatingNewQuest = false }) {
                NewQuestScreen(
                    viewModel = viewModel,
                    modifier = Modifier.width(IntrinsicSize.Min).padding(vertical = Paddings.loose),
                    onExitScreen = { creatingNewQuest = false }
                )
            }
        }
    }
}

@Composable
fun DeckScreen(
    viewModel: QuestViewModel,
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit = { }
) {

    Box {
        var allQuestVisible by remember {
            mutableStateOf(false)
        }
        Scaffold(
            modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onFabClicked,
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
                    Button(onClick = { viewModel.saveQuests(SampleData.getSampleQuests()) }) {
                        Text(text = "Refil DB")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { allQuestVisible = true }) {
                        Text(text = "Show all quests")
                    }
                }
            }
        ) {
            val questDeck by viewModel.questFlow.collectAsState(initial = QuestDeck(emptyList()))
            BasicCardDeck(
                questDeck = questDeck,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = Paddings.default, vertical = Paddings.loose),
                confirmDeckChanged = {
                    viewModel.saveQuests(it)
                    true
                }
            )
        }
        if (allQuestVisible) {
            val questDeck by viewModel.questFlow.collectAsState(initial = viewModel.questDeck)
            Dialog(onDismissRequest = { allQuestVisible = false }) {
                LazyColumn {
                    items(questDeck.quests, key = { it.toString() }) {
                        BasicQuestCard(quest = it, modifier = Modifier.height(IntrinsicSize.Min))
                    }
                }
            }
        }
    }
}


@Composable
fun NewQuestScreen(
    viewModel: QuestViewModel,
    modifier: Modifier = Modifier,
    onExitScreen: () -> Unit,
) {
        NewQuestCard(
            draftQuest = viewModel.draftQuest,
            modifier = modifier,
            confirmDraftChanged = {
                viewModel.draftQuest = it
                true
            },
            confirmNewQuestCreated = {
                viewModel.saveNewQuest(it)
                onExitScreen()
                true
            },
            confirmDraftCreated = { onExitScreen(); true}
            )
}


