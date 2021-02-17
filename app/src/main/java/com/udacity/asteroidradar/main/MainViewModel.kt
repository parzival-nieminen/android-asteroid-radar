package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.database.AsteroidRadarDao
import timber.log.Timber

class MainViewModel(asteroidRadarDao: AsteroidRadarDao, application: Application) :
    AndroidViewModel(application) {

    private val _asteroidList = asteroidRadarDao.selectAll()

    private val _navigateDetailAsteroid = MutableLiveData<Long>()

    val navigateDetailAsteroid
        get() = _navigateDetailAsteroid

    fun onNavigateDetailAsteroidClick(id: Long) {
        _navigateDetailAsteroid.value = id
    }

    init {
        Timber.i("init ViewModel")
    }

}
