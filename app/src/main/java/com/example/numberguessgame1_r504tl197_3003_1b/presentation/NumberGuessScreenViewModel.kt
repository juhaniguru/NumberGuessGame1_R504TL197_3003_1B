package com.example.numberguessgame1_r504tl197_3003_1b.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NumberGuessScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(NumberGuessState())
    val state = _state.asStateFlow()

}