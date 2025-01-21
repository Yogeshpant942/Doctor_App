package com.example.doctorapp.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doctorapp.Repositary
import com.example.doctorapp.ViewModel.FindDoctorViewModel

class AllDoctorFactory(val repositary: Repositary):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FindDoctorViewModel::class.java)) {
            return FindDoctorViewModel(repositary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}