package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidRadarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroidDto: AsteroidDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidDto)

    @Insert()
    fun insert(imageDto: ImageDto)

    @Query("SELECT * FROM asteroid_table WHERE id = :id")
    fun selectById(id: Long): AsteroidDto?

    @Query("SELECT * FROM asteroid_table ORDER BY id DESC")
    fun selectAll(): LiveData<List<AsteroidDto>>

    @Query("DELETE FROM asteroid_table")
    fun deleteAll()

    @Query("SELECT * FROM image_table ORDER BY id DESC LIMIT 1")
    fun selectCurrentImage(): LiveData<ImageDto>
}
