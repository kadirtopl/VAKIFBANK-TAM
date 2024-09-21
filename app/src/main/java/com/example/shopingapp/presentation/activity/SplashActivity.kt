package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val splashDuration: Long = 300 // 3 saniye

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Splash ekranı için bir zamanlayıcı ayarla
        Handler().postDelayed({
            // Ana sayfaya veya kayıt sayfasına geçiş
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish() // Bu Activity'yi kapat
        }, splashDuration)
    }
}
