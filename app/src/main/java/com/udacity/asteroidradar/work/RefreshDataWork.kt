package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.repository.AppRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidRadarDatabase.getInstance(applicationContext)
        val repository = AppRepository(database)

        return try {
            Timber.i("doWork is running")
            repository.refreshAsteroids()
            repository.refreshImage()
            repository.deleteAllOldData()
            Result.success()

        } catch (error: HttpException) {
            Timber.e("Http code: ${error.code()} message : ${error.message()}")
            Result.retry()
        }
    }
}
