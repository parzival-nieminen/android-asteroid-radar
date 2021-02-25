package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidRadarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroidTable: AsteroidTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageTable: ImageTable)

    @Query("SELECT * FROM asteroid_table WHERE id = :id")
    fun selectById(id: Long): AsteroidTable?

    @Query("SELECT * FROM asteroid_table ORDER BY id DESC")
    fun selectAll(): LiveData<List<AsteroidTable>>

    @Query("DELETE FROM asteroid_table")
    fun deleteAll()

    @Query("SELECT * FROM image_table ORDER BY id DESC LIMIT 1")
    fun selectCurrentImage(): LiveData<ImageTable>
}
