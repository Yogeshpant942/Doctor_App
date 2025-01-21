package com.example.doctorapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.Model.AllDoctor
import com.example.doctorapp.Model.recordClass
import com.example.doctorapp.Repositary
import kotlinx.coroutines.launch

class AllDoctorViewModel(private val repositary: Repositary):ViewModel() {
    val allDoc = MutableLiveData<List<AllDoctor>>()

    fun getData(category: String){
        viewModelScope.launch {
            allDoc.postValue(repositary.retrieveAllData(category))
        }
    }

}