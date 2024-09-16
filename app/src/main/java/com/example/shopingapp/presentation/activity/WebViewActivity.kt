package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("URL") ?: return

        // WebView'i başlat ve URL'yi yükle
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false // Kullanıcıya görünür zoom kontrollerini gizler

            webViewClient = WebViewClient() // Web sayfasının içinde gezinebilmesi için
            webChromeClient = WebChromeClient() // Web sayfasında JavaScript'i destekler ve diğer gelişmiş özellikleri sağlar
            loadUrl(url)
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack() // WebView'de geçmişe git
        } else {
            super.onBackPressed() // Geçmişte gidilecek yer yoksa, varsayılan geri buton davranışını gerçekleştir
        }
    }
}
