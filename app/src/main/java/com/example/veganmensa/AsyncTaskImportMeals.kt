package com.example.veganmensa

import android.os.AsyncTask
import android.widget.ListView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class AsyncTaskImportMeals(private val activity: MainActivity, private val dayList: ListView) : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg url: String): String {
        var text = ""
        url.forEach {
            var connection = URL(it).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text += connection.inputStream.use {
                    it.reader().use { reader ->
                        reader.readText()
                    }
                }
            } finally {
                connection.disconnect()
            }
        }
        return text
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        handleJson(result)
    }

    private fun handleJson(jsonString: String?) {
        val jsonArray = JSONArray(jsonString)

        val days = ArrayList<Day>()

        for (i in 0 until jsonArray.length()) {
            val dayJsonObject = jsonArray.getJSONObject(i)

            val meals = ArrayList<Meal>()
            val sideDishes = ArrayList<Meal>()

            val mealJsonArray = dayJsonObject.getJSONArray("meals")

            for (j in 0 until mealJsonArray.length()) {
                val mealJsonObject = mealJsonArray.getJSONObject(j)

                val allergenics = ArrayList<String>()
                val notes = mealJsonObject.getJSONArray("notes")
                for (k in 0 until notes.length()) allergenics.add(notes.getString(k))

                if (mealJsonObject.getString("category").contains("beilage")) {
                    sideDishes.add(
                        Meal(
                            -1,
                            mealJsonObject.getString("name"),
                            mealJsonObject.getString("category"),
                            0.0,
                            allergenics
                        )
                    )
                }

                if (!allergenics.contains("vegan")) continue

                if (mealJsonObject.getJSONObject("prices").getString("students") == "null") continue

                meals.add(
                    Meal(
                        mealJsonObject.getInt("id"),
                        mealJsonObject.getString("name"),
                        mealJsonObject.getString("category"),
                        mealJsonObject.getJSONObject("prices").getDouble("students"),
                        allergenics
                    )
                )
            }

            if (meals.size == 0) continue

            days.add(
                Day(
                    dayJsonObject.getString("date"), meals, sideDishes
                )
            )
        }

        dayList.adapter = DayListAdapter(activity, days)
    }
}