package io.github.rk22000.design_systems.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.rk22000.design_systems.R

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