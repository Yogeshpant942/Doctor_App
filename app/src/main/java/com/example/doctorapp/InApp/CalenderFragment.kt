package com.example.doctorapp.InApp

import android.os.Bundle
import android.provider.CalendarContract.EventDays
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.work.OneTimeWorkRequestBuilder
import com.example.doctorapp.NotificationWorker

import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentCalenderBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


class CalenderFragment : Fragment() {
    lateinit var binding: FragmentCalenderBinding
    private var selectDate: String? = null
    private var selectedTime: String? = null
    private var selectedRemainder: String? = null
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(layoutInflater, container, false)

        // Move this code here, before the return statement
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            selectDate = dateFormatter.format(calendar.time)
        }

        val timebuttons = listOf(
            binding.timeSlot1,
            binding.timeSlot2,
            binding.timeSlot3,
            binding.timeSlot4
        )

        setupButtonSelection(timebuttons) { time -> selectedTime = time }

        val reminderButtons = listOf(
            binding.reminder10,
            binding.reminder25,
            binding.reminder30,
            binding.reminder35
        )
        setupButtonSelection(reminderButtons) { reminder -> selectedRemainder = reminder }

        binding.btnConfirm.setOnClickListener {
            if (selectDate != null && selectedTime != null) {
                val appoinment = hashMapOf(
                    "date" to selectDate,
                    "time" to selectedTime,
                    "reminder" to selectedRemainder
                )

                db.collection("appointments").add(appoinment).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Appointment saved!", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "Error saving appointment: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(), "Please select a date and time.", Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Return the root view after setting everything up
        return binding.root
    }

    private fun setupButtonSelection(timebuttons: List<AppCompatButton>, onSelected: (String) -> Unit) {
        for (button in timebuttons) {
            button.setOnClickListener {
                for (btn in timebuttons) {
                    btn.setBackgroundResource(android.R.drawable.btn_default)
                }
                button.setBackgroundResource(android.R.color.holo_green_light)
                onSelected(button.text?.toString() ?: "")
            }
        }
    }
    private fun scheduleNotification(date:String,time:String){
        val dateTimeFormater = SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.getDefault())
        val dateTimeString = "$date $time"
        val appointmentTime = dateTimeFormater.parse(dateTimeString)
        if(appointmentTime!= null){
            val delay = appointmentTime.time-System.currentTimeMillis()
            if(delay>0){
                val workrequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInitialDelay(delay,TimeUnit.MILLISECONDS)
                    .build()
            }
            else{
                Toast.makeText(requireContext(),"Cannot sehedule a notification in the past",Toast.LENGTH_SHORT).show()
            }
        }
    }


}
