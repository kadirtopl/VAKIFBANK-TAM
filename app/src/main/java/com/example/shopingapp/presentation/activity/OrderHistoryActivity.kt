package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopingapp.R
import com.example.shopingapp.data.model.ItemsModel
import com.example.shopingapp.presentation.adapter.OrderAdapter
import com.example.shopingapp.databinding.ActivityOrderHistoryBinding
import com.google.firebase.database.*

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var database: DatabaseReference
    private lateinit var orderList: MutableList<ItemsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase Database referansını al
        database = FirebaseDatabase.getInstance().getReference("users") // "users" referansını al
        orderList = mutableListOf()

        // Siparişleri çek
        fetchOrders()
    }

    private fun fetchOrders() {
        // Kullanıcıların siparişlerini almak için her kullanıcıyı kontrol et
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear() // Önceki verileri temizle
                for (userSnapshot in snapshot.children) {
                    val ordersSnapshot = userSnapshot.child("orders") // Kullanıcının siparişlerini al
                    for (orderSnapshot in ordersSnapshot.children) {
                        val order = orderSnapshot.getValue(ItemsModel::class.java)
                        order?.let { orderList.add(it) } // Veriyi listeye ekle
                    }
                }
                setupRecyclerView() // RecyclerView'ı kur
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("OrderHistory", "Veri çekilirken hata: ${error.message}")
            }
        })
    }

    private fun setupRecyclerView() {
        binding.viewList.layoutManager = LinearLayoutManager(this)
        binding.viewList.adapter = OrderAdapter(orderList)
    }
}
