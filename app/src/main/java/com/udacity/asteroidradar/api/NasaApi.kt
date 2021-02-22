package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.HttpClient.getNasaService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// info: https://api.nasa.gov
// endpoint: neo
// url: https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=DEMO_KEY
// example: https://api.nasa.gov/neo/rest/v1/feed?start_date=2021-02-21&end_date=2021-02-21&api_key=DEMO_KEY
// endpoint: apod
// url: https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY

object HttpClient {

    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    fun getNasaService(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshiConverter()))
                .build()
        }
        return retrofit as Retrofit
    }

    private fun logger(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging;
    }

    private fun moshiConverter(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun client(): OkHttpClient {
        if (client == null) {
            client = OkHttpClient.Builder()
                .addInterceptor(logger())
                .build()
        }
        return client as OkHttpClient;
    }
}

object NasaApi {
    interface NasaService {
        @GET("neo/rest/v1/feed")
        suspend fun getNearEarthObject(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String,
        ): Response<String>

        @GET("planetary/apod")
        suspend fun getImageOfTheDay(@Query("api_key") apiKey: String): Response<PictureOfDay>
    }

    val SERVICE: NasaService by lazy { getNasaService().create(NasaService::class.java) }
}
