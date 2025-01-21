package com.example.doctorapp.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doctorapp.Repositary
import com.example.doctorapp.ViewModel.AddRecordViewModel
import com.example.doctorapp.ViewModel.HomeViewModel

class HomeFactory(private val repositary: Repositary):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repositary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}