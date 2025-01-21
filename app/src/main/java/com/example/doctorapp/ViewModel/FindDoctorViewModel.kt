package com.example.doctorapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.Model.AllDoctor
import com.example.doctorapp.Model.FindDoctor
import com.example.doctorapp.Repositary
import kotlinx.coroutines.launch

class FindDoctorViewModel(private val repositary: Repositary):ViewModel() {
    val likedDoctor = MutableLiveData<Boolean>()
    val remLikedDoc = MutableLiveData<Boolean>()
    val allDoc = MutableLiveData<List<FindDoctor>>()

    fun AddLikedDoctor(doctorName:String,doctorWork:String){
        viewModelScope.launch {
            likedDoctor.postValue(repositary.uploadLikedDoctor(doctorName,doctorWork))
        }
    }
    fun removeDoc(doctorName:String,doctorWork:String){
        viewModelScope.launch {
            remLikedDoc.postValue(repositary.removeFavDoc(doctorName,doctorWork))
        }
    }
    fun retrieveDoctor(){
      viewModelScope.launch {
          val data = repositary.retrieveAllDataDoctor()
          allDoc.postValue(data)
          Log.d("DoctorData", "Doctors fetched: $data")
      }
    }
}