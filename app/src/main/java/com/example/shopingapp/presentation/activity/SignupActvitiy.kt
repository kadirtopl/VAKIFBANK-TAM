package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityLoginBinding
import com.example.shopingapp.databinding.ActivitySignupActvitiyBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActvitiy : BasicActivity() {
    private  lateinit var binding:ActivitySignupActvitiyBinding
    private  lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupActvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener{
            val email =binding.emailInput.text.toString()
            val pass=binding.passwordInput.text.toString()


            if (email.isNotEmpty()&& pass.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val  intent=Intent(this,LoginActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }


            }else{
                Toast.makeText(this,"Zaten Böyle Bir Kullanıcı var " , Toast.LENGTH_SHORT).show()

            }
        }
        binding.cancelButton.setOnClickListener{
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

    }
}