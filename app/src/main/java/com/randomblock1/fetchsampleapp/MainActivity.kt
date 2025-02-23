package com.randomblock1.fetchsampleapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

const val DATA_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
val HTTP_CLIENT = OkHttpClient();

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            val json = getData()
            val jsonArray = JSONArray(json)
        } catch (e: Exception) {
            // Handle exception
        }

    }

    fun getData(): String {
        val request = Request.Builder().url(DATA_URL).build();
        val response = HTTP_CLIENT.newCall(request).execute();
        return response.body.string();
    }
}