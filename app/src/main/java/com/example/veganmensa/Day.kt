package com.example.veganmensa

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Day(val date: LocalDate, val meals: ArrayList<Meal>, val sideDishes: ArrayList<Meal>) {
    companion object {
        var dateParser = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd.MM.yyyy")
    }

    var isVisible: Boolean = false

    constructor(dateString: String, meals: ArrayList<Meal>, sideDishes: ArrayList<Meal>) : this(
        LocalDate.parse(dateString, dateParser),
        meals,
        sideDishes
    )

    fun isToday(): Boolean {
        return date == LocalDate.now()
    }

    override fun toString(): String {
        return this.date.format(dateFormatter)
    }
}
