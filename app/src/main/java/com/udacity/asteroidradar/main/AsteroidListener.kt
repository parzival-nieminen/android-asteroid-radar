package com.udacity.asteroidradar.main

import com.udacity.asteroidradar.domain.Asteroid

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
