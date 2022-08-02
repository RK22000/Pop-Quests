package io.github.rk22000.data

import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

object SampleData {
    fun getSampleQuests(): QuestDeck {
        val words = listOf(50, 80, 30, 120, 65, 263, 14)
        return QuestDeck(
            words.mapIndexed { index, len ->
                Quest(
                    description = "Quest $index \n" + LoremIpsum(len).values.joinToString(" "),
                    importance = Importance.DESIRABLE,
                    complexity = Complexity.values().getOrElse(index % Complexity.values().size) { Complexity.RPI }
                )
            }
        )
    }
}

class DeckPreviewProvider : PreviewParameterProvider<QuestDeck> {
    override val values: Sequence<QuestDeck>
        get() = sequenceOf(SampleData.getSampleQuests())
}

class QuestPreviewProvider: PreviewParameterProvider<Quest> {
    override val values: Sequence<Quest>
        get() = SampleData.getSampleQuests().quests.asSequence()
}