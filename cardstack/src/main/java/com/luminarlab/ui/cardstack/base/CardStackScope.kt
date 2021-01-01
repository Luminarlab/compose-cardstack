package com.luminarlab.ui.cardstack.base

import com.luminarlab.ui.cardstack.base.CardStackController

/**
 * A scope that gives information about the current card stack
 */

data class CardStackScope(
    val currentIndex: Int,
    val cardStackController: CardStackController
)