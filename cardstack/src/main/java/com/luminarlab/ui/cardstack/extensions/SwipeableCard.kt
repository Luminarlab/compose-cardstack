package com.luminarlab.ui.cardstack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.luminarlab.ui.cardstack.base.CardStackScope
import com.luminarlab.ui.cardstack.modifier.moveTo
import com.luminarlab.ui.cardstack.modifier.visible

@Composable
fun CardStackScope.SwipeableCard(
    modifier: Modifier = Modifier,
    index: Int,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.moveTo(
            x = if (index == currentIndex) cardStackController.offsetX.value else 0f,
            y = if (index == currentIndex) cardStackController.offsetY.value else 0f
        )
            .visible(visible = index == currentIndex || index == currentIndex - 1)
            .graphicsLayer(
                rotationZ = if (index == currentIndex) cardStackController.rotation.value else 0f,
                scaleX = if (index < currentIndex) cardStackController.scale.value else 1f,
                scaleY = if (index < currentIndex) cardStackController.scale.value else 1f
            )
            .shadow(4.dp, RoundedCornerShape(10.dp))
    ) {
        content()
    }
}