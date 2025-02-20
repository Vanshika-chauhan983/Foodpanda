package com.vanshika.foodpanda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.vanshika.foodpanda.databinding.ForgotPasswordBinding

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding: ForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()

        binding.nextbtn.setOnClickListener{
            val email= binding.forgotemail.text.toString()
            if (email.isNotEmpty()){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this,"Please check your mailbox!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, it.exception?.message ?: "", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this,"Email field can't be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}