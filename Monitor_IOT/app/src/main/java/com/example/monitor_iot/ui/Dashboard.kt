package com.example.monitor_iot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitor_iot.databinding.ActivityDashboardBinding
import com.example.monitor_iot.http.adapters.RetrofitAdapter
import com.example.monitor_iot.http.models.Login
import com.example.monitor_iot.http.models.Temperatura
import com.example.monitor_iot.ui.adapters.TemperaturaAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarRegistros()
        binding.fab.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                var intent = Intent(this@Dashboard,ActivityGrafica::class.java)
                startActivity(intent)
            }
        })
    }

    fun cargarRegistros(){
        CoroutineScope(Dispatchers.IO).launch {
            var adaptador = RetrofitAdapter()
            var call = adaptador.obtenerRegistros()

            call.enqueue(object : Callback<List<Temperatura>> {
                override fun onResponse(call: Call<List<Temperatura>>, response: Response<List<Temperatura>>) {
                    if (response.isSuccessful){
                        binding.recyclerView.apply{
                            layoutManager = LinearLayoutManager(context)
                            adapter = TemperaturaAdapter(response.body()!!)
                        }
                    }else{
                        Toast.makeText(this@Dashboard, "No esta disponible el servidor", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<List<Temperatura>>, t: Throwable) {
                    Toast.makeText(this@Dashboard, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
            delay(1000*65)
            cargarRegistros()
        }
    }

}