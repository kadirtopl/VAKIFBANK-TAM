package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityOrderDetailBinding // Binding sınıfını ekleyin

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding // Binding değişkeni
    private lateinit var order: OrderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater) // Binding'ı şişir
        setContentView(binding.root) // Root görünümünü ayarla

        order = intent.getParcelableExtra("order") ?: return

        // UI bileşenlerini güncelleyin
        binding.titleTxt.text = order.items.joinToString { it.title }
        binding.totalAmountTxt.text = "Toplam: TL ${order.totalAmount}"

        // Adres bilgisi kontrolü
        binding.addressTxt.text = if (order.address.isNotEmpty()) {
            order.address
        } else {
            "Adres bilgisi yok"
        }

        binding.orderDateTxt.text = "Sipariş Tarihi: ${order.orderDate}"

        // Model bilgisi kontrolü
        val model = order.items.firstOrNull()?.model?.firstOrNull() ?: "Model bilgisi yok"
        binding.modelTxt.text = "Model: $model"

        binding.descriptionTxt.text = order.items.joinToString { it.description }

        // Resmi ayarla
        val imageUrl = order.items.firstOrNull()?.picUrl?.firstOrNull()
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(binding.productImage)
        } else {
            binding.productImage.setImageResource(R.drawable.cash) // Yer tutucu resim
        }

        // Geri butonu için tıklama olayı
        binding.backBtn.setOnClickListener {
            onBackPressed() // Bir önceki aktiviteye dön
        }
    }
}
