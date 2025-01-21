package com.example.doctorapp.StartingInterface

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth


class SplashScreenFragment : Fragment() {
    lateinit var navController:NavController
    var auth =FirebaseAuth.getInstance()
    lateinit var binding:FragmentSplashScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(layoutInflater,container,false)

    val user = auth.currentUser
    if(user != null){
        val logIntime = user.metadata?.lastSignInTimestamp
        val signUptime = user.metadata?.creationTimestamp
        if(logIntime == signUptime){
            // navigate to first screen
            val hander = Handler(Looper.getMainLooper())
             hander.postDelayed(object :Runnable{
                 override fun run() {
                     findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                 }
             },3000)
        }
        }
        else {
            //navigate to home fragment
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(object : Runnable {
                override fun run() {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_startScreen1Fragment)

                }
            }, 3000)
    }
        return binding.root

    }
}