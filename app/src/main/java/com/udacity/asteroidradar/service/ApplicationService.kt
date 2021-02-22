package com.udacity.asteroidradar.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiHelper.endDate
import com.udacity.asteroidradar.api.ApiHelper.startDate
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.mapToDto
import com.udacity.asteroidradar.database.mapToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class ApplicationService(private val database: AsteroidRadarDatabase) {

    val currentImage: LiveData<PictureOfDay>
        get() = Transformations.map(database.asteroidRadarDao.selectCurrentImage()) { it?.mapToModel() }

    val asteroidList: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidRadarDao.selectAll()) { it?.mapToModel() }

    suspend fun insertAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val response = NasaApi.SERVICE.getNearEarthObject(startDate(), endDate(), API_KEY)
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
            } finally {
                this.coroutineContext.cancelChildren()
            }
        }
    }

    suspend fun insertImage() {
        withContext(Dispatchers.IO) {
            try {
                val response = NasaApi.SERVICE.getImageOfTheDay(API_KEY)

                if (response.isSuccessful) {
                    response.body()?.let {
                        database.asteroidRadarDao.insert(it.mapToDto())
                    }
                } else {
                    Timber.e("insertAsteroids call failed: ${response.errorBody().toString()}")
                }

            } catch (error: Exception) {
                Timber.e("message: ${error.message} class: ${error.javaClass} ")
            } finally {
                this.coroutineContext.cancelChildren()
            }
        }
    }
}