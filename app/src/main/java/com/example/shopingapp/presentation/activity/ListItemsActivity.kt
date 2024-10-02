package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityListItemsBinding
import com.example.shopingapp.presentation.adapter.ListItemsAdapter
import com.example.shopingapp.presentation.viewModel.MainViewModel


class ListItemsActivity : BasicActivity() {

    private lateinit var binding: ActivityListItemsBinding
    private val viewModel: MainViewModel by viewModels() // ViewModel nesnesi, veri yönetimi için kullanılır
    private var id: String = "" // Kategori ID'si
    private var title: String = "" // Kategori başlığı

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Gelen intent verilerini alır ve listeyi başlatır
        getBundle()
        initList()
    }


    //  Listedeen paketi alır  başlatır ve gerekli UI ayarlarını yapar.

    private fun initList() {
        binding.apply {
            backBtn.setOnClickListener { finish() }

            progressBarList.visibility = View.VISIBLE

            // ViewModel'den gelen verileri gözlemler ve günceller
            viewModel.recommended.observe(this@ListItemsActivity, Observer { items ->
                // RecyclerView için GridLayoutManager ve adaptör ayarlarını yapar
                viewList.layoutManager = GridLayoutManager(this@ListItemsActivity, 2)
                viewList.adapter = ListItemsAdapter(items)
                progressBarList.visibility = View.GONE
            })

            // ViewModel'den filtrelenmiş verileri yükler
            viewModel.loadFiltered(id)
        }
    }

    /**
     * Intent'den gelen verileri alır ve UI'yi günceller.
     */
    private fun getBundle() {
        // Intent'den kategori ID'sini ve başlığını alır
        id = intent.getStringExtra("id") ?: ""
        title = intent.getStringExtra("title") ?: ""

        // Kategori başlığını UI'da gösterir
        binding.categoryTxt.text = title
    }
}
