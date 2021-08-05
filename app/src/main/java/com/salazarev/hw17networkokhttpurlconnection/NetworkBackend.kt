package com.salazarev.hw17networkokhttpurlconnection

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class NetworkBackend {
    companion object {
        private var clientApi: ClientApi = OkHttpApi()

        val productsList: Single<String> = Single.fromCallable {
            return@fromCallable clientApi.productsList()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val productAdd: Single<String> = Single.fromCallable {
            return@fromCallable clientApi
                .productAdd("Test product", 13.5, "Test description")
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}