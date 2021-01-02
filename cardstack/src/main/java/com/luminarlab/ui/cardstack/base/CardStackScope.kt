package com.luminarlab.ui.cardstack.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.luminarlab.ui.cardstack.base.CardStackController

/**
 * A scope that gives information about the current card stack
 */

 class CardStackScope(
    currentIndex:Int ,
    cardStackController:CardStackController
){
    var currentIndex: Int by mutableStateOf(currentIndex)
    val cardStackController: CardStackController by mutableStateOf(cardStackController)
    val isEmpty get() = currentIndex < 0

    override fun toString(): String {
        return "CardStackScope(currentIndex=$currentIndex, cardStackController=$cardStackController, isEmpty=$isEmpty)"
    }


}