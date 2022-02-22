package com.example.weatherapp.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.remote.model.forecast.WeatherList
import com.example.weatherapp.databinding.ItemWeatherListBinding
import java.text.SimpleDateFormat
import java.util.*

class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    var items: List<WeatherList>
        get() = diffUtil.currentList
        set(value) {
            diffUtil.submitList(value)
        }

    inner class ViewHolder(private val binding: ItemWeatherListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pos: Int, item: WeatherList) {
            binding.apply {
                val date = Date(item.dt!!)
                val format = SimpleDateFormat("dd MMM yyyy")
                tvTitle.text = format.format(date)
                tvTemp.text = "${item.main?.temp} ℃ - ${item.weather?.get(0)?.main}"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherListAdapter.ViewHolder {
        val binding = ItemWeatherListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherListAdapter.ViewHolder, position: Int) {
        holder.onBind(position, items[position])
    }

    override fun getItemCount(): Int = items.size

    private val diffUtil = AsyncListDiffer(this, DiffCallback)

    object DiffCallback : DiffUtil.ItemCallback<WeatherList>() {
        override fun areItemsTheSame(
            oldModel: WeatherList,
            newModel: WeatherList
        ) = oldModel.dt == newModel.dt

        override fun areContentsTheSame(
            oldModel: WeatherList,
            newModel: WeatherList
        ) = oldModel.dt == newModel.dt
    }
}