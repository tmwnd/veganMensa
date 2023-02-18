package com.example.veganmensa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MealListAdapter(val context: Context, val list: ArrayList<Meal>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return list[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.meal, parent, false)
        val meal = list[i]

        view.findViewById<TextView>(R.id.meal_category).text = meal.category
        view.findViewById<TextView>(R.id.meal_name).text = meal.name
        view.findViewById<TextView>(R.id.meal_price).text = meal.price.toString() + "0€"

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