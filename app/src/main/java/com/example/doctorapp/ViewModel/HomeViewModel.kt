package com.example.doctorapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.Model.FeaturedDoctor
import com.example.doctorapp.Model.PopularDoctor
import com.example.doctorapp.Repositary
import kotlinx.coroutines.launch

class HomeViewModel(private  val repositary: Repositary): ViewModel(){
    val featureDoc = MutableLiveData<List<FeaturedDoctor>>()
        val popDoc = MutableLiveData<List<PopularDoctor>>()

    fun getFeaturedDoc(){
        viewModelScope.launch {
            val data  = repositary.getFeaturedDoc()
            featureDoc.postValue(data)
        }
    }

    fun getPopularDoc(doctor: String){
        viewModelScope.launch {
            val data = repositary.popularDoctor(doctor)
            popDoc.postValue(data)
        }
    }
}