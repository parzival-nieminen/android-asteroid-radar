package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidTable::class, ImageTable::class], version = 1, exportSchema = false)
abstract class AsteroidRadarDatabase : RoomDatabase() {

    abstract val asteroidRadarDao: AsteroidRadarDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidRadarDatabase? = null

        fun getInstance(context: Context): AsteroidRadarDatabase {

            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidRadarDatabase::class.java,
                        "asteroid_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE as AsteroidRadarDatabase
            }
        }
    }
}
