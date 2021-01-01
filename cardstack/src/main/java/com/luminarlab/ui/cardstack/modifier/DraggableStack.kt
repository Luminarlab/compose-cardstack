package com.luminarlab.ui.cardstack.modifier

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ThresholdConfig
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.rawDragGestureFilter
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luminarlab.ui.cardstack.base.CardStackController
import kotlin.math.abs
import kotlin.math.sign

/**
 * Enable drag gestures between a set of predefined anchors defined in [controller].
 *
 * @param controller The controller of the [draggableStack].
 * @param thresholdConfig Specifies where the threshold between the predefined Anchors is. This is represented as a lambda
 * that takes two float and returns the threshold between them in the form of a [ThresholdConfig].
 * @param velocityThreshold The threshold (in dp per second) that the end velocity has to exceed
 * in order to swipe, even if the positional [thresholds] have not been reached.
 */
@ExperimentalMaterialApi
fun Modifier.draggableStack(
    controller: CardStackController,
    thresholdConfig: (Float, Float) -> ThresholdConfig,
    velocityThreshold: Dp = 125.dp
) = composed {
    val density = AmbientDensity.current
    val velocityThresholdPx = with(density) { velocityThreshold.toPx() }
    val thresholds = { a: Float, b: Float ->
        with(thresholdConfig(a, b)) {
            density.computeThreshold(a, b)
        }
    }
    controller.threshold = thresholds(controller.center.x, controller.right.x)
    val draggable = Modifier.rawDragGestureFilter(
        object : DragObserver {
            override fun onStop(velocity: Offset) {
                super.onStop(velocity)
                if (controller.offsetX.value <= 0f) {
                    if (velocity.x <= -velocityThresholdPx) {
                        controller.swipeLeft()
                    } else {
                        if (controller.offsetX.value > -controller.threshold) controller.returnCenter()
                        else controller.swipeLeft()
                    }
                } else {
                    if (velocity.x >= velocityThresholdPx) {
                        controller.swipeRight()
                    } else {
                        if (controller.offsetX.value < controller.threshold) controller.returnCenter()
                        else controller.swipeRight()
                    }
                }
            }

            override fun onDrag(dragDistance: Offset): Offset {
                if (!controller.isAnimationRunning) {
                    controller.offsetX.snapTo(controller.offsetX.value + dragDistance.x)
                    controller.offsetY.snapTo(controller.offsetY.value + dragDistance.y)
                    val targetRotation = normalize(
                        controller.center.x,
                        controller.right.x,
                        abs(controller.offsetX.value),
                        0f,
                        10f
                    )
                    controller.rotation.snapTo(targetRotation * -controller.offsetX.value.sign)
                    controller.scale.snapTo(
                        normalize(
                            controller.center.x,
                            controller.right.x / 3,
                            abs(controller.offsetX.value),
                            0.8f
                        )
                    )
                }
                return super.onDrag(dragDistance)
            }
        },
        canStartDragging = { !controller.isAnimationRunning }
    )

    draggable
}

/**
 * Min max normalization
 *
 * @param min Minimum of the range
 * @param max Maximum of the range
 * @param v Value to normalize in the given [min, max] range
 * @param startRange Transform the normalized value with a particular start range
 * @param endRange Transform the normalized value with a particular end range
 */
fun normalize(
    min: Float,
    max: Float,
    v: Float,
    startRange: Float = 0f,
    endRange: Float = 1f
): Float {
    require(startRange < endRange) {
        "Start range is greater than End range"
    }
    val value = v.coerceIn(min, max)
    return (value - min) / (max - min) * (endRange - startRange) + startRange
}