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

class MealListAdapter(private val context: Context, private val meals: ArrayList<Meal>) :
    BaseAdapter() {
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

        view.visibility = if (meal.isFav) View.VISIBLE else View.GONE
        view.findViewById<TextView>(R.id.meal_category).text = meal.category
        view.findViewById<TextView>(R.id.meal_name).text = meal.name
        view.findViewById<TextView>(R.id.meal_price).text = meal.price.toString() + "0€"
        view.findViewById<CheckBox>(R.id.fav).isChecked = meal.isFav

        view.findViewById<CheckBox>(R.id.fav).setOnCheckedChangeListener { it, isChecked ->
            when (isChecked) {
                true -> meal.isFav = false
                false -> meal.isFav = false
            }
        }

        var allergenics = ""
        var div = ""
        meal.allergenics.forEach { allergenic ->
            if (Meal.allergenicMap.keys.contains(allergenic)) allergenics += div + Meal.allergenicMap[allergenic]
            div = ", "
        }

        view.findViewById<TextView>(R.id.meal_allergenics).text = allergenics
        view.setBackgroundColor(
            MaterialColors.getColor(
                view,
                if (i % 2 == 0) R.attr.colorEvenMeal else R.attr.colorOddMeal
            )
        )

        return view
    }
}