package com.example.shopingapp.presentation.activity

import OrderAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityOrderHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val ordersList = mutableListOf<OrderModel>()
    private var filteredOrdersList = mutableListOf<OrderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        displayUserFullName()
        setupRecyclerView()
        fetchOrders()

        // Arama butonuna tıklandığında
        binding.searchBtn.setOnClickListener {
            val query = binding.searchInput.text.toString().trim()
            filterOrders(query)
        }
    }

    private fun setupRecyclerView() {
        binding.orderRecylerView.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter(ordersList) { order ->
            // Sipariş detaylarına git
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
        }
        binding.orderRecylerView.adapter = orderAdapter
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
                    filteredOrdersList = ordersList.toMutableList() // Başlangıçta tüm siparişleri göster
                    orderAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { e ->
                    // Hata mesajı
                }
        }
    }

    private fun filterOrders(query: String) {
        filteredOrdersList = ordersList.filter { order ->
            order.items.any { it.title.contains(query, ignoreCase = true) }
        }.toMutableList()

        orderAdapter = OrderAdapter(filteredOrdersList) { order ->
            // Sipariş detaylarına git
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
        }
        binding.orderRecylerView.adapter = orderAdapter
        orderAdapter.notifyDataSetChanged()
    }

    private fun displayUserFullName() {
        val user = auth.currentUser
        user?.let {
            val userId = it.uid

            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("name")
                        val surname = document.getString("surname")
                        binding.firebaseAuth.text = "$name $surname" // Ad ve soyadı birleştir
                    } else {
                        binding.firebaseAuth.text = "Kullanıcı bilgileri bulunamadı"
                    }
                }
                .addOnFailureListener { exception ->
                    binding.firebaseAuth.text = "Hata: ${exception.message}"
                }
        }
    }
}
