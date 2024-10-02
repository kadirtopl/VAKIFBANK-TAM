package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopingapp.databinding.ActivitySearchBinding
import com.example.shopingapp.presentation.adapter.ListItemsAdapter
import com.example.shopingapp.presentation.viewModel.MainViewModel

class SearchActivity : BasicActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var listItemsAdapter: ListItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        // ViewModel'den tüm ürünleri yükle
        viewModel.loadAllItems()
    }

    private fun initUI() {
        binding.apply {
            // RecyclerView için LinearLayoutManager ve adaptör ayarlarını yap
            searchResultsList.layoutManager = GridLayoutManager(this@SearchActivity, 2)
            listItemsAdapter = ListItemsAdapter(emptyList()) // Başlangıçta boş bir liste ile
            searchResultsList.adapter = listItemsAdapter

            // ViewModel'den gelen verileri gözlemler
            viewModel.recommended.observe(this@SearchActivity, Observer { items ->
                listItemsAdapter.updateData(items) // Veriyi adapte et
                progressBarSearch.visibility = View.GONE // Veriler yüklendikten sonra progress bar'ı gizle
            })

            // SearchView'i dinle
            searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    listItemsAdapter.filter.filter(newText)
                    return true
                }
            })
        }
    }


}
