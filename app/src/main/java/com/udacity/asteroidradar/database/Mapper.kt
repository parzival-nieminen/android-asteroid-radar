package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

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

fun List<Asteroid>.mapToDto(): Array<AsteroidDto> {
    return map {
        AsteroidDto(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun PictureOfDay.mapToDto(): ImageDto {
    return ImageDto(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}

fun ImageDto.mapToModel(): PictureOfDay {
    return PictureOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}