package com.example.monitor_iot.http.models

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String)
