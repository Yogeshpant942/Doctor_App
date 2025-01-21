package com.example.doctorapp.StartingInterface

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SignUpFragment : Fragment() {
    lateinit var googleSignInClient: GoogleSignInClient
    val signINcode = 1001

    lateinit var binding : FragmentSignUpBinding
    val auth:FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater,container,false)
        return binding.root




        binding.ButtonSignUp.setOnClickListener{
            val Name = binding.Name
            val email = binding.editTextLoginEmail.text.toString()
            val password = binding.editTextLoginPassword.text.toString()

            signUpWithEmail(email,password)

        }
        binding.googleSignIN.setOnClickListener{
            signUpWithGoogle()
        }


    }

    private fun signUpWithGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("607680992378-tiahtogu3q0oui4c6ct219bjo5p6j67k.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)
        signIn()


    }
    private fun signIn(){
        val signInIntent :Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }
  private fun registerActivityForGoogleSignIn(){
      activityResultLauncher =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
          val resultCode = result.resultCode
          val data = result.data

          if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
              val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
              firebaseSignWithGoogle(task)
          }

      }
  }

    private fun firebaseSignWithGoogle(task: Task<GoogleSignInAccount>) {
       try{
           val account:GoogleSignInAccount = task.getResult(ApiException::class.java)
           Toast.makeText(requireContext(),"Successfully Logged In",Toast.LENGTH_SHORT).show()
           fireBaseGoogleAccount(account)
       }
       catch (e:ApiException){
           Toast.makeText(requireContext(),e.localizedMessage,Toast.LENGTH_SHORT).show()

       }
    }

    private fun fireBaseGoogleAccount(account: GoogleSignInAccount) {
        val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null) {
                    // Retrieve user details
                    val email = user.email
                    val name = user.displayName

                    // Show a success message
                    Toast.makeText(requireContext(), "Welcome, $name!", Toast.LENGTH_SHORT).show()

                    // Redirect to the main app screen
                    val intent = Intent(requireContext(), LoginFragment::class.java)
                    intent.putExtra("USER_NAME", name)
                    intent.putExtra("USER_EMAIL", email)
                    startActivity(intent)

                }
            } else {
                // Show an error message
                val errorMessage = task.exception?.localizedMessage ?: "Login failed, please try again."
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpWithEmail( email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task->
            if(task.isSuccessful){
                Toast.makeText(requireContext(),"Your Account is Created",Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(requireContext(), task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        }
    }



}