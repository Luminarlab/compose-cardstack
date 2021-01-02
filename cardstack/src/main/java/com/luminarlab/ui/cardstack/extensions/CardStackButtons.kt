package com.luminarlab.ui.cardstack

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDownAlt
import androidx.compose.material.icons.filled.ThumbUpAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.luminarlab.ui.cardstack.base.CardStackScope

/**
 * Von Yannick Knoll am 2021-01-01 erstellt.
 */

@Composable
fun CardStackScope.CardStackButtons(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FloatingActionButton(
            onClick = {
                Log.d("MOVL", "${this@CardStackButtons}")
                if (currentIndex >= 0 && !this@CardStackButtons.isEmpty) cardStackController.swipeLeft()
            },
            backgroundColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation()
        ) {
            Icon(Icons.Filled.ThumbDownAlt, tint = Color.Red)
        }
        Spacer(modifier = Modifier.width(70.dp))
        FloatingActionButton(
            onClick = {
                Log.d("MOVL", "${this@CardStackButtons}")
                if (currentIndex >= 0 && !this@CardStackButtons.isEmpty) cardStackController.swipeRight()
            },
            backgroundColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation()
        ) {
            Icon(Icons.Filled.ThumbUpAlt, tint = Color.Green)
        }
    }
}