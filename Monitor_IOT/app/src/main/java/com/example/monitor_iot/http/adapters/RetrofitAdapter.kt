package com.example.monitor_iot.http.adapters

import com.example.monitor_iot.http.models.Login
import com.example.monitor_iot.http.models.Temperatura
import com.example.monitor_iot.http.services.IService
import retrofit2.Call

class RetrofitAdapter:BaseAdapter("http://192.168.212.180:5000/"),IService {
    private val service:IService

    override fun login(login: Login): Call<Login> {
        return service.login(login)
    }

    override fun obtenerRegistros(): Call<List<Temperatura>> {
        return service.obtenerRegistros()
    }

    init {
        service = createService(IService::class.java)
    }
}