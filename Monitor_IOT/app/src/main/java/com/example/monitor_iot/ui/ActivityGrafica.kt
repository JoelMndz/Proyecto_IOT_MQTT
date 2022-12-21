package com.example.monitor_iot.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitor_iot.R
import com.example.monitor_iot.databinding.ActivityGraficaBinding
import com.example.monitor_iot.http.adapters.RetrofitAdapter
import com.example.monitor_iot.http.models.Temperatura
import com.example.monitor_iot.ui.adapters.TemperaturaAdapter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityGrafica : AppCompatActivity() {
    private lateinit var binding: ActivityGraficaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGraficaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cargarRegistros()
    }

    fun cargarRegistros(){
        CoroutineScope(Dispatchers.IO).launch {
            var adaptador = RetrofitAdapter()
            var call = adaptador.obtenerRegistros()

            call.enqueue(object : Callback<List<Temperatura>> {
                override fun onResponse(call: Call<List<Temperatura>>, response: Response<List<Temperatura>>) {
                    if (response.isSuccessful){
                        var lineEntry = ArrayList<Entry>()
                        var temperaturas = response.body()!!.reversed()

                        for (i in 0..temperaturas.size-1){
                            lineEntry.add(Entry(i.toFloat(),temperaturas[i].dato.toFloat()))
                        }
                        var lineDataset =LineDataSet(lineEntry,"Temperatura")

                        lineDataset.color = resources.getColor(R.color.purple_500)
                        lineDataset.valueTextSize = 0f
                        lineDataset.fillColor = Color.BLUE

                        lineDataset.setDrawFilled(true)
                        lineDataset.fillColor = resources.getColor((R.color.ligth))
                        lineDataset.fillAlpha = 30

                        binding.lineChart.data = LineData(lineDataset)
                        binding.lineChart.animateX(2000)

                    }
                }
                override fun onFailure(call: Call<List<Temperatura>>, t: Throwable) {
                    Log.e("Error",t.message.toString())
                }
            })
            delay(1000*65)
            cargarRegistros()
        }
    }

    fun establecerGrafica(temperaturas:List<Temperatura>){
        var lineEntry = ArrayList<Entry>()
        for (i in 0..temperaturas.size-1){
            lineEntry.add(Entry(i.toFloat(),temperaturas[i].dato.toFloat()))
        }


        var lineDataset =LineDataSet(lineEntry,"Temperatura")
        lineDataset.color = resources.getColor(R.color.purple_500)
        lineDataset.circleRadius = 10f
        binding.lineChart.data = LineData(lineDataset)
    }

}