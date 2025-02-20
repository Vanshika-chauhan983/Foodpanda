package com.vanshika.foodpanda

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.vanshika.foodpanda.databinding.LoginBinding
import com.vanshika.foodpanda.databinding.SignupBinding

class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var profileSharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.lPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    binding.btnLogin.isEnabled = false
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                        if (it.isSuccessful) {
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            val id= it.result.user?.uid
                            if (id != null) {
                                storeValue("uid",id)
                            }
                        }
                        else{
                            Toast.makeText(this, it.exception?.message ,LENGTH_SHORT).show()
                        }
                        binding.btnLogin.isEnabled = true
                    }
                }
                else {
                    Toast.makeText(this, "Password must contain atleast 6 characters!", LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Empty fields are not allowed!", LENGTH_SHORT).show()
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
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun storeValue(uid: String,value: String){
        profileSharedPreferences=getSharedPreferences("mypref",Context.MODE_PRIVATE)
        profileSharedPreferences.edit().putString(uid,value).apply()
    }
}


