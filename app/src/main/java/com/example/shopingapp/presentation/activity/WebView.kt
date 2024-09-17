package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.R

class WebView : BasicActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true // JavaScript'i etkinleştirir (gerekirse)
        webView.settings.domStorageEnabled = true // HTML5 localStorage desteğini etkinleştirir

        // WebViewClient ayarı
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                // Sayfa yükleme tamamlandığında yapılacak işlemler
                // Örneğin, bir Toast mesajı gösterebilirsiniz
                // Toast.makeText(this@WebViewActivity, "Page loaded!", Toast.LENGTH_SHORT).show()
            }
        }

        // URL'yi yükleme
        val url = intent.getStringExtra("URL")
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack() // WebView geri gidiyorsa önceki sayfaya gider
        } else {
            super.onBackPressed() // WebView geri gidemezse varsayılan davranış
        }
    }
}
