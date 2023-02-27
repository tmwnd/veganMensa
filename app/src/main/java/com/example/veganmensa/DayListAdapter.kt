package com.example.veganmensa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.color.MaterialColors

class DayListAdapter(private val context: Context, private val days: ArrayList<Day>) :
    BaseAdapter() {
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

        (view.findViewById<TextView>(R.id.date).parent as LinearLayout).setBackgroundColor(
            MaterialColors.getColor(
                view, if (day.isToday()) R.attr.colorToday else R.attr.colorDay
            )
        )

        var mealListAdapter = MealListAdapter(context, day.meals)
        for (i in 0 until mealListAdapter.count) view.findViewById<LinearLayout>(R.id.meal_list)
            .addView(mealListAdapter.getView(i, convertView, parent))
        var sideDishListAdapter = SideDishListAdapter(context, day.sideDishes)
        for (i in 0 until sideDishListAdapter.count) view.findViewById<LinearLayout>(R.id.side_dish_list)
            .addView(sideDishListAdapter.getView(i, convertView, parent))

        (view.findViewById<TextView>(R.id.date).parent as LinearLayout).setOnClickListener {
            when (day.isVisible) {
                false -> showDay(it.parent as LinearLayout, day)
                true -> hideDay(it.parent as LinearLayout, day)
            }
        }

        if (day.isVisible) showDay(view, day)

        return view
    }

    fun showDay(view: View, day: Day) {
        var i = 0
        (view.parent as ListView).children.forEach { day ->
            hideDay(day, getItem(i) as Day)
            i += 1
        }

        day.isVisible = true

        view.findViewById<LinearLayout>(R.id.meal_list).children.forEach { meal ->
            meal.visibility = View.VISIBLE
            meal.findViewById<CheckBox>(R.id.fav).isEnabled = true
        }
        view.findViewById<LinearLayout>(R.id.side_dish_list).visibility = View.VISIBLE
    }

    fun hideDay(view: View, day: Day) {
        day.isVisible = false

        view.findViewById<LinearLayout>(R.id.meal_list).children.forEach { meal ->
            meal.visibility =
                if (meal.findViewById<CheckBox>(R.id.fav).isChecked) View.VISIBLE else View.GONE
            meal.findViewById<CheckBox>(R.id.fav).isEnabled = false
        }
        view.findViewById<LinearLayout>(R.id.side_dish_list).visibility = View.GONE
    }
}