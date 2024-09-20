package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var order: OrderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        order = intent.getParcelableExtra("order") ?: return

        // UI bileşenlerini güncelleyin
        val totalAmountTxt = findViewById<TextView>(R.id.totalAmountTxt)
        totalAmountTxt.text = "Toplam: TL${order.totalAmount}"

        // Daha fazla detay ekleyebilirsiniz...
    }
}
