package com.example.quiz

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.ui.theme.QuizTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    /*Box(
                        modifier = Modifier
                            .fillMaxSize()
                            //.background(Color.Green)
                    ) {


                        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            title()
                            enterName()
                            //buttonContinue()
                        }
                    }*/

                    Navigation()

                    playMainFun()

                }
            }
        }
    }


}


@Composable
fun playMainFun() {

    val context = LocalContext.current



    val menuMusic: MediaPlayer = remember {
        MediaPlayer.create(context, R.raw.synthwave6)
    }

    val introMusic: MediaPlayer = remember {
        MediaPlayer.create(context, R.raw.synth_verb2)
    }

    Manager.menuMusic = menuMusic

    LaunchedEffect(playMenu) {

        CoroutineScope(Dispatchers.IO).launch {

            if(playMain) {
                menuMusic.isLooping = true
                menuMusic.start()
            }
            else{
                introMusic.start()
            }


        }



    }

}

/*@Composable
fun title(){

    Text(
        text = "Quiz",
        color = Color.White,
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 150.dp, bottom = 30.dp)

    )

}

@Composable
private fun enterName(){

    val nameState = remember { mutableStateOf("") }
    //var hideKeyboard = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .size(300.dp, 190.dp)

    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(
                text = "Digite seu nome",
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 15.dp)

            )

            OutlinedTextField(
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                value = nameState.value,
                onValueChange = {nameState.value = it},
                label = { Text(text = stringResource(id = R.string.name))},

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    cursorColor = Color.Black),

                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(270.dp)
                    .height(58.dp)

            )

            Button(
                onClick = {onAddTapped(nameState.toString())},
                modifier = Modifier
                    .width(270.dp)
                    .height(55.dp)
                    .padding(top = 15.dp)
            ){
                Text(text = stringResource(id = R.string.cont))
            }


        }


    }
}

private fun onAddTapped(nameState:String){
    //val userState = _addUserState.value ?: return
    if(nameState.isNotEmpty()) {
        Manager.user = nameState

    }
}
*/