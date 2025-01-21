package com.example.doctorapp.Model

import android.net.Uri

data class PopularDoctor(
    var Name:String,
    var post:String,
    var image:String? = null,
    var like:Boolean? =false,
    var rating:String
)
