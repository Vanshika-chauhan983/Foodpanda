package com.vanshika.foodpanda.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.vanshika.foodpanda.DataClasses.User
import com.vanshika.foodpanda.databinding.SignupBinding


class SignupActivity : AppCompatActivity() {

    private lateinit var binding:SignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()

        binding.register.setOnClickListener{
            val email = binding.emailAddress.text.toString()
            val pass = binding.sPassword.text.toString()
            val confirmPass = binding.confirmpassword.text.toString()
            val username = binding.username.text.toString()
            val mobileNumber=binding.mobile.text.toString()
            val delivery = binding.address.text.toString()

            if ((email.isNotEmpty()) && (pass.isNotEmpty()) && (confirmPass.isNotEmpty()) && (username.isNotEmpty()) && (mobileNumber.isNotEmpty()) && (delivery.isNotEmpty())){
                if(pass.length>=6){
                    if (pass == confirmPass){
                        binding.register.isEnabled = false
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                            if (it.isSuccessful){
                                id=it.result.user?.uid ?:""
                                signupSuccess(id)
                            }
                            else{
                                Toast.makeText(this, it.exception?.message ?: "",Toast.LENGTH_SHORT).show()
                            }
                            binding.register.isEnabled = true
                        }
                    }else{
                        Toast.makeText(this,"Password is not matching!",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"Password must contain atleast 6 characters!",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Empty fields are not allowed!",Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun signupSuccess(id:String){

        val username = binding.username.text.toString()
        val mobileNumber=binding.mobile.text.toString()
        val delivery = binding.address.text.toString()
        val email = binding.emailAddress.text.toString()
        val pass = binding.sPassword.text.toString()

        database=FirebaseDatabase.getInstance().getReference("User")
        val user= User(username,email,mobileNumber,delivery,pass)
        database.child(id).setValue(user).addOnSuccessListener {

            binding.username.text.clear()
            binding.mobile.text.clear()
            binding.address.text.clear()
            binding.emailAddress.text.clear()
            binding.sPassword.text?.clear()

            Toast.makeText(this,"Successfully Saved!",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{

            Toast.makeText(this,"Failed!", Toast.LENGTH_SHORT).show()
        }
        val intent=Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}