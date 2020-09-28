package com.codecool.drawapp.data_layer

class GameLobby() {

    lateinit var gameId : String
    lateinit var gameLeader : String
    lateinit var players : List<User>
    var round : Int = 1

    constructor(id:String,leader:String,players:List<User>,curRound:Int) : this(){
        gameId = id
        gameLeader = leader
        this.players = players
        round = curRound
    }
}