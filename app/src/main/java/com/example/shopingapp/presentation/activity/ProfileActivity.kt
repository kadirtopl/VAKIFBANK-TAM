package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityProfileActvitiyBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileActvitiyBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileActvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase Authentication nesnesini başlat
        auth = FirebaseAuth.getInstance()

        binding.addressButton.setOnClickListener {
            val intent = Intent(this, AddressesActivity::class.java)
            startActivity(intent)
        }

        binding.ordersButton.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            logoutUser() // Çıkış işlemi
        }
        binding.backBtn.setOnClickListener{
            finish()
        }
    }

    private fun logoutUser() {
        auth.signOut() // Firebase'den çıkış yap
        startActivity(Intent(this, IntroActivity::class.java)) // Giriş ekranına yönlendir
        finish() // Mevcut aktiviteyi kapat
    }
}
