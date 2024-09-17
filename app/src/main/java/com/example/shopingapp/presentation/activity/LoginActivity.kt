package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.util.Patterns

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

            if (validateInputs(email, pass)) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
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
            }
        }
    }

    private fun validateInputs(email: String, pass: String): Boolean {
        return when {
            email.isEmpty() -> {
                Toast.makeText(this, "E-posta adresi boş olamaz", Toast.LENGTH_SHORT).show()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(this, "Geçersiz e-posta adresi", Toast.LENGTH_SHORT).show()
                false
            }
            pass.isEmpty() -> {
                Toast.makeText(this, "Şifre boş olamaz", Toast.LENGTH_SHORT).show()
                false
            }
            pass.length < 6 -> {
                Toast.makeText(this, "Şifre en az 6 karakter uzunluğunda olmalıdır", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
