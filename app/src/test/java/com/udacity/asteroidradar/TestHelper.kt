package com.udacity.asteroidradar
/*
    blog post: https://bit.ly/3qFi1EU
 */
object TestHelper {
    private fun getInputStreamFromResource(fileName: String) =
        javaClass.classLoader?.getResourceAsStream(fileName)

    fun getJsonResponse(fileName: String): String {
        return getInputStreamFromResource(fileName)?.bufferedReader()
            .use { bufferReader -> bufferReader?.readText() } ?: ""
    }
}
