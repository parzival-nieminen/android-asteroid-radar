package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidRadarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroidDto: AsteroidDto)

    @Query("SELECT * FROM asteroid_table WHERE id = :id")
    suspend fun selectById(id: Long): AsteroidDto?

    @Query("SELECT * FROM asteroid_table")
    fun selectAll(): LiveData<List<AsteroidDto>>?

    @Query("DELETE FROM asteroid_table")
    suspend fun deleteAll()
}
