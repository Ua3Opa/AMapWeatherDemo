package com.amapweather.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amapweather.adapter.CityAdapter
import com.amapweather.adapter.WeatherAdapter
import com.amapweather.databinding.ActivityMainBinding
import com.amapweather.entitys.CityData
import com.amapweather.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY: String = "dd300523c4d9a4c8ce3a05bd04965507"
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var cityData = ArrayList<CityData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewModel = mainViewModel

        initCityData();

        initAdapter();

        mainViewModel.forecastData.observe(this, {
            binding.rvWeather.adapter!!.notifyDataSetChanged()
        })
    }

    private fun initAdapter() {
        binding.rvCitys.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCitys.adapter = CityAdapter(this, cityData, mainViewModel::queryWeather)

        binding.rvWeather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvWeather.adapter = WeatherAdapter(this, mainViewModel, mainViewModel::queryWeather)
    }

    private fun initCityData() {
        cityData.add(CityData("北京市", "110000"))
        cityData.add(CityData("上海市", "310000"))
        cityData.add(CityData("广州市", "440100"))
        cityData.add(CityData("深圳市", "440300"))
        cityData.add(CityData("苏州市", "320500"))
        cityData.add(CityData("沈阳市", "210100"))
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.queryWeather(cityData.get(0))
    }

}