package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.network.ApiHelper

@Entity(tableName = "image_table")
data class ImageTable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val url: String,
    val mediaType: String,
    val title: String,
    val day: String = ApiHelper.endDate()
)
