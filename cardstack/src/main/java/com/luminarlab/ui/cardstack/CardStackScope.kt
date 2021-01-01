package com.luminarlab.ui.cardstack

/**
 * A scope that gives information about the current card stack
 */

data class CardStackScope(
    val currentIndex: Int,
    val cardStackController: CardStackController
)