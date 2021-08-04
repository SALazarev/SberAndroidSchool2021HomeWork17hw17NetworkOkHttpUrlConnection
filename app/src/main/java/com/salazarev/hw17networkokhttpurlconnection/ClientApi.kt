package com.salazarev.hw17networkokhttpurlconnection

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

abstract class ClientApi {
    companion object {
        const val REQUEST_URL = "https://fakestoreapi.com/products"
    }

    abstract fun productAdd(title: String, price: Double, description: String): String

    abstract fun productsList(): String

    fun getRequestBody(title: String, price: Double, description: String): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(Product::class.java)
        return adapter.toJson(Product(title, price, description))
    }

}

