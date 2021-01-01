package com.luminarlab.ui.cardstack

import androidx.compose.animation.AnimatedFloatModel
import androidx.compose.animation.asDisposableClock
import androidx.compose.animation.core.AnimationClockObservable
import androidx.compose.animation.core.AnimationClockObserver
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.SwipeableDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp

/**
 * Controller of the [draggableStack] modifier.
 *
 * @param clock The animation clock that will be used to drive the animations.
 * @param screenWidth The width of the screen used to calculate properties such as rotation and scale
 * @param animationSpec The default animation that will be used to animate swipes.
 */
open class CardStackController(
    clock: AnimationClockObservable,
    private val screenWidth: Float,
    internal val animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
) {
    /**
     * Whether the state is currently animating.
     */
    var isAnimationRunning: Boolean by mutableStateOf(false)
        private set

    /**
     * Anchors
     */
    val right = Offset(screenWidth, 0f)
    val left = Offset(-screenWidth, 0f)
    val center = Offset(0f, 0f)

    /**
     * Threshold to start swiping
     */
    var threshold: Float = 0.0f

    // May Change
    private val animationClockProxy: AnimationClockObservable = object : AnimationClockObservable {
        override fun subscribe(observer: AnimationClockObserver) {
            isAnimationRunning = true
            clock.subscribe(observer)
        }

        override fun unsubscribe(observer: AnimationClockObserver) {
            isAnimationRunning = false
            clock.unsubscribe(observer)
        }
    }

    /**
     * The current position (in pixels) of the First Card.
     */
    val offsetX = AnimatedFloatModel(0f, animationClockProxy)
    val offsetY = AnimatedFloatModel(0f, animationClockProxy)

    /**
     * The current rotation (in pixels) of the First Card.
     */
    val rotation = AnimatedFloatModel(0f, animationClockProxy)

    /**
     * The current scale factor (in pixels) of the Card before the first one displayed.
     */
    val scale = AnimatedFloatModel(0.8f, animationClockProxy)

    var onSwipe : (direction : Swipe.Direction) -> Unit = {}


    fun swipeLeft(){
        offsetX.animateTo(-screenWidth, animationSpec) { endReason, endOffset ->
            onSwipe(Swipe.Direction.LEFT)
            // After the animation of swiping return back to Center to make it look like a cycle
            offsetX.snapTo(center.x)
            offsetY.snapTo(0f)
            rotation.snapTo(0f)
            scale.snapTo(0.8f)
        }
        scale.animateTo(1f, animationSpec)
    }

    fun swipeRight(){
        offsetX.animateTo(screenWidth, animationSpec) { endReason, endOffset ->
            onSwipe(Swipe.Direction.RIGHT)
            // After the animation return back to Center to make it look like a cycle
            offsetX.snapTo(center.x)
            offsetY.snapTo(0f)
            rotation.snapTo(0f)
            scale.snapTo(0.8f)
        }
        scale.animateTo(1f, animationSpec)
    }

    fun returnCenter(){
        offsetX.animateTo(center.x, animationSpec)
        offsetY.animateTo(center.y, animationSpec)
        rotation.animateTo(0f, animationSpec)
        scale.animateTo(0.8f, animationSpec)
    }

}

/**
 * Create and [remember] a [CardStackController] with the default animation clock.
 *
 * @param animationSpec The default animation that will be used to animate to a new state.
 */
@Composable
fun rememberCardStackController(
        animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
): CardStackController {
    val clock = AmbientAnimationClock.current.asDisposableClock()
    val screenWidth = with(AmbientDensity.current){
        AmbientConfiguration.current.screenWidthDp.dp.toPx()
    }
    return remember {
        CardStackController(
                clock = clock,
                screenWidth = screenWidth,
                animationSpec = animationSpec
        )
    }
}

