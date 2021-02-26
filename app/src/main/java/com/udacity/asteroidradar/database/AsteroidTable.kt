package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.udacity.asteroidradar.network.ApiHelper

@Entity(tableName = "asteroid_table")
data class AsteroidTable(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var codename: String,
    @ColumnInfo(name = "close_approach_date")
    @TypeConverters(Converter::class)
    val closeApproachDate: String,
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double,
    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double,
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,
    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double,
    @ColumnInfo(name = "is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean,
    @TypeConverters(Converter::class)
    val day: String = ApiHelper.endDate()
)
