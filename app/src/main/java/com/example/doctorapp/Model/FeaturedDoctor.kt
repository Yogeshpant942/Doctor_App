package com.example.doctorapp.Model

import android.net.Uri

data class FeaturedDoctor(
    val name:String,
    val price:String,
    val work:String,
    val liked:Boolean? = false,
    val rating:String,
    val image: String? = null
)
