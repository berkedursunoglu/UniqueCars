package com.example.uniquecars

import java.io.Serializable

data class Cars(
    val car_name: String?,
    val car_model: String?, val car_year: String?, val id: Int, val image: ByteArray?
) :Serializable{
}