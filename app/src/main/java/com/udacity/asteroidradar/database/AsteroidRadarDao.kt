package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidRadarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageTable: ImageTable)

    @Query("SELECT count(*) FROM asteroid_table WHERE date(day) = date(:today)")
    fun selectHasCurrentAsteroids(today: String): Int

    @Query("SELECT * FROM asteroid_table ORDER BY close_approach_date DESC")
    fun selectAll(): LiveData<List<AsteroidTable>>

    @Query("SELECT count(*) FROM image_table WHERE date(day) = date(:today)")
    fun selectHasCurrentImage(today: String): Int

    @Query("SELECT * FROM image_table ORDER BY day DESC LIMIT 1")
    fun selectCurrentImage(): LiveData<ImageTable>

    @Query("DELETE FROM asteroid_table WHERE date(close_approach_date) < date(:today)")
    fun deleteAllOldAsteroids(today: String)

    @Query("DELETE FROM image_table WHERE date(day) < date(:today)")
    fun deleteAllOldImages(today: String)
}
