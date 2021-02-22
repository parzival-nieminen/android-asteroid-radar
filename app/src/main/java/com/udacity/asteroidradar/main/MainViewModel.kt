package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiHelper
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidRadarDao
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

class MainViewModel(private val database: AsteroidRadarDao, application: Application) :
    AndroidViewModel(application) {

    private var _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

    private var _image = MutableLiveData<PictureOfDay>()
    val image: LiveData<PictureOfDay> = _image

    private val _navigateToDetailAsteroid = MutableLiveData<Asteroid?>()

    val navigateToDetailAsteroid
        get() = _navigateToDetailAsteroid

    fun onNavigateToDetailAsteroidClick(asteroid: Asteroid) {
        _navigateToDetailAsteroid.value = asteroid
    }

    fun onNavigateToDetailAsteroidClick() {
        _navigateToDetailAsteroid.value = null
    }

    init {
        Timber.i("init ViewModel")
        viewModelScope.launch {
            _image.value = NasaApi.SERVICE.getImageOfTheDay(API_KEY)
        }

        viewModelScope.launch {
            val result = NasaApi.SERVICE.getNearEarthObject(
                ApiHelper.startDate(),
                ApiHelper.endDate(),
                API_KEY
            )
            val dataList = parseAsteroidsJsonResult(JSONObject(result))
            _asteroidList.value = dataList
        }
    }
}



