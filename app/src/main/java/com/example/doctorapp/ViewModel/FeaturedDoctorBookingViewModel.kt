package com.example.doctorapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.Model.FeaturedDoctor
import com.example.doctorapp.Model.PatientNameAndContact
import com.example.doctorapp.Repositary
import kotlinx.coroutines.launch

class FeaturedDoctorBookingViewModel(private val repositary: Repositary):ViewModel() {
    val featuredBookingData = MutableLiveData<Boolean>()

    fun saveData(detail:PatientNameAndContact)
    {
        viewModelScope.launch {
            featuredBookingData.value = repositary.uploadPatientDetailForFeaturedDoctor(detail)
        }
    }
}