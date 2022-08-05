package io.github.rk22000.basic_quests

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.github.rk22000.data.*
import io.github.rk22000.design_systems.theme.Paddings
import io.github.rk22000.design_systems.theme.fabShape
import io.github.rk22000.design_systems.ui.BasicCardDeck
import io.github.rk22000.design_systems.ui.BasicQuestCard
import io.github.rk22000.design_systems.ui.NewQuestCard
import io.github.rk22000.design_systems.ui.SettingsButton
import io.github.rk22000.navigation.BASIC_QUEST_SCREEN_DESTINATION
import io.github.rk22000.navigation.SettingsScreenDestination
import java.time.LocalDate


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
        var filterTag: String? by remember { mutableStateOf(null) }
        var filterMenuExpanded by remember { mutableStateOf(false) }
        fun newQuestDialog() {
            creatingNewQuest = true
            allQuestVisible = false
        }

        fun showAllQuests() {
            allQuestVisible = true
            creatingNewQuest = false
        }

        val currentDate = LocalDate.now().toEpochDay()
        val currentQuestFilter: (Quest) -> Boolean = {
            with(it){
                startLine <= currentDate &&
                currentMood.check(this) &&
                        filterTag?.let { it in tags }?:true
            }
        }

        val fullQuestDeck by viewModel.questFlow.collectAsState(initial = QuestDeck(emptyList()))
        val filteredQuestDeck by remember(key1 = fullQuestDeck, key2 = currentMood, key3 = filterTag) {
            mutableStateOf(
                with(fullQuestDeck) {
                    copy(
                        quests = quests.filter(currentQuestFilter).sortedWith(currentMood.comparator)
                    )
                }
            )
        }
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
                        Text(text = "Show full deck")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box {
                        TextButton(onClick = { filterMenuExpanded = true }) {
                            Text(text = "Filter:<${filterTag ?: ""}>")
                        }
                        DropdownMenu(
                            expanded = filterMenuExpanded,
                            onDismissRequest = { filterMenuExpanded = false }) {
                            SampleTags.values().forEach {
                                DropdownMenuItem(onClick = {
                                    filterTag = it.name
                                    filterMenuExpanded = false
                                }) {
                                    Text(text = it.name)
                                }
                            }
                            DropdownMenuItem(onClick = {
                                filterTag = null; filterMenuExpanded = false
                            }) {
                                Text(text = "None")
                            }

                        }
                    }

                    SettingsButton { navController.navigate(SettingsScreenDestination) }
                }
            }
        ) {
            BasicCardDeck(
                questDeck = filteredQuestDeck,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = Paddings.default, vertical = Paddings.loose),
                confirmQuestDeleted = {
                    with(fullQuestDeck) {
                        viewModel.saveQuests(copy(quests-it))
                    }
                    true
                }
            )
        }
        // The new Quest Dialog
        if (creatingNewQuest) {
            Dialog(onDismissRequest = { creatingNewQuest = false }) {
                val localContext = LocalContext.current
                NewQuestCard(draftQuest = viewModel.draftQuest,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = Paddings.loose),
                    confirmDraftCreated = {
                        viewModel.draftQuest = it
                        creatingNewQuest = false
                        true
                    },
                    confirmNewQuestCreated = { newQuest ->
                        with(newQuest) {
                            if (description !in fullQuestDeck.quests.map { it.description }) let {
                                viewModel.saveNewQuest(it)
                                creatingNewQuest = false
                                Toast.makeText(localContext, "Quest created", Toast.LENGTH_SHORT).show()
                                true
                            } else {
                                Toast.makeText(localContext, "Quest with same description already exists", Toast.LENGTH_SHORT).show()
                                false
                            }
                        }
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
                        filteredQuestDeck.quests,
                        key = { _, item -> item.toString() }) { _, item ->
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