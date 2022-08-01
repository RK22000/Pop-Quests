package io.github.rk22000.design_systems.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import io.github.rk22000.data.Complexity
import io.github.rk22000.data.Importance
import io.github.rk22000.data.Quest
import io.github.rk22000.data.QuestViewModel
import io.github.rk22000.design_systems.theme.Paddings
import io.github.rk22000.design_systems.theme.Shapes
import io.github.rk22000.design_systems.theme.cardShape
import io.github.rk22000.design_systems.theme.stroke

@Composable
fun NewQuestCard(
    draftQuest: Quest,
    modifier: Modifier = Modifier,
    confirmDraftChanged: (Quest) -> Boolean = { true },
    confirmNewQuestCreated: (Quest) -> Boolean = { true },
    confirmDraftCreated: (Quest) -> Boolean = { true },
    shape: Shape = cardShape,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
) {
//    WindowInsets.Companion.ime
    Card(
        modifier = modifier,
//            .windowInsetsPadding(WindowInsets.ime),
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation
    ) {
        Column(
            Modifier
                .padding(Paddings.default)
                .fillMaxSize()
        ) {
            var draft by remember {
                mutableStateOf(draftQuest)
            }
            TextField(
                value = draft.description,
                onValueChange = { newDescription ->
                    draft.copy(description = newDescription)
                        .takeIf(confirmDraftChanged)
                        ?.let { confirmedDraft -> draft = confirmedDraft }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                label = { Text("New Quest") },
                placeholder = { Text(text = "Enter the new Quest") }
            )
            Row {
                Box(Modifier.weight(1f)) {
                    var impExpanded by remember {
                        mutableStateOf(false)
                    }
                    DropdownMenuItem(onClick = { impExpanded = true }) {
                        Text(text = draft.importance.label)
                    }
                    DropdownMenu(
                        expanded = impExpanded,
                        onDismissRequest = { impExpanded = false }) {
                        Importance.values().forEach {
                            DropdownMenuItem(onClick = {
                                draft.copy(importance = it)
                                    .takeIf(confirmDraftChanged)
                                    ?.let { confirmedDraft -> draft = confirmedDraft
                                        impExpanded = false
                                    }

                            }) {
                                Text(text = it.label)
                            }
                        }
                    }
                }

                Box(Modifier.weight(1f)) {
                    var impExpanded by remember {
                        mutableStateOf(false)
                    }
                    DropdownMenuItem(onClick = { impExpanded = true }) {
                        Text(text = draft.complexity.label)
                    }
                    DropdownMenu(
                        expanded = impExpanded,
                        onDismissRequest = { impExpanded = false }) {
                        Complexity.values().forEach {
                            DropdownMenuItem(onClick = {
                                draft.copy(complexity = it)
                                    .takeIf(confirmDraftChanged)
                                    ?.let { confirmedDraft -> draft = confirmedDraft
                                        impExpanded = false
                                    }
                            }) {
                                Text(text = it.label)
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { if (confirmNewQuestCreated(draft)) { confirmDraftChanged(Quest("", Importance.DESIRABLE, Complexity.SIMPLE))} }) {
                    Text(text = "Create Quest",
                        maxLines = 1
                    )
                }
                TextButton(onClick = { if (confirmDraftCreated(draft)) { /* TODO exit card */ } }) {
                    Text(text = "Draft Quest",
                    maxLines = 1)
                    
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewNewQuestCard() {
    NewQuestCard(draftQuest = Quest("", Importance.DESIRABLE, Complexity.SIMPLE))
}