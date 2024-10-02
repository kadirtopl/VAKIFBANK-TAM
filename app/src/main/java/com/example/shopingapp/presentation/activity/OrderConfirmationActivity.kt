package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityOrderConfirmationBinding

class OrderConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Onay mesajını ayarla
        binding.confirmationMessage.text = "Siparişiniz başarıyla oluşturuldu!"
        binding.apply {
            devamet.setOnClickListener{
                val intent=Intent(this@OrderConfirmationActivity,MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
