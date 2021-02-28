package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.util.Constants.API_KEY
import okhttp3.Interceptor

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request();
        val originalHttpUrl = original.url;

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build();

        val requestBuilder = original.newBuilder()
            .url(url);

        val request = requestBuilder.build();
        return chain.proceed(request);
    }
}
