package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.shopingapp.databinding.ActivityIntroBinding

class IntroActivity : BasicActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase Auth nesnesini başlat
        firebaseAuth = FirebaseAuth.getInstance()

        // Kullanıcı giriş yapmış mı kontrol et
        if (firebaseAuth.currentUser != null) {
            // Eğer kullanıcı giriş yaptıysa, ana sayfaya geç
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Bu Activity'yi kapat
        }

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, SignupActvitiy::class.java))
        }

        binding.textLgn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
