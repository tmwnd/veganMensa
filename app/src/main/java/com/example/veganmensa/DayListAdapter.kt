package com.example.veganmensa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.color.MaterialColors

class DayListAdapter(val context: Context, val days: ArrayList<Day>) : BaseAdapter() {
    override fun getCount(): Int {
        return days.size
    }

    override fun getItem(i: Int): Any {
        return days[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.day, parent, false)
        val day = getItem(i) as Day

        view.findViewById<TextView>(R.id.date).text = day.toString()

        if (day.isToday())
            (view.findViewById<TextView>(R.id.date).parent as LinearLayout).setBackgroundColor(
                MaterialColors.getColor(
                    view,
                    com.google.android.material.R.attr.colorPrimaryVariant
                )
            )

        (view.findViewById<TextView>(R.id.date).parent as LinearLayout).setOnClickListener {
            val day = it.parent as LinearLayout

            val meals = day.findViewById<LinearLayout>(R.id.meal_list)
            val sideDishes = day.findViewById<LinearLayout>(R.id.side_dish_list)
            val favs = day.findViewById<LinearLayout>(R.id.fav_list)

            when (meals.visibility) {
                View.VISIBLE -> {
                    meals.visibility = View.GONE
                    sideDishes.visibility = View.GONE

                    favs.visibility = View.VISIBLE
                }
                View.GONE -> {
                    meals.visibility = View.VISIBLE
                    sideDishes.visibility = View.VISIBLE

                    favs.visibility = View.GONE
                }
            }
        }

        var mealListAdapter = MealListAdapter(context, day.meals)
        for (i in 0 until mealListAdapter.count)
            view.findViewById<LinearLayout>(R.id.meal_list)
                .addView(mealListAdapter.getView(i, convertView, parent))
        var sideDishListAdapter = SideDishListAdapter(context, day.sideDishes)
        for (i in 0 until sideDishListAdapter.count)
            view.findViewById<LinearLayout>(R.id.side_dish_list)
                .addView(sideDishListAdapter.getView(i, convertView, parent))

        return view
    }
}