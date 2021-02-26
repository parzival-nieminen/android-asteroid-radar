package com.udacity.asteroidradar.database

import androidx.room.TypeConverter
import com.udacity.asteroidradar.util.Constants
import java.text.SimpleDateFormat
import java.util.*

/*
    converter for db type
    info https://bit.ly/2O30VlS
 */
class Converter {
    private val format = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }
}
