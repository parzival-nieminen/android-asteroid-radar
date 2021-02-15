package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AsteroidRadarDao {

    @Insert
    suspend fun insert(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_table WHERE id = :id")
    suspend fun selectById(id: Long): Asteroid?

    @Query("SELECT * FROM asteroid_table")
    fun selectAll(): LiveData<List<Asteroid>>?

    @Query("DELETE FROM asteroid_table")
    suspend fun deleteAll()
}