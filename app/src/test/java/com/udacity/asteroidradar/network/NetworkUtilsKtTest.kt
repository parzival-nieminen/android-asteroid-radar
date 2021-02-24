package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.TestHelper
import org.junit.Assert
import org.junit.Test

class NetworkUtilsKtTest {

    @Test
    fun getImageOfTheDay() {
        val json = TestHelper.getJsonResponse("NasaApodJsonResponse_one.json")
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<PictureOfDay> = moshi.adapter(PictureOfDay::class.java)
        val result = jsonAdapter.fromJson(json)

        Assert.assertEquals("image", result?.mediaType)
        Assert.assertEquals("NGC 2244: A Star Cluster in the Rosette Nebula", result?.title)
        Assert.assertEquals(
            "https://apod.nasa.gov/apod/image/2102/rosette_goldman_960.jpg",
            result?.url
        )
    }
}
