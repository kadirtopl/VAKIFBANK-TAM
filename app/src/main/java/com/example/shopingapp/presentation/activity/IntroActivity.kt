package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityIntroBinding

class IntroActivity : BasicActivity() {
    private  lateinit var  binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
binding.apply {
    startBtn.setOnClickListener {
        startActivity(Intent(this@IntroActivity,MainActivity::class.java))
    }
}
    }
}