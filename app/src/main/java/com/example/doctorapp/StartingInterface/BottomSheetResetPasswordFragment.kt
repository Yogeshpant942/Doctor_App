package com.example.doctorapp.StartingInterface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentBottomSheetResetPasswordBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth


class BottomSheetResetPasswordFragment : BottomSheetDialogFragment() {

lateinit var binding:FragmentBottomSheetResetPasswordBinding
val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentBottomSheetResetPasswordBinding.inflate(layoutInflater,container,false)
        binding.continueButton.setOnClickListener {
            val email = binding.emailForReset.text.toString().trim()
            if(email!= null){
                auth.sendPasswordResetEmail(email).addOnCompleteListener {task->
                    if(task.isSuccessful){
                        Toast.makeText(requireContext(), "we sent a password reset mail to your email",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireContext(),task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        return binding.root
    }
}