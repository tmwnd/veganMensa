package com.example.veganmensa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val URL = "https://openmensa.org/api/v2/canteens/" + getString(R.string.mensa_id) + "/meals"
        AsyncTaskImportMeals(this, findViewById(R.id.day_list)).execute(URL)
    }
}