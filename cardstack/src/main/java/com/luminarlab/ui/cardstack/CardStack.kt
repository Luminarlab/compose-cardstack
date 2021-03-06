package com.luminarlab.ui.cardstack

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luminarlab.ui.cardstack.base.CardStackScope
import com.luminarlab.ui.cardstack.base.Swipe
import com.luminarlab.ui.cardstack.base.rememberCardStackController
import com.luminarlab.ui.cardstack.modifier.draggableStack

/**
 * A stack of cards that can be dragged.
 * If they are dragged after a [thresholdConfig] or exceed the [velocityThreshold] the card is swiped.
 *
 * @param items Cards to show in the stack.
 * @param thresholdConfig Specifies where the threshold between the predefined Anchors is. This is represented as a lambda
 * that takes two float and returns the threshold between them in the form of a [ThresholdConfig].
 * @param velocityThreshold The threshold (in dp per second) that the end velocity has to exceed
 * in order to swipe, even if the positional [thresholds] have not been reached.
 * @param enableButtons Show or not the buttons to swipe or not
 * @param onSwipeLeft Lambda that executes when the animation of swiping left is finished
 * @param onSwipeRight Lambda that executes when the animation of swiping right is finished
 * @param onEmptyRight Lambda that executes when the cards are all swiped
 */
@ExperimentalMaterialApi
@Composable
fun <T> CardStack(
    modifier: Modifier = Modifier,
    items: MutableList<T>,
    thresholdConfig: (Float, Float) -> ThresholdConfig = { _, _ -> FractionalThreshold(0.2f) },
    velocityThreshold: Dp = 125.dp,
    onSwipe: (item: T, index: Int, direction: Swipe.Direction) -> Unit = { _, _, _ -> },
    onEmptyStack: (lastItem: T) -> Unit = {},
    buttons: @Composable CardStackScope.() -> Unit = {},
    empty: @Composable () -> Unit = {},
    content: @Composable CardStackScope.(item: T, index: Int) -> Unit
) {


    val csc = rememberCardStackController()
    val scope by remember { mutableStateOf(CardStackScope(items.size - 1, csc)) }
    scope.apply {

        if (isEmpty) onEmptyStack(items.last())


        cardStackController.onSwipe = { direction ->
            onSwipe(items[currentIndex], items.size - currentIndex, direction)
            currentIndex--
        }


        ConstraintLayout(modifier = modifier.fillMaxSize().padding(20.dp)) {
            val (buttonsRef, stack) = createRefs()


            Box(modifier = Modifier
                .constrainAs(buttonsRef) {
                    this.bottom.linkTo(parent.bottom)
                    top.linkTo(stack.bottom)
                }) {
                buttons()
            }


            Box(modifier = Modifier
                .constrainAs(stack) {
                    top.linkTo(parent.top)
                }
                .draggableStack(
                    controller = cardStackController,
                    thresholdConfig = thresholdConfig,
                    velocityThreshold = velocityThreshold
                )
                .fillMaxHeight(0.8f)
            ) {
                if (!isEmpty) items.asReversed().forEachIndexed { index, item ->

                    content(item, index)
                } else empty()
            }
        }
    }
}



