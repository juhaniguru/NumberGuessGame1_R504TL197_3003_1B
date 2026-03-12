package com.example.numberguessgame1_r504tl197_3003_1b.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlin.random.Random

@Composable
fun NumberGuessScreenRoot(modifier: Modifier = Modifier) {









    // tätä kutsutaan, kun
    // käyttäjä painaa "Arvaa"-nappia
    fun onGuess() {
        try {
            val numberInt = state.value.number.toInt()

            if (numberInt == state.value.correctNumber) {

                state.value = state.value.copy(
                    correct = true,
                    guessText = "Arvasit oikein! Siihen meni ${state.value.timesGuessed + 1} kertaa"
                )
            } else {

                var text = "Arvasit väärin"

                if (numberInt > state.value.correctNumber) {
                    text += ", arvauksesi on liian suuri"
                } else {
                    text += ", arvauksesi on liian pieni"
                }



                state.value = state.value.copy(guessText = text, correct = false)

            }

            state.value = state.value.copy(timesGuessed = state.value.timesGuessed + 1)
            // tässä ei ole virheviesteillä merkitystä
            // pidetään vain huoli siitä, ettei sovellus kaadu
            // jos käyttäjä syöttää kenttään tekstiä,
            // jota ei voi muuttaa kokonaisluvuksi
        } catch (e: Exception) {
        }


    }
    // juuricomposablessa kutsutaan @composablea, joka piirtää käyttöliittymän
    // tässä toteutuu state hoistin, koska
    // tila valuu alaspäin NumberGuessScreenille ja
    // eventit tulevat takaisin ylös
    NumberGuessScreen(
        guessText = state.value.guessText,
        number = state.value.number,
        updateText = { newText ->
            Log.d("juhanitestaa", newText)
            state.value = state.value.copy(number = newText)
        },
        onGuess = { onGuess() },
        onNewGame = { onNewGame() }
    )
}

@Composable
fun NumberGuessScreen(
    modifier: Modifier = Modifier,
    guessText: String,
    number: String,
    // updateText saa parametrinaan merkkijonon String, eikä palauta mitään
    // updateText suoritetaan, kun käyttäjä kirjoittaa numeroa tekstikenttään
    updateText: (String) -> Unit,
    // onGuess on eventhandler, joka ei ota parametrejä vastaan,
    // eikä palauta mitään. onGuess suoritetaan, kun painetaan Uusi Peli-nappia
    onGuess: () -> Unit,
    // onNewGame on signatureltaan samanlainen kuin onGuess (ei ota parametrejä,
    // eikä palauta mitään). Kutustaan kun painetaan Uusi peli-nappia
    onNewGame: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                // tekstikentän arvo on Rootilta staten numberin arvo
                value = number,
                // tämä on onvalueChange-lambda,
                // newText on käyttäjän tekstikenttään kirjoittama uusi teksti!
                // kun teksti päivittyy,
                // 1. kutustaan updateText
                // 2. se päivittää Rootissa staten
                // 3. uusi staten arvo valuu tänne takaisin
                // 4. tekstikentän value päivittyy
                onValueChange = { newText ->

                    updateText(newText)
                }
            )

            // Nappi, jonka onClick-eventissä
            // suoriteaan onGuess-funktio
            // enabled = number != "" tarkoittaa, että
            // nappia voi painaa vain, kun tekstikenttä ei ole tyhjä
            Button(onClick = onGuess, enabled = number != "") {
                Text("Arvaa")
            }

            // Näytetään napin alla teksti, osuiko arvaus kohdalleen
            Text(guessText)

            // alimpana napi, josta voi koska tahansa
            // aloittaa uuden pelin
            Button(onClick = onNewGame) {
                Text("Uusi peli")
            }

        }
    }
}