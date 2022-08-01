package io.github.rk22000.design_systems.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val fabShape = RoundedCornerShape(50)
val cardShape = RoundedCornerShape(30.dp)

val stroke = BorderStroke(2.dp, Purple200)