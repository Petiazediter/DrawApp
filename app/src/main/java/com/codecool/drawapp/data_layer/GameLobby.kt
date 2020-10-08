package com.codecool.drawapp.data_layer

class GameLobby() {

    lateinit var gameId : String
    lateinit var gameLeader : String
    lateinit var players : ArrayList<User>
    var round : Int = 1

    constructor(id:String,leader:String,players:ArrayList<User>,curRound:Int) : this(){
        gameId = id
        gameLeader = leader
        this.players = players
        round = curRound
    }
}