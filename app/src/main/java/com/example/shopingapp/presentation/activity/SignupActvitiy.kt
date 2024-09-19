package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityLoginBinding
import com.example.shopingapp.databinding.ActivitySignupActvitiyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignupActvitiy : BasicActivity() {
    private lateinit var binding: ActivitySignupActvitiyBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupActvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.registerButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val pass = binding.passwordInput.text.toString()
            val name = binding.nameInput.text.toString()
            val surname = binding.surnameInput.text.toString()

            if (validateInputs(email, pass, name, surname)) {
                registerUser(email, pass, name, surname)
            }
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateInputs(email: String, pass: String, name: String, surname: String): Boolean {
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
            name.isEmpty() -> {
                Toast.makeText(this, "İsim boş olamaz", Toast.LENGTH_SHORT).show()
                false
            }
            surname.isEmpty() -> {
                Toast.makeText(this, "Soyisim boş olamaz", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun registerUser(email: String, password: String, name: String, surname: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener

                    val userMap = hashMapOf(
                        "email" to email,
                        "name" to name,
                        "surname" to surname,
                        "orders" to mutableListOf<String>() // Boş bir sipariş listesi
                    )

                    firestore.collection("users").document(userId)
                        .set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore Error", "Kullanıcı bilgilerini kaydetme hatası: ${e.message}")
                            Toast.makeText(this, "Kayıt sırasında hata: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Kayıt yapılamadı"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
}
