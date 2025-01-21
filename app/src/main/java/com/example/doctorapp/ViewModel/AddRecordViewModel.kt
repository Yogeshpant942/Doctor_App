package com.example.doctorapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.Model.recordClass
import com.example.doctorapp.Repositary
import kotlinx.coroutines.launch

class AddRecordViewModel(private val repositary: Repositary):ViewModel() {
      val record = MutableLiveData<Boolean>()
    val getRecord = MutableLiveData<List<recordClass>>()

     fun uploadData(recordClass: recordClass){
         viewModelScope.launch {
             record.postValue(repositary.uploadPatientRecord(recordClass))
         }
     }

    fun getData(){
        viewModelScope.launch {
            getRecord.postValue(repositary.retrievePatientRecord())
            
        }
    }
}