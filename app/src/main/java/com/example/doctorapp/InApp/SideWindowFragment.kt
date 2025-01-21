package com.example.doctorapp.InApp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSideWindowBinding
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SideWindowFragment : Fragment() {

lateinit var binding:FragmentSideWindowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSideWindowBinding.inflate(layoutInflater,container,false)

        binding.logOut.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Log Out")
                    .setMessage("Are you sure You Want to logout")
                    .setPositiveButton("YES"){dialog,_->
                  logOut()
                    }
                    .setNegativeButton("NO"){dialog,_->
                        dialog.dismiss()

                    }
                    .setCancelable(false)

        }
        return binding.root

    }



    private fun logOut() {
        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            for(profile in user.providerData){
                when(profile.providerId){
                EmailAuthProvider.PROVIDER_ID->{
                    logoutEmail()
                }
                GoogleAuthProvider.PROVIDER_ID->{
                    logOutGoogle()
                }FacebookAuthProvider.PROVIDER_ID->{
                    logOutFaceBook()
                }
                }
            }
        }
    }

    private fun logoutEmail() {
        FirebaseAuth.getInstance().signOut()
            //intent
    }

    private fun logOutFaceBook() {
        LoginManager.getInstance().logOut()

    }

    private fun logOutGoogle() {
        val googleSignInclient = GoogleSignIn.getClient(requireContext(),GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
        googleSignInclient.signOut().addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireContext(),"Succesfully Logged Out",Toast.LENGTH_SHORT).show()
                //intent
            }
            else {
                Log.e("Logout", "Google sign-out failed.", it.exception)
            }

        }

    }


}