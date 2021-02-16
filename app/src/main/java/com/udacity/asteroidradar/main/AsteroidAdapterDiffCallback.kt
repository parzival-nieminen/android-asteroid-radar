package com.udacity.asteroidradar.main

import androidx.recyclerview.widget.DiffUtil
import com.udacity.asteroidradar.database.Asteroid

class AsteroidAdapterDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}
