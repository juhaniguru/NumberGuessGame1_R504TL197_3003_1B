package com.example.numberguessgame1_r504tl197_3003_1b

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.numberguessgame1_r504tl197_3003_1b.ui.theme.NumberGuessGame1_R504TL197_3003_1BTheme
import kotlin.random.Random

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

@Composable
fun NumberGuessScreenRoot(modifier: Modifier = Modifier) {

    // tehdään MutableState (mutableStateOf), joka on tyyppiä
    // NumberGuessState
    val state = remember {
        mutableStateOf(NumberGuessState())
    }
    // alussa arvotaan numero, kun peli käynnistetään
    // numero on 1-100
    if (state.value.correctNumber == 0) {
        state.value = state.value.copy(correctNumber = Random.nextInt(1, 101))
    }


    // tätä kutsutaan, kun käyttäjä painaa "Uusi peli"-nappia
    // se vain nollaa tilan siihen, mitä se oli ennen 1. käynnistystä
    fun onNewGame() {

        state.value = state.value.copy(
            correct = false,
            guessText = "",
            number = "",
            timesGuessed = 0,
            correctNumber = Random.nextInt(1, 101)
        )

    }

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
        updateText = { state.value = state.value.copy(number = it) },
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumberGuessGame1_R504TL197_3003_1BTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NumberGuessScreenRoot(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding))
                }
            }
        }
    }
}

