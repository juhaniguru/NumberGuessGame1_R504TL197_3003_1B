package com.example.numberguessgame1_r504tl197_3003_1b.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class NumberGuessScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(NumberGuessState())
    val state = _state.asStateFlow()

    fun onNewGame() {

        _state.update { currentState ->
            currentState.copy(
                correct = false,
                guessText = "",
                number = "",
                timesGuessed = 0,
                correctNumber = Random.nextInt(1, 101)
            )
        }

    }

}