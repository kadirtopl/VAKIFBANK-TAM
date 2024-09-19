package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityCartActvitiyBinding
import com.example.shopingapp.presentation.adapter.CartAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartActvivity : BasicActivity() {
    private lateinit var binding: ActivityCartActvitiyBinding
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartActvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        managmentCart = ManagmentCart(this)

        setUpViews()
        initCartList()
        calculateCart()
    }

    private fun setUpViews() {
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.method1.setOnClickListener { selectPaymentMethod(binding.method1, binding.method2) }
        binding.method2.setOnClickListener { selectPaymentMethod(binding.method2, binding.method1) }

        binding.buyBtn.setOnClickListener {
            if (managmentCart.getListCart().isNotEmpty()) {
                placeOrder()
            } else {
                showToast("Sepetiniz boş, lütfen ürün ekleyin.")
            }
        }
    }

    var selectedPaymentMethod: String? = "Kapıda Ödeme"
    private fun selectPaymentMethod(selectedMethod: View, deselectedMethod: View) {
        selectedMethod.setBackgroundResource(R.drawable.green_bg_selected)
        deselectedMethod.setBackgroundResource(R.drawable.grey_bg_selected)

        if(selectedMethod.isSelected){
            selectedPaymentMethod = selectedMethod.transitionName
        }
        updateMethodTextColor(selectedMethod, true)
        updateMethodTextColor(deselectedMethod, false)
    }

    private fun updateMethodTextColor(method: View, isSelected: Boolean) {
        val titleColor = if (isSelected) R.color.black else R.color.grey
        val subtitleColor = if (isSelected) R.color.black else R.color.darkGrey

        method.findViewById<TextView>(R.id.methodTitle1).setTextColor(ContextCompat.getColor(this, titleColor))
        method.findViewById<TextView>(R.id.methodSubtitle1).setTextColor(ContextCompat.getColor(this, subtitleColor))
    }

    private fun initCartList() {
        binding.viewCart.layoutManager = LinearLayoutManager(this)
        binding.viewCart.adapter = CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
            override fun onChanged() {
                calculateCart()
            }
        })

        binding.emptyTxt.visibility = if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
        binding.scrollView3.visibility = if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
    }

    private fun calculateCart() {
        val deliveryFee = 10.0
        val itemTotal = managmentCart.getTotalFee()
        val total = Math.round((itemTotal + deliveryFee) * 100) / 100.0

        with(binding) {
            totalFeeTxt.text = "TL$itemTotal"
            deliveryTxt.text = "TL$deliveryFee"
            totalTxt.text = "TL$total"
        }
    }

    private fun placeOrder() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val order = OrderModel(
                items = managmentCart.getListCart(),
                totalAmount = managmentCart.getTotalFee() + 10.0,
                orderDate = System.currentTimeMillis().toString()
            )

            // Kullanıcının "orders" alt koleksiyonuna sipariş ekleyin
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("orders") // Alt koleksiyon
                .add(order)
                .addOnSuccessListener { documentReference ->
                    showToast("Sipariş başarıyla verildi: ${documentReference.id}")
                    initCartList() // Sepeti güncelle
                }
                .addOnFailureListener { e ->
                    showToast("Sipariş verirken hata: ${e.message}")
                }
        } else {
            showToast("Kullanıcı oturumu açmamış.")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
