package com.salazarev.hw17networkokhttpurlconnection

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

abstract class ClientApi {
    companion object {
        const val REQUEST_URL = "https://jsonplaceholder.typicode.com/posts"
    }

    abstract fun postsAdd(userId: Long, title: String, body: String): String

    abstract fun postsList(): String

    fun getRequestBody(userId: Long, title: String, body: String): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(Post::class.java)
        return adapter.toJson(Post(userId, title, body))
    }

}

