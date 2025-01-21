package com.example.doctorapp.Model


data class FindDoctor(
    val name:String?= null,
    val work:String?= null,
    val experience:String? = null,
    val nextTime:Int? = null,
    val image: String? = null,
    var liked:Boolean? = false
)


