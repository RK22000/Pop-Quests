package io.github.rk22000.design_systems.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import io.github.rk22000.design_systems.theme.Paddings
import io.github.rk22000.design_systems.theme.cardShape

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
            complexity = Complexity.SIMPLE
        ),
        modifier = Modifier.size(width = 300.dp, height = 500.dp)
    )
}

@Preview
@Composable
fun hello(){
    Surface {
        Text(text = "Helo World")
    }
}