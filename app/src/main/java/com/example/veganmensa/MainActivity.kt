package com.example.veganmensa

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://openmensa.org/api/v2/canteens/187/days/%222023-02-21%22/meals"

        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String
            var connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text =
                    connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }

            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(jsonString: String?) {
        val jsonArray = JSONArray(jsonString)

        val meals = ArrayList<Meal>()
        val sideDishes = ArrayList<Meal>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val allergenics = ArrayList<String>()
            val notes = jsonObject.getJSONArray("notes")
            for (j in 0 until notes.length())
                allergenics.add(notes.getString(j))

            if (jsonObject.getString("category").contains("beilage")){
                sideDishes.add(
                    Meal(
                        -1,
                        jsonObject.getString("name"),
                        jsonObject.getString("category"),
                        0.0,
                        allergenics
                    )
                )
            }

            if (!allergenics.contains("vegan"))
                continue

            if (jsonObject.getJSONObject("prices").getString("students") == "null")
                continue

            meals.add(
                Meal(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("category"),
                    jsonObject.getJSONObject("prices").getDouble("students"),
                    allergenics
                )
            )
        }

        findViewById<ListView>(R.id.meal_list).adapter = MealListAdapter(this, meals)
        findViewById<ListView>(R.id.side_dish_list).adapter = SideDishListAdapter(this, sideDishes)
    }
}