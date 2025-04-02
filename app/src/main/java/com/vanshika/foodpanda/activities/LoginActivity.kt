package com.vanshika.foodpanda.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.vanshika.foodpanda.databinding.LoginBinding

class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.lPassword.text.toString()

            if (email.isEmpty()) {
                binding.email.error = "Email is required!"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.lPassword.error = "Password is required!"
                return@setOnClickListener
            }

            if (password.length >= 6) {
                binding.btnLogin.isEnabled = false
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {

                        if (firebaseAuth.currentUser?.isEmailVerified == true) {
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Please verify email!", LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, it.exception?.message, LENGTH_SHORT).show()
                    }
                    binding.btnLogin.isEnabled = true
                }
            } else {
                Toast.makeText(this, "Password must contain atleast 6 characters!", LENGTH_SHORT)
                    .show()
            }
        }

        binding.forgot.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        binding.account.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            if (firebaseAuth.currentUser?.isEmailVerified == true) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}


