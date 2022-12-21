package com.example.monitor_iot.http.services

import com.example.monitor_iot.http.models.Login
import com.example.monitor_iot.http.models.Temperatura
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IService {
    @POST("api/login")
    fun login(@Body login:Login):Call<Login>;

    @GET("api/temperatura")
    fun obtenerRegistros():Call<List<Temperatura>>
}