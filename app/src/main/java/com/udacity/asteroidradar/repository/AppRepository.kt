package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.mapToModel
import com.udacity.asteroidradar.database.mapToTable
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.ApiHelper.endDate
import com.udacity.asteroidradar.network.ApiHelper.startDate
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.sql.SQLException

class AppRepository(private val database: AsteroidRadarDatabase) {

    val currentImage: LiveData<PictureOfDay> =
        Transformations.map(database.asteroidRadarDao.selectCurrentImage()) { it?.mapToModel() }

    val asteroidList: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidRadarDao.selectAll()) { it?.mapToModel() }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val hasCurrentAsteroidsInDatabase =
                    database.asteroidRadarDao.selectHasCurrentAsteroids(endDate())
                if (hasCurrentAsteroidsInDatabase == 0) {
                    Timber.i("Fetching Asteroids")
                    val response = NasaApi.SERVICE.getNearEarthObject(startDate(), endDate())
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val data = parseAsteroidsJsonResult(JSONObject(it))
                            database.asteroidRadarDao.insertAll(*data.mapToTable())
                        }
                    }
                }
            } catch (error: HttpException) {
                Timber.e("Http code: ${error.code()} message : ${error.message()}")
            } catch (error: SQLException) {
                Timber.e(
                    "SQL error code: ${error.errorCode} " +
                            "state : ${error.sqlState} message : ${error.message}"
                )
            }
        }
    }

    suspend fun refreshImage() {
        withContext(Dispatchers.IO) {
            try {
                val hasCurrentImageInDatabase =
                    database.asteroidRadarDao.selectHasCurrentImage(endDate())
                if (hasCurrentImageInDatabase == 0) {
                    Timber.i("Fetching Image of the day")
                    val response = NasaApi.SERVICE.getImageOfTheDay()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            database.asteroidRadarDao.insert(it.mapToTable())
                        }
                    }
                }
            } catch (error: HttpException) {
                Timber.e("Http code: ${error.code()} message : ${error.message()}")
            } catch (error: SQLException) {
                Timber.e(
                    "SQL error code: ${error.errorCode} " +
                            "state : ${error.sqlState} message : ${error.message}"
                )
            }
        }
    }

    suspend fun deleteAllOldData() {
        withContext(Dispatchers.IO) {
            try {
                Timber.i("delete all old Data")
                val today = endDate()
                database.asteroidRadarDao.deleteAllOldAsteroids(today)
                database.asteroidRadarDao.deleteAllOldImages(today)
            } catch (error: SQLException) {
                Timber.e(
                    "SQL error code: ${error.errorCode} " +
                            "state : ${error.sqlState} message : ${error.message}"
                )
            }
        }
    }
}
