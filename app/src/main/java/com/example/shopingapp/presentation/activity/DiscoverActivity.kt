package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityDiscoverActivityBinding
import com.example.shopingapp.presentation.adapter.ListItemsAdapter
import com.example.shopingapp.presentation.viewModel.MainViewModel

class DiscoverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscoverActivityBinding
    private val viewModel: MainViewModel by viewModels() // ViewModel nesnesini burada doğru bir şekilde alıyoruz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // UI'yi başlat
        initUI()

        // ViewModel'den tüm ürünleri yükler
        viewModel.loadAllItems()
    }

    private fun initUI() {
        binding.apply {
            // Geri dönme butonunu ayarla
            backBtn.setOnClickListener { finish() }

            // İlk başta progress bar'ı göster
            progressBarList.visibility = View.VISIBLE

            // ViewModel'den gelen verileri gözlemler ve RecyclerView ayarlarını yapar
            viewModel.recommended.observe(this@DiscoverActivity, Observer { items ->
                // RecyclerView için GridLayoutManager ve adaptör ayarlarını yapar
                viewList.layoutManager = GridLayoutManager(this@DiscoverActivity, 2)
                viewList.adapter = ListItemsAdapter(items)
                progressBarList.visibility = View.GONE // Veriler yüklendikten sonra progress bar'ı gizle
            })
        }
    }
}
