package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Asteroid

fun AsteroidDto.mapToModel(): Asteroid {
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

fun List<AsteroidDto>.mapToModel(): List<Asteroid> {
    return map { it.mapToModel() }
}

fun Asteroid.mapToDto(): AsteroidDto {
    return AsteroidDto(
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

fun List<Asteroid>.mapToDto(): List<AsteroidDto> {
    return map { it.mapToDto() }
}
