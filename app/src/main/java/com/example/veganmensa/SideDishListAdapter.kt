package com.example.veganmensa

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SideDishListAdapter(val context: Context, val sideDishes: ArrayList<Meal>) : BaseAdapter() {
    override fun getCount(): Int {
        return sideDishes.size
    }

    override fun getItem(i: Int): Any {
        return sideDishes[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.side_dish, parent, false)
        val meal = getItem(i) as Meal

        view.findViewById<TextView>(R.id.meal_category).text = meal.category + ":"
        view.findViewById<TextView>(R.id.meal_name).text = meal.name

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