package com.example.numberguessgame1_r504tl197_3003_1b.presentation

data class NumberGuessState(
    // tämä on käyttäjän syöttämä numero
    // kaikki syötteet ovat merkkijonoja (merkkijono on vain numeerinen tässä tapauksessa)
    val number: String = "",
    // tämä on teksti joka näytetään käyttäjälle
    // kertoo onko arvaus oikein, suurempi vai pienempi kuin arvottu luku
    val guessText: String = "",
    // totuusarvo, jolla määritetään, onko arvaus oikein vain ei
    // jos oikein,  näytetään käyttäjälle uusi peli-nappi, josta voi aloittaa pelin alusta
    val correct: Boolean = false,
    val correctNumber: Int = 0,
    val timesGuessed: Int = 0


)
