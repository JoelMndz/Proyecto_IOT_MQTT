package com.example.monitor_iot.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitor_iot.R
import com.example.monitor_iot.databinding.ItemTemperaturaBinding
import com.example.monitor_iot.http.models.Temperatura
import java.text.SimpleDateFormat
import java.util.*

class TemperaturaAdapter(val lista: List<Temperatura>):RecyclerView.Adapter<TemperaturaAdapter.ViewHolderTemperatura>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTemperatura {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_temperatura, parent, false)

        return ViewHolderTemperatura(view)
    }

    override fun onBindViewHolder(holder: ViewHolderTemperatura, position: Int) {
        val item = lista[position]
        holder.bin(item)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    class ViewHolderTemperatura(ItemView: View): RecyclerView.ViewHolder(ItemView){
        var binding:ItemTemperaturaBinding = ItemTemperaturaBinding.bind(ItemView)

        fun bin(item: Temperatura){
            binding.lblDato.text = "${item.dato} C°"
            binding.lblCreatedAt.text = item.createdAt
        }
    }
}