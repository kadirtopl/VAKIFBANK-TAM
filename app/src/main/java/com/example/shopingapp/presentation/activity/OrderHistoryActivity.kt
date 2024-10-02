package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityOrderHistoryBinding
import com.example.shopingapp.presentation.adapter.OrderAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val ordersList = mutableListOf<OrderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        binding.backBtn.setOnClickListener {
            finish()
        }

        displayUserFullName()
        setupRecyclerView()
        fetchOrders()


        // Arama işlemini dinlemek için TextWatcher ekle
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                orderAdapter.filter(query) // Filtreleme fonksiyonunu çağır
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
            FirebaseFirestore.getInstance().collection("users").document(userId).collection("orders").orderBy("orderDate",
                Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val order = document.toObject(OrderModel::class.java)
                        ordersList.add(order)
                    }
                    orderAdapter.notifyDataSetChanged() // Sipariş listesini güncelle
                }
                .addOnFailureListener { e ->
                    // Hata mesajı
                }
        }
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
