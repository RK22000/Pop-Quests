package io.github.rk22000.design_systems.ui

import android.nfc.Tag
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.rk22000.design_systems.R
import io.github.rk22000.design_systems.theme.Paddings
import io.github.rk22000.design_systems.theme.fabShape

@Preview
@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    clickable: () -> Boolean = { true },
    onClick: () -> Unit = { }
) {
    Image(
        painter = painterResource(id = R.drawable.ic_baseline_settings_24),
        contentDescription = "Settings Button",
        modifier = modifier
            .clickable(enabled = clickable(), onClick = onClick)
    )
}

@Composable
fun TagButton(
    tagLabel: String,
    modifier: Modifier = Modifier,
    selected: () -> Boolean = { true },
    clickable: () -> Boolean = { false },
    onClick: () -> Unit = { }
) {
    Text(
        text = tagLabel,
        modifier = modifier
            .clickable(enabled = clickable(), onClick = onClick)
            .let {
                if (selected())
                    it.background(color = MaterialTheme.colors.primary, shape = fabShape)
                else
                    it.border(width = 1.dp, color = MaterialTheme.colors.primary, shape = fabShape)
            }
            .padding(horizontal = Paddings.tight),
    )
}

@Preview
@Composable
fun TagPreview() {
    Column {
        TagButton(tagLabel = "Selected", selected = { true })
        Spacer(modifier = Modifier.size(5.dp))
        TagButton(tagLabel = "Unselected", selected = { false })
    }
}