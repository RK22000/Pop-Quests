package io.github.rk22000.design_systems.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.rk22000.design_systems.theme.Paddings

@Preview
@Composable
fun ListItem(
    firstItem: ()->Boolean = {false},
    content: @Composable ()->Unit = { Text(text = "This is ListItem") },
) {
   Column(modifier = Modifier.padding(horizontal = Paddings.default)) {
       if (!firstItem()) {
           Divider(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(vertical = Paddings.tight),
               color = MaterialTheme.colors.onBackground
           )
       }
       content()
   }
}