package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityOrderHistoryBinding
import com.example.shopingapp.presentation.adapter.OrderAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var orderAdapter: OrderAdapter
    private val ordersList = mutableListOf<OrderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchOrders()
    }

    private fun setupRecyclerView() {
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter(ordersList) { order ->
            // Sipariş detaylarına git
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
        }
        binding.orderRecyclerView.adapter = orderAdapter
    }

    private fun fetchOrders() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").document(userId).collection("orders")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val order = document.toObject(OrderModel::class.java)
                        ordersList.add(order)
                    }
                    orderAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    // Hata mesajı
                }
        }
    }
}
