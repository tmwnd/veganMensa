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

class MealListAdapter(val context: Context, val meals: ArrayList<Meal>) : BaseAdapter() {
    override fun getCount(): Int {
        return meals.size
    }

    override fun getItem(i: Int): Any {
        return meals[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.meal, parent, false)
        val meal = getItem(i) as Meal

        view.findViewById<TextView>(R.id.meal_category).text = meal.category
        view.findViewById<TextView>(R.id.meal_name).text = meal.name
        view.findViewById<TextView>(R.id.meal_price).text = meal.price.toString() + "0€"

        view.findViewById<CheckBox>(R.id.fav).setOnCheckedChangeListener { it, isChecked ->
            val day = it.parent.parent.parent.parent as LinearLayout
            val favs = day.findViewById<LinearLayout>(R.id.fav_list)

            if (isChecked) {
                val favMeal = getView(i, convertView, parent)
                val fav = favMeal.findViewById<CheckBox>(R.id.fav)

                fav.isEnabled = false
                fav.setOnCheckedChangeListener(null)
                fav.isChecked = true

                favs.addView(favMeal)
            } else {
                for (fav in favs.children)
                    if (fav.findViewById<TextView>(R.id.meal_name).text == meal.name)
                        favs.removeView(fav)
            }
        }

        var allergenics = ""
        var div = ""
        for (allergenic in meal.allergenics) {
            if (allergenicMap.keys.contains(allergenic))
                allergenics += div + allergenicMap[allergenic]
            div = ", "
        }

        view.findViewById<TextView>(R.id.meal_allergenics).text = allergenics

        return view
    }
}