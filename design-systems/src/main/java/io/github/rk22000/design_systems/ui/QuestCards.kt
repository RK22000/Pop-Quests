package io.github.rk22000.design_systems.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.rk22000.data.Complexity
import io.github.rk22000.data.Importance
import io.github.rk22000.data.Quest
import io.github.rk22000.data.SampleTags
import io.github.rk22000.design_systems.theme.Paddings
import io.github.rk22000.design_systems.theme.cardShape
import java.time.LocalDate

@Composable
fun BasicQuestCard(
    quest: Quest,
    modifier: Modifier = Modifier,
    shape: Shape = cardShape,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
) {
    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation
    ) {
        Column(Modifier.padding(Paddings.default)) {
            Text(
                text = quest.priority.toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = quest.description,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Start Day\n${LocalDate.ofEpochDay(quest.startLine)}",
                    color = MaterialTheme.colors.primary
                )
                quest
                    .takeUnless { it.deadLind == LocalDate.MAX.toEpochDay() }
                    ?.let {
                        Text(
                            text = "Deadline \n${LocalDate.ofEpochDay(it.deadLind)}",
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.End
                        )
                    }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                quest.tags.forEachIndexed { index, tag ->
                    TagButton(
                        tagLabel = tag,
                        modifier = Modifier
                            .let {
                                if (index != 0)
                                    it.padding(start = Paddings.tight)
                                else
                                    it
                            },
                        selected = { true },
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = quest.importance.label)
                Divider(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Text(text = quest.complexity.label)
            }

        }
    }

}

@Preview
@Composable
fun BasicCardPreview() {
    BasicQuestCard(
        quest = Quest(
            description = LoremIpsum(50).values.joinToString(" "),
            importance = Importance.DESIRABLE,
            complexity = Complexity.SIMPLE,
            tags = listOf(SampleTags.FUN.name)
        ),
        modifier = Modifier.size(width = 300.dp, height = 500.dp)
    )
}

@Preview
@Composable
fun hello() {
    Surface {
        Text(text = "Helo World")
    }
}