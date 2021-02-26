package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay

fun AsteroidTable.mapToModel(): Asteroid {
    return Asteroid(
        id = id,
        codename = codename,
        closeApproachDate = closeApproachDate,
        absoluteMagnitude = absoluteMagnitude,
        estimatedDiameter = estimatedDiameter,
        relativeVelocity = relativeVelocity,
        distanceFromEarth = distanceFromEarth,
        isPotentiallyHazardous = isPotentiallyHazardous
    )
}

fun List<AsteroidTable>.mapToModel(): List<Asteroid> {
    return map { it.mapToModel() }
}

fun List<Asteroid>.mapToTable(): Array<AsteroidTable> {
    return map {
        AsteroidTable(
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

fun PictureOfDay.mapToTable(): ImageTable {
    return ImageTable(
        mediaType = mediaType,
        title = title,
        url = url
    )
}

fun ImageTable.mapToModel(): PictureOfDay {
    return PictureOfDay(
        mediaType = mediaType,
        title = title,
        url = url
    )
}
