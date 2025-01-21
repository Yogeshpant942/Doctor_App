package com.example.doctorapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.Model.FavouiteDoctor
import com.example.doctorapp.Repositary
import kotlinx.coroutines.launch

class FavouriteDoctorModel(private val repositary: Repositary):ViewModel() {
 var favDoc = MutableLiveData<List<FavouiteDoctor>>()
    val removeDoc = MutableLiveData<Boolean>()

    fun fetchFavData(){
        viewModelScope.launch {
            favDoc.value = repositary.getFavouriteDoctor()
        }
    }
    fun removeFavDoc(name:String,work:String){
        viewModelScope.launch {
            removeDoc.value = repositary.removeFavDoc(name,work)
        }
    }
}