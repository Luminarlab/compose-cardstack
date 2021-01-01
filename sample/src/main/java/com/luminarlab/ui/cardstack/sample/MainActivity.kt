package com.luminarlab.ui.cardstack.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.platform.setContent
import com.luminarlab.ui.cardstack.*

class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        val item1 = Item(
            "https://f4.bcbits.com/img/0020592180_10.jpg",
            "Jane",
            "16 miles near you"
        )
        val item2 = Item(
            "https://images.pexels.com/photos/91224/pexels-photo-91224.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260",
            "Robert",
            "7 miles near you"
        )
        val item3 = Item(
            "https://images.pexels.com/photos/789812/pexels-photo-789812.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
            "Daria",
            "3 miles from you"
        )
        val item4 = Item(
            "https://images.pexels.com/photos/872756/pexels-photo-872756.jpeg?cs=srgb&dl=pexels-dishan-lathiya-872756.jpg&fm=jpg",
            "Violet",
            "43 miles from you"
        )
        val items = mutableListOf(item1, item2, item3, item4)
        super.onCreate(savedInstanceState)



        setContent {
            val cardStackController = rememberCardStackController()

            CardStack(
                modifier = Modifier,
                enableButtons = true,
                items = items,
                cardStackController = cardStackController,
            ) { item, index, currentIndex ->
                SwipeableCard(
                    currentIndex = currentIndex,
                    index = index,
                    cardStackController = cardStackController
                ) {
                    CardContent(item = item)
                }
            }
        }
    }
}





