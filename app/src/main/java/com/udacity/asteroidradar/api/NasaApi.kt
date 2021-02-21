package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// info: https://api.nasa.gov
// example: https://api.nasa.gov/neo/rest/v1/feed?start_date=2021-02-20&end_date=2021-02-21&api_key=DEMO_KEY
// example: https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=DEMO_KEY
private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"
private const val API_KEY = "DEMO_KEY"

private fun client(): OkHttpClient {

    val apiKeyParameter = ApiKeyInterceptor()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(apiKeyParameter)
        .build()
}

private fun moshiConverter(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

/*
 Parameters API Key Interceptor
 Based on the blog post: "Retrofit 2 — How to Add Query Parameters to Every Request"
 https://bit.ly/2NOsUpf 21.02.2021
 */
class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}

object NasaApi {
    interface NasaApiService {
        @GET("feed")
        fun getNearEarthObject(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String
        )
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshiConverter()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val service = retrofit.create(NasaApiService::class.java)

}
