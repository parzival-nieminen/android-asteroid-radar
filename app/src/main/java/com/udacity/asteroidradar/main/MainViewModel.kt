package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.repository.AppRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidRadarDatabase.getInstance(application)
    private val appRepository = AppRepository(database)
    private val _navigateToDetailAsteroid = MutableLiveData<Asteroid?>()

    val asteroidList: LiveData<List<Asteroid>>
        get() = appRepository.asteroidList

    val image: LiveData<PictureOfDay>
        get() = appRepository.currentImage

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
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if (activeNetwork?.isConnected == true) {
            viewModelScope.launch {
                appRepository.refreshImage()
                appRepository.refreshAsteroids()
            }
        }
    }
}
