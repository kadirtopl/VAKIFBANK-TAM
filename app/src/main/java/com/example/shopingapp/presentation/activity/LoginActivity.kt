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
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BasicActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val pass = binding.passwordInput.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {1
                        // Giriş başarılı ise MainActivity'ye geç
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Bu Activity'yi kapat, böylece geri butonuyla tekrar açılmaz
                    } else {
                        // Hata durumunda kullanıcıya hata mesajı göster
                        val errorMessage = task.exception?.localizedMessage ?: "Giriş yapılamadı"
                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "E-posta ve şifre alanları boş olamaz", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
