package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val url: String,
    val mediaType: String,
    val title: String
)