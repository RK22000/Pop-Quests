package io.github.rk22000.design_systems.ui

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.rk22000.data.Quest
import io.github.rk22000.data.QuestDeck
import io.github.rk22000.data.SampleData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasicCardDeck(
    questDeck: QuestDeck,
    modifier: Modifier = Modifier,
    confirmQuestDeleted:  (Quest) -> Boolean = { true },
    // Don't create a confirmed Deck changed
    // Instead create confirm <some action>
    // Prevent this composable from taking unnecessary/unexpected actions
) {
    var quests by remember(key1 = questDeck){
        mutableStateOf(questDeck.quests)
    }
    var confirmKey by remember {
        mutableStateOf(false)
    }
    val dismissState = remember(key1 = quests, key2 = confirmKey) {
        DismissState(DismissValue.Default, confirmStateChange = {
            true
        })
    }
    Log.v("BasicCardDeck", "Recomposing with QuestDeck with ${questDeck.quests.size} quests on ${Thread.currentThread().name} " +
        "confirm key = $confirmKey, animating = ${dismissState.isAnimationRunning}, dismissState.currentValue = ${dismissState.currentValue}")
    if (!dismissState.isAnimationRunning && dismissState.currentValue != DismissValue.Default) {
        val deletedQuest = quests[0]
        val alteredQuests = quests.subList(1, quests.size)
        if (confirmQuestDeleted(deletedQuest))
            quests = alteredQuests
        else
            confirmKey = !confirmKey

    }

    if (quests.isNotEmpty()) {
        SwipeToDismiss(
            state = dismissState,
            modifier = modifier,
            dismissThresholds = { FractionalThreshold(0.9f) },
            background = {
                quests.getOrNull(1)?.let {
                    val nextCardScale = 0.95f
                    BasicQuestCard(
                        quest = it,
                        modifier = Modifier
                            .scale(nextCardScale + (1-nextCardScale) * dismissState.progress.fraction),
                        elevation = dismissState.progress.fraction.dp
                    )
                }
            },
        ) {
            quests.getOrNull(0)?.let {
                BasicQuestCard(quest = it)
            }
        }
    }

}

@Preview
@Composable
fun BasicDeckPreview() {
    BasicCardDeck(
        questDeck = SampleData.getSampleQuests(),
        modifier = Modifier.size(300.dp, 500.dp)
    )
}