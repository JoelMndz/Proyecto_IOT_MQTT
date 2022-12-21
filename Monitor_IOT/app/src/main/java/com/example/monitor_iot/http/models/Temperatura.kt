package com.example.monitor_iot.http.models

import com.google.gson.annotations.SerializedName

data class Temperatura(
    @SerializedName("dato")
    var dato:Int,
    @SerializedName("createdAt")
    var createdAt:String
)
