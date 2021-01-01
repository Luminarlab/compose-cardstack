package com.luminarlab.ui.cardstack.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.visible(
    visible: Boolean = true
) = this.then(Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    if (visible) {
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    } else {
        layout(0, 0) {}
    }
})