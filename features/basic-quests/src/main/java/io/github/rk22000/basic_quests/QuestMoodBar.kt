package io.github.rk22000.basic_quests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.github.rk22000.data.Complexity
import io.github.rk22000.data.Mood


@Preview
@Composable
fun MoodBar(
    modifier: Modifier = Modifier,
    currentMood: Mood = Mood.NORMAL,
    onMoodChanged: (Mood)->Unit = {  }
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Mood.values().forEach {
            TextButton(
                onClick = { onMoodChanged(it) },
                colors = if (it == currentMood) {
                    ButtonDefaults.textButtonColors()
                } else {
                    ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colors.primary.copy(alpha = 0.5f)
                    )
                }
            ) {
                Text(it.label)
            }
        }
    }
}