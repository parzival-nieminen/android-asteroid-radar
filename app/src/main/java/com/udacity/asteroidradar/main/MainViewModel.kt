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

class MainViewModel(private val database: AsteroidRadarDao, application: Application) :
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

        var one = Asteroid(1, "AAA", "AAA", .1, .1, .1, .1, true)
        var two = Asteroid(2, "BBB", "BBB", .2, .2, .2, .2, true)
        var tree = Asteroid(3, "CCC", "CCC", .3, .3, .3, .3, true)
        _asteroidList.value = mutableListOf(one, two, tree, one, two, tree, one, two, tree)

        viewModelScope.launch {
            database.insert(one.mapToModel())
            database.insert(two.mapToModel())
            database.insert(tree.mapToModel())
            //_asteroidList.value = database.selectAll()?.value?.mapToModel()
        }
    }
}


