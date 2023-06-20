package com.example.quiz

import android.media.MediaPlayer

object Manager {

    var user = ""

    var curPoints = 0

    var leaderboard = ArrayList<UserModel>()

    var desc:List<UserModel> = listOf()

    var randomized:List<String> = listOf()

    var menuMusic:MediaPlayer? = null

    fun addUserLeaderboard(user:String, score:Int){
        val userModel = UserModel(user, score)
        leaderboard.add(userModel)
        //leaderboard.sortByDescending { leaderboard -> Manager.leaderboard.size }
        desc = leaderboard.sortedWith(compareByDescending({ it.points }))
    }

    fun getQuestions(): List<Question>{
        val questionsList = ArrayList<Question>()

        val q1 = Question(1, "Na década de 80, qual foi o modelo de carro mais popular nos Estados Unidos?",
            R.drawable.cars,
            "Oldsmobile Cutlass",
            "Ford Escort",
            "Chevrolet Cavalier",
            "Honda Accord",
            "Oldsmobile Cutlass")

        questionsList.add(q1)

        val q2 = Question(2, "Qual é o estilo musical que traz muito da estética dos anos 80 aos dias atuais?",
            R.drawable.eight,
            "Vaporwave",
            "Pop",
            "SynthWave",
            "Lofi",
            "SynthWave")

        questionsList.add(q2)

        val q3 = Question(3, "Originalmente com o título de 'Puckman' no japão, qual jogo de arcade dos anos 80 foi indicado ao Guiness como 'Jogo operado por moedas mais bem sucedido'?",
            R.drawable.arcade,
            "Enduro",
            "Pac-Man",
            "Super Mario Bros",
            "Pitfall",
            "Pac-Man")

        questionsList.add(q3)

        val q4 = Question(4, "No filme 'De Volta Para o Futuro', de 1985, qual o carro utilizado como máquina do tempo pelos protagonistas?",
            R.drawable.back,
            "DMC DeLorean",
            "Chevrolet Celebrity",
            "Oldsmobile Cutlass Supreme",
            "Lamborghini Countach",
            "DMC DeLorean")

        questionsList.add(q4)

        val q5 = Question(5, "No filme 'Halloween III - A Noite das Bruxas', de 1982, qual era o festival antigo que o proprietario da empresa Silver Shamrock Novelties, Conal Cochran estava tentando ressucitar?",
            R.drawable.halloween,
            "Halloween",
            "Samhain",
            "Dia dos mortos",
            "Noite de Santa Valburga",
            "Samhain")

        questionsList.add(q5)


        val q6 = Question(6, "Qual foi o video game portátil da Nintendo lançado em 1989?",
            R.drawable.nintendo,
            "Nintendo DS",
            "Master System",
            "NES",
            "Gameboy",
            "Gameboy")

        questionsList.add(q6)

        val q7 = Question(7, "Matt e Ross Duffer são os diretores de qual série da Netflix situada em Indiana, nos anos 80?",
            R.drawable.indiana,
            "Mindhunter",
            "Stranger Things",
            "Dark",
            "Wednesday",
            "Stranger Things")

        questionsList.add(q7)

        val q8 = Question(8, "Em 1983, a primeira Camcorder foi lançada por qual empresa?",
            R.drawable.cam,
            "Technicolor",
            "Fujifilm",
            "Sony",
            "Kodak",
            "Sony")

        questionsList.add(q8)

        val q9 = Question(9, "No filme 'E.T - O Extraterrestre', qual doce Elliot dá para o E.T?",
            R.drawable.et,
            "Rocky Road",
            "Sweet Tarts",
            "Golden Crisp",
            "Reeses",
            "Reeses")

        questionsList.add(q9)

        val q10 = Question(10, "O que significa o acrônimo 'VHS'?",
            R.drawable.vhs,
            "Vector Holographic Signs",
            "Video Home System",
            "Video Host System",
            "Vintage Home Supply",
            "Video Home System")

        questionsList.add(q10)

        return questionsList.toList().shuffled()
    }

}