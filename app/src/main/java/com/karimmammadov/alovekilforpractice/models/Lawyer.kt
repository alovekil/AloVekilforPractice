package com.karimmammadov.alovekilforpractice.models

data class Lawyer(
    var birth_date: String,
    var certificates: ArrayList<String>,
    var father_name: String,
    var gender: String,
    var law_practice: String,
    var lawyer_card: String,
    var lawyer_practice: String,
    var service_languages: ArrayList<Int>,
    var service_types: ArrayList<Int>,
    var university: String,
    var voen: String
)
