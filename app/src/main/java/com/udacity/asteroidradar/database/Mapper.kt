package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Asteroid

fun com.udacity.asteroidradar.database.Asteroid.mapToModel(): Asteroid {
    return Asteroid(
        id = this.id,
        codename = this.codename,
        closeApproachDate = this.closeApproachDate,
        absoluteMagnitude = this.absoluteMagnitude,
        estimatedDiameter = this.estimatedDiameter,
        relativeVelocity = this.relativeVelocity,
        distanceFromEarth = this.distanceFromEarth,
        isPotentiallyHazardous = this.isPotentiallyHazardous
    )
}

fun List<com.udacity.asteroidradar.database.Asteroid>.mapToModel(): List<Asteroid> {
    return map { it.mapToModel() }
}
