package com.codecool.drawapp.api

import com.google.gson.annotations.SerializedName

class RandomWord{
    @SerializedName("id")
    var id : Int? = null
    @SerializedName("word")
    var name : String? = null
}