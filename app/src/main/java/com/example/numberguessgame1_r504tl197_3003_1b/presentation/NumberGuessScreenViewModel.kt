package com.example.numberguessgame1_r504tl197_3003_1b.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class NumberGuessScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(NumberGuessState())
    val state = _state.asStateFlow()

    fun onGuess() {
        try {
            val numberInt = state.value.number.toInt()

            if (numberInt == state.value.correctNumber) {

                _state.update { currentState ->
                    currentState.copy(
                        correct = true,
                        guessText = "Arvasit oikein! Siihen meni ${state.value.timesGuessed + 1} kertaa"
                    )
                }
            } else {

                var text = "Arvasit väärin"

                if (numberInt > state.value.correctNumber) {
                    text += ", arvauksesi on liian suuri"
                } else {
                    text += ", arvauksesi on liian pieni"
                }



                _state.update { currentState ->
                    currentState.copy(guessText = text, correct = false)
                }

            }

            _state.update { currentState ->
                currentState.copy(timesGuessed = state.value.timesGuessed + 1)
            }
            // tässä ei ole virheviesteillä merkitystä
            // pidetään vain huoli siitä, ettei sovellus kaadu
            // jos käyttäjä syöttää kenttään tekstiä,
            // jota ei voi muuttaa kokonaisluvuksi
        } catch (e: Exception) {
        }


    }

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