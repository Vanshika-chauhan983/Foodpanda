package com.vanshika.foodpanda.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.vanshika.foodpanda.DataClasses.User
import com.vanshika.foodpanda.databinding.SignupBinding


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: SignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val email = binding.emailAddress.text.toString()
            val pass = binding.sPassword.text.toString()
            val confirmPass = binding.confirmpassword.text.toString()
            val username = binding.username.text.toString()
            val mobileNumber = binding.mobile.text.toString()
            val delivery = binding.address.text.toString()

            if ((email.isNotEmpty()) && (pass.isNotEmpty()) && (confirmPass.isNotEmpty()) && (username.isNotEmpty()) && (mobileNumber.isNotEmpty()) && (delivery.isNotEmpty())) {
                if (pass.length >= 6) {
                    if (pass == confirmPass) {
                        binding.register.isEnabled = false

                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    id = it.result.user?.uid ?: ""
                                    firebaseAuth.currentUser?.sendEmailVerification()
                                        ?.addOnSuccessListener {
                                            saveData(
                                                id,
                                                email,
                                                pass,
                                                username,
                                                mobileNumber,
                                                delivery
                                            )
                                        }?.addOnFailureListener {
                                            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                } else {
                                    Toast.makeText(
                                        this,
                                        it.exception?.message ?: "",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                binding.register.isEnabled = true

                            }
                    } else {
                        Toast.makeText(this, "Password is not matching!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Password must contain atleast 6 characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun saveData(
        id: String,
        email: String,
        pass: String,
        username: String,
        mobileNumber: String,
        delivery: String
    ) {
        val database = FirebaseDatabase.getInstance().getReference("User")
        val user = User(username, email, mobileNumber, delivery, pass)
        database.child(id).setValue(user).addOnSuccessListener {

            binding.username.text.clear()
            binding.mobile.text.clear()
            binding.address.text.clear()
            binding.emailAddress.text.clear()
            binding.sPassword.text?.clear()

            updateUI()
            Toast.makeText(this, "Successfully Saved!", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            Toast.makeText(this, "Signup Failed!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI() {
        if (firebaseAuth.currentUser?.isEmailVerified == true) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Please verify email before logging in!", Toast.LENGTH_SHORT)
                .show()
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