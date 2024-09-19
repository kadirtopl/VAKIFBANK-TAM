package com.example.shopingapp.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityOrderHistoryBinding
import com.example.shopingapp.presentation.adapter.OrderAdapter

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var adapter: OrderAdapter
    private val orders = mutableListOf<OrderModel>() // Sipariş listesi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadOrders()
    }

    private fun setupRecyclerView() {
        adapter = OrderAdapter(orders)
        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewOrders.adapter = adapter
    }

    private fun loadOrders() {


        adapter.notifyDataSetChanged()
    }
}
