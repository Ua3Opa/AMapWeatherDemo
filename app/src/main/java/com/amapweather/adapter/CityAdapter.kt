package com.amapweather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amapweather.databinding.ItemCityBinding
import com.amapweather.entitys.CityData

class CityAdapter(var context: Context, var cityData: List<CityData>, var listener: (CityData) -> Unit) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCityBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return cityData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.btnCity.text = cityData.get(position).cityName
        holder.binding.btnCity.setOnClickListener({
            listener.invoke(cityData.get(position))
        })
    }

    class ViewHolder(var binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}