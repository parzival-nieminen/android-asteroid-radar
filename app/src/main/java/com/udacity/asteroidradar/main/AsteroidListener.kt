package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.database.Asteroid

class AsteroidListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid.id)
}
