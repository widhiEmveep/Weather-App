package com.example.weatherapp.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.local.entity.FavoriteCity
import com.example.weatherapp.databinding.ItemFavoriteBinding

class FavoriteCityAdapter : RecyclerView.Adapter<FavoriteCityAdapter.ViewHolder>() {

    var items: List<FavoriteCity>
        get() = diffUtil.currentList
        set(value) {
            diffUtil.submitList(value)
        }

    inner class ViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pos: Int, item: FavoriteCity) {
            binding.apply {
                binding.tvTitle.text = item.cityName

                itemFavorite.setOnClickListener {
                    onItemClickListener.invoke(item.cityName)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteCityAdapter.ViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteCityAdapter.ViewHolder, position: Int) {
        holder.onBind(position, items[position])
    }

    override fun getItemCount(): Int = items.size

    private val diffUtil = AsyncListDiffer(this, DiffCallback)

    object DiffCallback : DiffUtil.ItemCallback<FavoriteCity>() {
        override fun areItemsTheSame(
            oldModel: FavoriteCity,
            newModel: FavoriteCity
        ) = oldModel.id == newModel.id

        override fun areContentsTheSame(
            oldModel: FavoriteCity,
            newModel: FavoriteCity
        ) = oldModel.id == newModel.id
    }

    var onItemClickListener: (String) -> Unit = { _ -> }

    fun onItemClickListenerCallback(callback: (cityName: String) -> Unit) {
        onItemClickListener = callback
    }
}