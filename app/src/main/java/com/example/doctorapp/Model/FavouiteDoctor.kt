package com.example.doctorapp.Model

data class FavouiteDoctor(
    val name:String,
    var work:String,
    val image:String? = null,
    val like:Boolean? = true
)
