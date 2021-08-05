package com.salazarev.hw17networkokhttpurlconnection

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private lateinit var getAllNoteBtn: Button
    private lateinit var addNoteBtn: Button
    private lateinit var responseTv: TextView

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllNoteBtn = findViewById(R.id.get_all_note)
        addNoteBtn = findViewById(R.id.add_note)
        responseTv = findViewById(R.id.tv_response)

        getAllNoteBtn.setOnClickListener {
            val disposable= NetworkBackend.productsList
                .subscribe { text: String, throwable: Throwable? ->
                    if (throwable == null) responseTv.text = text
                }
            compositeDisposable.add(disposable)
        }

        addNoteBtn.setOnClickListener {
            val disposable= NetworkBackend.productAdd
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