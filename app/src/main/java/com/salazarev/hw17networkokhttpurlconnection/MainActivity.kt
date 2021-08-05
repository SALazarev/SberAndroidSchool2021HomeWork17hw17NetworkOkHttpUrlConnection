package com.salazarev.hw17networkokhttpurlconnection

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var getAllNoteBtn: Button
    private lateinit var addNoteBtn: Button
    private lateinit var responseTv: TextView

    private val compositeDisposable = CompositeDisposable()
    private var clientApi: ClientApi = OkHttpApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllNoteBtn = findViewById(R.id.get_all_note)
        addNoteBtn = findViewById(R.id.add_note)
        responseTv = findViewById(R.id.tv_response)

        getAllNoteBtn.setOnClickListener {

            val disposable = Single.fromCallable {
                return@fromCallable clientApi.productsList()
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text: String, throwable: Throwable? ->
                    if (throwable == null) responseTv.text = text
                }
            compositeDisposable.add(disposable)
        }

        addNoteBtn.setOnClickListener {
            val disposable = Single.fromCallable {
                return@fromCallable clientApi
                    .productAdd("Test product", 13.5, "Test description")
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text: String, throwable: Throwable? ->
                    if (throwable == null) responseTv.text = text
                }
            compositeDisposable.add(disposable)
        }

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}