package com.salazarev.hw17networkokhttpurlconnection

import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpApi : ClientApi() {

    private var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .writeTimeout(5000, TimeUnit.MILLISECONDS)
        .addNetworkInterceptor(HttpLoggingInterceptor())
        .build()

    override fun postsAdd(title: String, price: Double, description: String): String {
        val request = Request.Builder()
            .url(REQUEST_URL.toHttpUrl())
            .addHeader("Content-Type", "application/json")
            .post(getRequestBody(title, price, description).toRequestBody())
            .build()
        return handleResponse(request)
    }

    override fun postsList(): String {
        val request = Request.Builder()
            .url(REQUEST_URL.toHttpUrl())
            .build()
        return handleResponse(request)
    }

    private fun handleResponse(request: Request): String {
        val response: Response = okHttpClient.newCall(request).execute()
        response.use {
            return try {
                if (response.isSuccessful) {
                    val body = response.body
                    body?.string() ?: "No content"
                } else "Response code: ${response.code}"
            } catch (e: Exception) {
                e.toString()
            }
        }
    }

}