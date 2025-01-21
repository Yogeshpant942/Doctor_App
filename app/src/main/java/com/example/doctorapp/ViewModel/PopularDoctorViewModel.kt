package com.example.doctorapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doctorapp.Model.PopularDoctor
import com.example.doctorapp.Repositary
import kotlinx.coroutines.launch

class PopularDoctorViewModel(private val repo:Repositary): ViewModel() {
    val popularDoc = MutableLiveData<List<PopularDoctor>>()

    fun popDoc(doctor: String){
        viewModelScope.launch {
            popularDoc.postValue(repo.popularDoctor(doctor))
        }
    }
}