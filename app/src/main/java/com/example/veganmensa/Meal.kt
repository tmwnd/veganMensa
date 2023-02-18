package com.example.veganmensa

val allergenicMap = mapOf(
    "Farbstoff" to "1",
    "Konservierungsstoff" to "2",
    "Antioxidationsmittel" to "3",
    "Geschmacksverstärker" to "4",
    "geschwefelt" to "5",
    "geschwärzt" to "6",
    "gewachst" to "7",
    "Phosphat" to "8",
    "Süßungsmittel" to "9",
    "enthält eine Phenylalaninquelle" to "10",
    "Gluten" to "A",
    "Weizen" to "A1",
    "Roggen" to "A2",
    "Gerste" to "A3",
    "Hafer" to "A4",
    "Dinkel" to "A5",
    "Sellerie" to "B",
    "Erdnüsse" to "F",
    "Sojabohnen" to "G",
    "Schalenfrüchte" to "I",
    "Mandeln" to "I1",
    "Haselnüsse" to "I2",
    "Walnüsse" to "I3",
    "Kaschunüsse" to "I4",
    "Pecannüsse" to "I5",
    "Paranüsse" to "I6",
    "Pistazien" to "I7",
    "Macadamianüsse" to "I8",
    "Senf" to "J",
    "Sesamsamen" to "K",
    "Schwefeldioxid oder Sulfite" to "L",
    "Lupinen" to "M"
)

data class Meal(val id: Int, val name: String, val category: String, val price: Double, val allergenics: ArrayList<String>)