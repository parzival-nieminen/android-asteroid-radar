package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidRadarDao
import com.udacity.asteroidradar.database.mapToModel
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(asteroidRadarDao: AsteroidRadarDao, application: Application) :
    AndroidViewModel(application) {

    private var _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

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
        _asteroidList.value = mutableListOf()
        viewModelScope.launch {
            _asteroidList.value = asteroidRadarDao.selectAll()?.value?.mapToModel()
        }
    }

}

