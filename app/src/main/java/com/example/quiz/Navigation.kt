package com.example.quiz

import android.graphics.Color.parseColor
import android.media.MediaPlayer
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size

import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.room.Room
import com.example.quiz.room.AppDatabase
import com.example.quiz.room.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

//import androidx.compose.runtime.livedata.observeAsState
import kotlin.time.Duration.Companion.seconds

//import androidx.navigation.navArgument

val String.color
    get() = Color(parseColor(this))

val neonFont = FontFamily(Font(R.font.neon_80s))

val colorPurple = "#CC3CFF".color
val colorBlue = "#62C5FD".color
val colorGreen = "#83FFC3".color



@Composable
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Intro.route){
        composable(
            route = Screen.Intro.route
        ){
            Intro(navController = navController)
        }
        composable(route = Screen.MainScreen.route){

            MainScreen(navController = navController)
        }
        composable(
            route = Screen.Question.route,
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType

                }
            )
        ){
            Question(navController = navController)
        }

        composable(
            route = Screen.Leaderboard.route
        ){
            Leaderboard()
        }
    }
}




val shadowColorPurple = colorPurple.copy(alpha = 0.5f).toArgb()
val transparentPurple = colorPurple.copy(alpha= 0f).toArgb()
val transparentGreen = colorGreen.copy(alpha= 0f).toArgb()

var playMenu = false
var launched = false

var isVisible = false

@OptIn(ExperimentalTextApi::class)
@Composable
fun Intro(navController: NavController) {

    val context = LocalContext.current

    /*val introMusic: MediaPlayer = remember {
        MediaPlayer.create(context, R.raw.synth_verb2)
    }

    LaunchedEffect(playMenu) {

        CoroutineScope(Dispatchers.IO).launch {


            introMusic.start()



        }

    }*/

    var alphaBright by remember{ mutableStateOf(0f) }
    var alphaShadow by remember{ mutableStateOf(0f) }

    var startTransition by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true){
        startTransition = true
    }

    val transition = rememberInfiniteTransition()
    val animatedPurple by transition.animateColor(
        initialValue = colorPurple,
        targetValue = colorPurple.copy(alpha = 0.9f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedGreen by animateColorAsState(
        if(startTransition) colorGreen.copy(alpha = 1f) else colorGreen.copy(alpha = 0f),
        animationSpec = tween(18000)
    )

    var introTicks by remember{mutableStateOf(0)}

    LaunchedEffect(introTicks) {
        if(introTicks < 13 ) {
            delay(1.seconds)
            introTicks++
            Log.d("TAGAnim", introTicks.toString())
        }
        else if(introTicks >= 4){
            playMenu = true
            playMain = true
            navController.navigate(Screen.MainScreen.route)
        }
    }

    /*Button(

        onClick = {
            isVisible = !isVisible
        },
        modifier = Modifier
            .width(100.dp)
            .height(50.dp)






    ){
        Text(
            text = "oiiiiiii"
        )
    }*/

    

        

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
    
        ) {
            /*AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally() + fadeIn()

            ) {*/




                Card(
                    border = BorderStroke(4.dp, animatedGreen),
                    backgroundColor = "#000000".color,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .align(Alignment.Center)
                        .rotate(-12f)
                        .drawBehind {
                            drawIntoCanvas {
                                val paint = Paint()
                                val frameworkPaint = paint.asFrameworkPaint()
                                frameworkPaint.color = transparentGreen
                                frameworkPaint.setShadowLayer(
                                    20.dp.toPx(),
                                    0.dp.toPx(),
                                    0.dp.toPx(),
                                    animatedGreen.toArgb()
                                )
                                it.drawRoundRect(
                                    0f,
                                    0f,
                                    this.size.width,
                                    this.size.height,
                                    0.dp.toPx(),
                                    0.dp.toPx(),
                                    paint
                                )
                            }
                        }
                        .clip(RoundedCornerShape(25.dp))
                        .size(280.dp, 120.dp)


                ) {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = "Luigi Studios",
                            color = animatedGreen,
                            fontFamily = neonFont,

                            fontSize = 40.sp,
                            style = TextStyle.Default.copy(
                                shadow = Shadow(
                                    color = colorGreen,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 50f
                                ),
                                drawStyle = Stroke(
                                    miter = 10f,
                                    width = 5f,
                                    join = StrokeJoin.Round
                                )
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 35.dp, bottom = 5.dp)
                                .rotate(2f)

                        )


                    }
                }

                /*val context = LocalContext.current
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data(data = R.drawable.koopa_gif1).apply(block = {
                            size(Size.ORIGINAL)
                        }).build(), imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .width(180.dp)
                        .height(122.dp)
                        .padding(vertical = 0.dp)
                        .align(Alignment.CenterEnd)
                        .offset(y = 100.dp, x = 20.dp)
                )*/
           // }
        }


}

var playMain = false


@OptIn(ExperimentalTextApi::class)
@Composable
fun MainScreen(navController: NavController) {

    val context = LocalContext.current
    mQuestionsList = Manager.getQuestions()
    curPos = 1


    //val mMediaPlayer = MediaPlayer.create(context, R.raw.synthwave2)


    if(playMain){
        Manager.menuMusic!!.isLooping = true
        Manager.menuMusic!!.start()
        //playMain = false
    }

    /*if(playMenu) {
        DisposableEffect(Unit) {
            mMediaPlayer.isLooping = true
            mMediaPlayer.start()
            playMenu = false

            onDispose {

            }
        }
    }*/





    val transition = rememberInfiniteTransition()
    val animatedPurple by transition.animateColor(
        initialValue = colorPurple,
        targetValue = colorPurple.copy(alpha = 0.9f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedPurpleShadow by transition.animateColor(
        initialValue = colorPurple.copy(alpha = 0.8f),
        targetValue = colorPurple.copy(alpha = 0.5f),
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )


    val animatedGreen by transition.animateColor(
        initialValue = colorGreen,
        targetValue = colorGreen.copy(alpha = 0.9f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedBlue by transition.animateColor(
        initialValue = colorBlue,
        targetValue = colorBlue.copy(alpha = 0.9f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )


    val nameState = remember { mutableStateOf("") }


    /////////////////////////



    var getLeadBool = true

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "user"
    ).build()

    Manager.leaderboard.clear()

    var getUsers:List<com.example.quiz.room.User> = listOf()

    /*CoroutineScope(Dispatchers.IO).launch {
        db.userDao().nukeTable()
    }*/

    LaunchedEffect(getLeadBool) {


        CoroutineScope(Dispatchers.IO).launch {
            if (db.userDao().getAll().isNotEmpty()) {
                getUsers = db.userDao().getAll()

                for (i in getUsers) {
                    Manager.addUserLeaderboard(i.user, i.points)
                }

                getLeadBool = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {



        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            Box() {

                val offset = -30.dp

                val context = LocalContext.current
                val imageLoader = ImageLoader.Builder(context)
                    .components {
                        if (SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data(data = R.drawable.palm2).apply(block = {
                            size(Size.ORIGINAL)
                        }).build(), imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(112.dp)
                        .padding(vertical = 0.dp)
                        .align(Alignment.TopEnd)
                        .offset(y = 160.dp, x = 20.dp)
                )

                Text(
                    text = "80's TRIVIA",
                    color = animatedPurple,
                    fontFamily = neonFont,
                    fontSize = 43.sp,
                    style = TextStyle.Default.copy(
                        shadow =  Shadow(
                            color =  colorPurple,
                            offset = Offset(2f, 2f),
                            blurRadius = 50f
                        ),
                        drawStyle = Stroke(
                            miter = 10f,
                            width = 5f,
                            join = StrokeJoin.Round
                        )
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 150.dp, bottom = 100.dp, end = 90.dp)

                )


            }







            Card(
                border = BorderStroke(4.dp,"#CC3CFF".color),
                backgroundColor = "#000000".color,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .drawBehind {
                        drawIntoCanvas {
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = transparentPurple
                            frameworkPaint.setShadowLayer(
                                20.dp.toPx(),
                                0.dp.toPx(),
                                0.dp.toPx(),
                                animatedPurple.toArgb()
                            )
                            it.drawRoundRect(
                                0f,
                                0f,
                                this.size.width,
                                this.size.height,
                                0.dp.toPx(),
                                0.dp.toPx(),
                                paint
                            )
                        }
                    }
                    .clip(RoundedCornerShape(18.dp))
                    .background("#CC3CFF".color)
                    .size(310.dp, 190.dp)



            ){

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(
                        text = "DIGITE SEU NOME",
                        color = animatedGreen,
                        fontSize = 18.sp,
                        fontFamily = neonFont,
                        //fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = TextStyle.Default.copy(
                            shadow =  Shadow(
                                color = "#83FFC3".color,
                                offset = Offset(2f, 2f),
                                blurRadius = 50f
                            ),
                        ),
                        modifier = Modifier

                            .padding(top = 25.dp)

                    )

                    TextField(
                        textStyle = LocalTextStyle.current.copy(color = "#83FFC3".color),
                        value = nameState.value,
                        onValueChange = {
                            nameState.value = it},
                        label = { Text(text = stringResource(id = R.string.name))},

                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = "#83FFC3".color,
                            unfocusedBorderColor = "#83FFC3".color,
                            focusedLabelColor = "#83FFC3".color,
                            cursorColor = "#83FFC3".color),

                        modifier = Modifier
                            .padding(top = 20.dp)
                            .width(270.dp)
                            .height(58.dp)

                    )




                }


            }


        }

        val shadowColorBlue = colorBlue.copy(alpha = 0.5f).toArgb()
        val transparentBlue = colorBlue.copy(alpha= 0f).toArgb()
        val offsetY = -150.dp

        Button(


            border = BorderStroke(4.dp,"#62C5FD".color),
            colors = ButtonDefaults.buttonColors(backgroundColor = "#000000".color),
            onClick = {onAddTapped(nameState.value, navController)},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = offsetY)
                .drawBehind {
                    drawIntoCanvas {
                        val paint = Paint()
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.color = transparentBlue
                        frameworkPaint.setShadowLayer(
                            20.dp.toPx(),
                            0.dp.toPx(),
                            0.dp.toPx(),
                            animatedBlue.toArgb()
                        )
                        it.drawRoundRect(
                            0f,
                            0f,
                            this.size.width,
                            this.size.height,
                            0.dp.toPx(),
                            0.dp.toPx(),
                            paint
                        )

                    }

                }
                .background(Color.Black)
                .width(200.dp)
                .height(55.dp)

            // .padding(top = 15.dp)


        ){
            Text(
                text = stringResource(id = R.string.cont),
                color = "#62C5FD".color,

                )
        }

        val offsetY2 = -70.dp

        Button(



            colors = ButtonDefaults.buttonColors(backgroundColor = "#000000".color),
            onClick = { gotToLeader(navController) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = offsetY2, x = 60.dp)
                .drawBehind {
                    drawIntoCanvas {
                        val paint = Paint()
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.color = transparentPurple
                        frameworkPaint.setShadowLayer(
                            20.dp.toPx(),
                            0.dp.toPx(),
                            0.dp.toPx(),
                            shadowColorPurple
                        )
                        it.drawRoundRect(
                            0f,
                            0f,
                            this.size.width,
                            this.size.height,
                            0.dp.toPx(),
                            0.dp.toPx(),
                            paint
                        )

                    }

                }
                .background(Color.Black)
                .width(150.dp)
                .height(40.dp)

            // .padding(top = 15.dp)


        ){
            Text(
                text = "Leaderboard",
                color = "#CC3CFF".color,
                style = TextStyle.Default.copy(
                    shadow =  Shadow(
                        color = "#CC3CFF".color,
                        offset = Offset(2f, 2f),
                        blurRadius = 100f
                    ),
                    drawStyle = Stroke(
                        miter = 10f,
                        width = 5f,
                        join = StrokeJoin.Round
                    )
                ),
                )
        }
    }
}

private fun onAddTapped(nameState:String, navController: NavController){

    if(nameState.isNotEmpty()) {
        Manager.user = nameState
        navController.navigate(Screen.Question.route)
    }
}

private fun gotToLeader(navController: NavController){
    navController.navigate(Screen.Leaderboard.route)
}

var mQuestionsList: List<Question>? = null



private fun randomizeOptions(question: Question?):List<String>{
    val options = ArrayList<String>()
    options.add(0, question!!.optionOne)
    options.add(1, question!!.optionTwo)
    options.add(2, question!!.optionThree)
    options.add(3, question!!.optionFour)

    val randomized = options.shuffled()
    //Log.d("TAG", options.joinToString())
    //Log.d("TAG2", randomized.joinToString())

    return randomized
}

private fun checkAnswer(answer:String, question:Question):Boolean{

    //var getCorrectRandom = randomized.indexOf(question.correctAnswer)


        return answer == (question.correctAnswer)

}


private fun checkAnswer2(answer:String, question:Question, colorPos:Int, randomized:List<String>, questionColors: ArrayList<Color>){

    var getCorrectRandom = randomized.indexOf(question.correctAnswer)

    Log.d("TAG", questionColors.joinToString())

    if(answer == (question.correctAnswer)){
        questionColors[colorPos] = "#83FFC3".color
        //score += 100

    }
    else{
        questionColors[colorPos] = "#EA3838".color
        questionColors[getCorrectRandom] = "#83FFC3".color

    }





    Log.d("TAG2", questionColors.joinToString())

}


//val questionColors = ArrayList<String>()

var curPos = 1
var score = 0


@Composable
fun Question(navController: NavController) {

    val context = LocalContext.current

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "user"
    ).build()

    val transition = rememberInfiniteTransition()
    val animatedPurple by transition.animateColor(
        initialValue = colorPurple,
        targetValue = colorPurple.copy(alpha = 0.9f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedBlue by transition.animateColor(
        initialValue = colorBlue,
        targetValue = colorBlue.copy(alpha = 0.8f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )


    var countPoints by remember { mutableStateOf(true) }

    var tickPoints by remember { mutableStateOf(30) }


    var count by remember { mutableStateOf(false) }

    var ticks by remember { mutableStateOf(0) }


    var shadowColor1 by remember {mutableStateOf(colorPurple.copy(alpha = 0.5f).toArgb())}
    var transparent1 by remember {mutableStateOf(colorPurple.copy(alpha= 0f).toArgb())}

    var shadowColor2 by remember {mutableStateOf(colorPurple.copy(alpha = 0.5f).toArgb())}
    var transparent2 by remember {mutableStateOf(colorPurple.copy(alpha= 0f).toArgb())}

    var shadowColor3 by remember {mutableStateOf(colorPurple.copy(alpha = 0.5f).toArgb())}
    var transparent3 by remember {mutableStateOf(colorPurple.copy(alpha= 0f).toArgb())}

    var shadowColor4 by remember {mutableStateOf(colorPurple.copy(alpha = 0.5f).toArgb())}
    var transparent4 by remember {mutableStateOf(colorPurple.copy(alpha= 0f).toArgb())}



    var color1 by remember {mutableStateOf("#CC3CFF".color)}
    var color2 by remember {mutableStateOf("#CC3CFF".color)}
    var color3 by remember {mutableStateOf("#CC3CFF".color)}
    var color4 by remember {mutableStateOf("#CC3CFF".color)}


    var questionColors by remember {mutableStateOf(ArrayList<Color>())}

    questionColors.add(0, color1)
    questionColors.add(1, color2)
    questionColors.add(2, color3)
    questionColors.add(3, color4)


    LaunchedEffect(tickPoints, countPoints) {
        if(tickPoints > 0 && countPoints) {
            delay(1.seconds)
            tickPoints--
            Log.d("TAGPoints", tickPoints.toString())
        }
        else if(tickPoints <= 0){
            countPoints = false
        }
    }


    val question: Question? = mQuestionsList!!.get(curPos - 1)

    var save = true

    if(Manager.randomized.isEmpty()){
        Manager.randomized = randomizeOptions(question)
    }

    val randomized = Manager.randomized


    var timeRemaining = remember { mutableStateOf(60) }

    if(curPos >= 10){
        Manager.curPoints = score

        //Manager.addUserLeaderboard(Manager.user, Manager.curPoints)
        LaunchedEffect(save) {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().insert(User(Manager.user, Manager.curPoints))
                save = false
            }
        }
        Manager.randomized = listOf()
        curPos = 1
        score = 0
        navController.navigate(Screen.MainScreen.route)
    }



    LaunchedEffect(ticks, count) {
        if(ticks < 2 && count) {
            delay(1.seconds)
            ticks++
            Log.d("TAG3", ticks.toString())
        }
        else if(ticks >= 1){
            Log.d("TAGGOFASSE", questionColors[0].toString())



            count = false
            ticks = 0
            navController.navigate(Screen.Question.route)


        }

        if(ticks == 1){



            questionColors[0] = "#CC3CFF".color
            questionColors[1] = "#CC3CFF".color
            questionColors[2] = "#CC3CFF".color
            questionColors[3] = "#CC3CFF".color

            curPos +=1
            Manager.randomized = listOf()

            //




            Log.d("TAG OPTIONS", Manager.randomized.toString())


        }
    }

    //Log.d("TAG QUESTIONS", mQuestionsList.toString())







    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Text(
            text = "Score: " + score.toString(),
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = neonFont,
            //fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .width(350.dp)
                .padding(top = 10.dp, start = 5.dp)

        )

        Text(
            text = tickPoints.toString(),
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = neonFont,
            //fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .width(350.dp)
                .padding(top = 10.dp, end = 0.dp)

        )

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {




            Card(
                border = BorderStroke(4.dp,"#CC3CFF".color),
                backgroundColor = "#000000".color,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .drawBehind {
                        drawIntoCanvas {
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = transparentPurple
                            frameworkPaint.setShadowLayer(
                                20.dp.toPx(),
                                0.dp.toPx(),
                                0.dp.toPx(),
                                animatedPurple.toArgb()
                            )
                            it.drawRoundRect(
                                0f,
                                0f,
                                this.size.width,
                                this.size.height,
                                0.dp.toPx(),
                                0.dp.toPx(),
                                paint
                            )
                        }
                    }
                    .clip(RoundedCornerShape(18.dp))
                    .background("#CC3CFF".color)
                    .size(290.dp, 180.dp)




            ){
                Image(
                    painter = painterResource(question!!.image),
                    contentDescription = "imagem",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))

                )
            }

            Text(
                text = question!!.question,
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = neonFont,
                //fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(350.dp)
                    .padding(top = 35.dp)

            )

            val shadowColorBlue = colorBlue.copy(alpha = 0.5f).toArgb()
            val transparentBlue = colorBlue.copy(alpha= 0f).toArgb()
            val offsetY = 40.dp
            val padBottom = 35.dp


            Button(


                border = BorderStroke(4.dp,color1),
                colors = ButtonDefaults.buttonColors(backgroundColor = "#000000".color),
                onClick = {
                    if(count == false) {
                        checkAnswer2(randomized[0], question, 0, randomized, questionColors)
                        color1 = questionColors[0]

                        count = true
                        shadowColor1 = questionColors[0].copy(alpha = 0.5f).toArgb()
                        transparent1 = questionColors[0].copy(alpha = 0f).toArgb()

                        if(checkAnswer(randomized[0], question)){
                            score += (100 * (tickPoints / 2))
                        }

                    }

                    /*if(checkAnswer(randomized[0], question, 0, randomized)){
                        color1 = "#83FFC3".color
                        curPos+=1
                        score += 100
                        color1 = "#CC3CFF".color


                    }
                    else{
                        color1 = "#EA3838".color
                        curPos+=1
                        color1 = "#CC3CFF".color
                    }*/
                          },
                modifier = Modifier
                    .offset(y = offsetY)
                    .padding(bottom = padBottom)
                    .drawBehind {
                        drawIntoCanvas {
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = transparent1
                            frameworkPaint.setShadowLayer(
                                20.dp.toPx(),
                                0.dp.toPx(),
                                0.dp.toPx(),
                                shadowColor1
                            )
                            it.drawRoundRect(
                                0f,
                                0f,
                                this.size.width,
                                this.size.height,
                                0.dp.toPx(),
                                0.dp.toPx(),
                                paint
                            )

                        }

                    }
                    .background(Color.Black)
                    .width(340.dp)
                    .height(53.dp)

                // .padding(top = 15.dp)


            ){
                Text(
                    text = randomized[0],
                    color = Color.White,

                    )
            }


            Button(


                border = BorderStroke(4.dp,color2),
                colors = ButtonDefaults.buttonColors(backgroundColor = "#000000".color),
                onClick = {
                    if(count == false) {
                        checkAnswer2(randomized[1], question, 1, randomized, questionColors)
                        color2 = questionColors[1]

                        count = true

                        shadowColor2 = questionColors[1].copy(alpha = 0.5f).toArgb()
                        transparent2 = questionColors[1].copy(alpha = 0f).toArgb()

                        if(checkAnswer(randomized[1], question)){
                            score += (100 * (tickPoints / 2))
                        }

                        /*if(checkAnswer(randomized[1], question, 1, randomized)){
                            color2 = "#83FFC3".color
                            curPos+=1
                            score += 100
                            color2 = "#CC3CFF".color


                        }
                        else{
                            color2 = "#EA3838".color
                            curPos+=1
                            color2 = "#CC3CFF".color
                        }*/
                    }
                          },
                modifier = Modifier
                    .offset(y = offsetY)
                    .padding(bottom = padBottom)
                    .drawBehind {
                        drawIntoCanvas {
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = transparent2
                            frameworkPaint.setShadowLayer(
                                20.dp.toPx(),
                                0.dp.toPx(),
                                0.dp.toPx(),
                                shadowColor2
                            )
                            it.drawRoundRect(
                                0f,
                                0f,
                                this.size.width,
                                this.size.height,
                                0.dp.toPx(),
                                0.dp.toPx(),
                                paint
                            )

                        }

                    }
                    .background(Color.Black)
                    .width(340.dp)
                    .height(53.dp)

                // .padding(top = 15.dp)


            ){
                Text(
                    text = randomized[1],
                    color = Color.White,

                    )
            }


            Button(


                border = BorderStroke(4.dp,color3),
                colors = ButtonDefaults.buttonColors(backgroundColor = "#000000".color),
                onClick = {
                    if(count == false) {
                        checkAnswer2(randomized[2], question, 2, randomized, questionColors)
                        color3 = questionColors[2]

                        count = true

                        shadowColor3 = questionColors[2].copy(alpha = 0.5f).toArgb()
                        transparent3 = questionColors[2].copy(alpha = 0f).toArgb()

                        if(checkAnswer(randomized[2], question)){
                            score += (100 * (tickPoints / 2))
                        }

                        /*if(checkAnswer(randomized[2], question, 2, randomized)){
                            color1 = "#83FFC3".color
                            curPos+=1
                            score += 100
                            color1 = "#CC3CFF".color


                        }
                        else{
                            color1 = "#EA3838".color
                            curPos+=1
                            color1 = "#CC3CFF".color
                        }*/
                    }
                          },
                modifier = Modifier
                    .offset(y = offsetY)
                    .padding(bottom = padBottom)
                    .drawBehind {
                        drawIntoCanvas {
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = transparent3
                            frameworkPaint.setShadowLayer(
                                20.dp.toPx(),
                                0.dp.toPx(),
                                0.dp.toPx(),
                                shadowColor3
                            )
                            it.drawRoundRect(
                                0f,
                                0f,
                                this.size.width,
                                this.size.height,
                                0.dp.toPx(),
                                0.dp.toPx(),
                                paint
                            )

                        }

                    }
                    .background(Color.Black)
                    .width(340.dp)
                    .height(53.dp)

                // .padding(top = 15.dp)


            ){
                Text(
                    text = randomized[2],
                    color = Color.White,

                    )
            }


            Button(


                border = BorderStroke(4.dp,color4),
                colors = ButtonDefaults.buttonColors(backgroundColor = "#000000".color),
                onClick = {
                    if(count == false) {
                        checkAnswer2(randomized[3], question, 3, randomized, questionColors)
                        color4 = questionColors[3]

                        count = true

                        shadowColor4 = questionColors[3].copy(alpha = 0.5f).toArgb()
                        transparent4 = questionColors[3].copy(alpha = 0f).toArgb()

                        if(checkAnswer(randomized[3], question)){
                            score += (100 * (tickPoints / 2))
                        }

                        /*if(checkAnswer(randomized[3], question, 3, randomized)){
                            color1 = "#83FFC3".color
                            curPos+=1
                            score += 100
                            color1 = "#CC3CFF".color


                        }
                        else{
                            color1 = "#EA3838".color
                            curPos+=1
                            color1 = "#CC3CFF".color
                        }*/
                    }
                          },
                modifier = Modifier
                    .offset(y = offsetY)
                    .padding(bottom = padBottom)
                    .drawBehind {
                        drawIntoCanvas {
                            val paint = Paint()
                            val frameworkPaint = paint.asFrameworkPaint()
                            frameworkPaint.color = transparent4
                            frameworkPaint.setShadowLayer(
                                20.dp.toPx(),
                                0.dp.toPx(),
                                0.dp.toPx(),
                                shadowColor4
                            )
                            it.drawRoundRect(
                                0f,
                                0f,
                                this.size.width,
                                this.size.height,
                                0.dp.toPx(),
                                0.dp.toPx(),
                                paint
                            )

                        }

                    }
                    .background(Color.Black)
                    .width(340.dp)
                    .height(53.dp)

                // .padding(top = 15.dp)


            ){
                Text(
                    text = randomized[3],
                    color = Color.White,

                    )
            }


        }


    }
    
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Leaderboard() {


    val transition = rememberInfiniteTransition()
    val animatedPurple by transition.animateColor(
        initialValue = colorPurple,
        targetValue = colorPurple.copy(alpha = 0.9f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedBlue by transition.animateColor(
        initialValue = colorBlue,
        targetValue = colorBlue.copy(alpha = 0.8f),
        animationSpec = infiniteRepeatable(
            animation = tween(80),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val shadowColorPurple = colorPurple.copy(alpha = 0.5f).toArgb()
        val transparentPurple = colorPurple.copy(alpha = 0f).toArgb()


        Card(
            border = BorderStroke(4.dp, "#CC3CFF".color),
            backgroundColor = "#000000".color,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .padding(top = 0.dp)
                .align(Alignment.Center)
                .drawBehind {
                    drawIntoCanvas {
                        val paint = Paint()
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.color = transparentPurple
                        frameworkPaint.setShadowLayer(
                            20.dp.toPx(),
                            0.dp.toPx(),
                            0.dp.toPx(),
                            animatedPurple.toArgb()
                        )
                        it.drawRoundRect(
                            0f,
                            0f,
                            this.size.width,
                            this.size.height,
                            0.dp.toPx(),
                            0.dp.toPx(),
                            paint
                        )
                    }
                }
                .clip(RoundedCornerShape(18.dp))
                .background("#CC3CFF".color)
                .size(310.dp, 635.dp)


        ) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Leaderboard",
                    color = animatedPurple,
                    fontFamily = neonFont,

                    fontSize = 28.sp,
                    style = TextStyle.Default.copy(
                        shadow = Shadow(
                            color = "#CC3CFF".color,
                            offset = Offset(2f, 2f),
                            blurRadius = 50f
                        ),
                        drawStyle = Stroke(
                            miter = 10f,
                            width = 5f,
                            join = StrokeJoin.Round
                        )
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 35.dp, bottom = 5.dp)

                )

                if (Manager.desc.isNotEmpty()) {
                    Column(Modifier.fillMaxSize()) {
                        for (i in Manager.desc) {


                            Row(
                                Modifier

                                    .padding(top = 25.dp)
                            ) {
                                Text(
                                    text = (Manager.desc.indexOf(i) + 1).toString() + " - " + i.name,
                                    //text = "1 - luigi",
                                    color = "#62C5FD".color,
                                    fontFamily = neonFont,
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Left,
                                    style = TextStyle.Default.copy(
                                        shadow = Shadow(
                                            color = animatedBlue,
                                            offset = Offset(2f, 2f),
                                            blurRadius = 50f
                                        ),
                                        drawStyle = Stroke(
                                            miter = 10f,
                                            width = 7f,
                                            join = StrokeJoin.Round
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(top = 0.dp, start = 35.dp)

                                )

                                Text(
                                    text = i.points.toString(),
                                    //text = "6500",
                                    color = "#62C5FD".color,
                                    fontFamily = neonFont,
                                    textAlign = TextAlign.End,
                                    fontSize = 25.sp,
                                    style = TextStyle.Default.copy(
                                        shadow = Shadow(
                                            color = animatedBlue,
                                            offset = Offset(2f, 2f),
                                            blurRadius = 50f
                                        ),
                                        drawStyle = Stroke(
                                            miter = 10f,
                                            width = 7f,
                                            join = StrokeJoin.Round
                                        )
                                    ),
                                    modifier = Modifier
                                        .padding(top = 0.dp, start = 100.dp)

                                )
                            }


                        }

                    }
                }


            }
        }
    }

}
