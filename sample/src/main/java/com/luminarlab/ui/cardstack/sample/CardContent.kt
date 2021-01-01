package com.luminarlab.ui.cardstack

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luminarlab.ui.cardstack.sample.Item
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun CardContent(modifier: Modifier = Modifier, item: Item) {
    Box(modifier = modifier) {
        if (item.url != null) {
            CoilImage(
                data = item.url,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = item.text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.clickable(
                    onClick = {},
                    indication = null
                ) // disable the highlight of the text when dragging
            )
            Text(
                text = item.subText,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.clickable(
                    onClick = {},
                    indication = null
                ) // disable the highlight of the text when dragging
            )
        }
    }
}