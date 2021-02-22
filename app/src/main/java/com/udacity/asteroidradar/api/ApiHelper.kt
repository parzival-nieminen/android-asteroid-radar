package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import java.text.SimpleDateFormat
import java.util.*

object ApiHelper {

    fun startDate(): String {
        val startDate = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return startDate.format(Calendar.getInstance().time)
    }

    fun endDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, DEFAULT_END_DATE_DAYS)
        val currentTime = calendar.time
        val endDate = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
        return endDate.format(currentTime)
    }
}