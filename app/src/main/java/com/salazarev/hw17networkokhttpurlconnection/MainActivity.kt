package com.salazarev.hw17networkokhttpurlconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainActivity : AppCompatActivity() {

    private lateinit var getAllNoteBtn: Button
    private lateinit var addNoteBtn: Button
    private lateinit var responseTv: TextView

    private var clientApi: ClientApi = OkHttpApi()
    private val executor = Executors.newSingleThreadExecutor()
    private var lastTask: Future<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllNoteBtn = findViewById(R.id.get_all_note)
        addNoteBtn = findViewById(R.id.add_note)
        responseTv = findViewById(R.id.tv_response)

        getAllNoteBtn.setOnClickListener {
            submitRequest {
                val text = clientApi.postsList()
                responseTv.post { responseTv.text = text }
            }
        }
        addNoteBtn.setOnClickListener {
            submitRequest {
                val text = clientApi.postsAdd("Test product", 13.5, "Test description")
                responseTv.post { responseTv.text = text }
            }
        }
    }

    private fun submitRequest(runnable: Runnable) {
        if (lastTask != null) lastTask?.cancel(true)
        lastTask = executor.submit(runnable)
    }

    override fun onStop() {
        super.onStop()
        executor.shutdown()
        lastTask = null
    }
}