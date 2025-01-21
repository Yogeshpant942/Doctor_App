package com.example.doctorapp.StartingInterface

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.doctorapp.InApp.HomeFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentLoginBinding
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var callbackManager:CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        registerActivityWithSignIn() // Register the launcher

        callbackManager = CallbackManager.Factory.create()

        // Google Sign-In button click
        binding.googleLogin.setOnClickListener {
            signInWithGoogle()
        }

        binding.forgetPassword.setOnClickListener {
            val bottomFragment = BottomSheetResetPasswordFragment()
            bottomFragment.show(parentFragmentManager,bottomFragment.tag)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

    private fun registerActivityWithSignIn() {
        googleSignInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                signInWithFirebase(task)
            }
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(("AIzaSyBLW8LWY1Kq6yLxR3Bzg3uzSQeMSnzhEXo")) // Use string resource
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInIntent: Intent = googleSignInClient.signInIntent
        googleSignInResultLauncher.launch(signInIntent)
    }


    private fun signInWithFirebase(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            firebaseGoogleAccount(account)
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseGoogleAccount(account: GoogleSignInAccount) {
        val authCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null) {
                    val email = user.email
                    val name = user.displayName
                    Toast.makeText(requireContext(), "Welcome, $name!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(requireContext(), HomeFragment::class.java)
                    intent.putExtra("USER_NAME", name)
                    intent.putExtra("USER_EMAIL", email)
                    startActivity(intent)
                    requireActivity().finish()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    task.exception?.localizedMessage ?: "Login failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}