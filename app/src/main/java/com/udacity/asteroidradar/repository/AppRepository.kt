package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.ApiHelper.startDate
import com.udacity.asteroidradar.network.ApiHelper.endDate
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.mapToDto
import com.udacity.asteroidradar.database.mapToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AppRepository(private val database: AsteroidRadarDatabase) {

    val currentImage: LiveData<PictureOfDay>
        get() = Transformations.map(database.asteroidRadarDao.selectCurrentImage()) { it?.mapToModel() }

    val asteroidList: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidRadarDao.selectAll()) { it?.mapToModel() }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val response = NasaApi.SERVICE.getNearEarthObject(startDate(), endDate())
                if (response.isSuccessful) {
                    response.body()?.let {
                        val data = parseAsteroidsJsonResult(JSONObject(it))
                        database.asteroidRadarDao.insertAll(*data.mapToDto())
                    }
                } else {
                    Timber.e("insertAsteroids call failed: ${response.errorBody().toString()}")
                }
            } catch (error: Exception) {
                Timber.e("message: ${error.message} class: ${error.javaClass} ")
            }
        }
    }

    suspend fun refreshImage() {
        withContext(Dispatchers.IO) {
            try {
                val response = NasaApi.SERVICE.getImageOfTheDay()
                if (response.isSuccessful) {
                    response.body()?.let {
                        database.asteroidRadarDao.insert(it.mapToDto())
                    }
                } else {
                    Timber.e("insertAsteroids call failed: ${response.errorBody().toString()}")
                }

            } catch (error: Exception) {
                Timber.e("message: ${error.message} class: ${error.javaClass} ")
            }
        }
    }
}
