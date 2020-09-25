package com.codecool.drawapp.data_layer

 class FriendInvite (){

     lateinit var fromId : String
     lateinit var toId : String

     constructor(fId : String, tId : String) : this(){
         fromId = fId
         toId = tId
     }

 }