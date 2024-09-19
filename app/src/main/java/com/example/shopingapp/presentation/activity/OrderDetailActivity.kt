package com.example.shopingapp.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding
    private lateinit var order: OrderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getOrderData()
        displayOrderDetails()
    }

    private fun getOrderData() {
        order = intent.getParcelableExtra("order") ?: run {
            finish() // Eğer order null ise, Activity'yi kapat
            return
        }
    }

    private fun displayOrderDetails() {
        binding.orderDetailsTxt.text = order.items.joinToString(", ") { it.title }
        binding.totalAmountTxt.text = "Toplam: TL${order.totalAmount}"
        binding.orderDateTxt.text = order.orderDate
    }
}
