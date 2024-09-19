package com.example.shopingapp.presentation.activity

import android.content.Intent
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

class CartActivity : BasicActivity() {
    private lateinit var binding: ActivityCartActvitiyBinding
    private lateinit var managmentCart: ManagmentCart
    private var selectedPaymentMethod: String = "Kapıda Ödeme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartActvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        setVariable()
        initCartList()
        calculatorCart()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager = LinearLayoutManager(this)
        binding.viewCart.adapter = CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
            override fun onChanged() {
                calculatorCart()
            }
        })
        updateCartVisibility()
    }

    private fun updateCartVisibility() {
        with(binding) {
            emptyTxt.visibility = if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView3.visibility = if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.buyBtn.setOnClickListener {
            placeOrder()
        }

        binding.method1.setOnClickListener { updatePaymentMethodUI(binding.method1, binding.method2, "Kapıda Ödeme") }
        binding.method2.setOnClickListener { updatePaymentMethodUI(binding.method2, binding.method1, "Kredi Kartı") }
    }

    private fun updatePaymentMethodUI(selectedMethod: View, unselectedMethod: View, paymentMethod: String) {
        selectedPaymentMethod = paymentMethod

        // Seçilen yöntemin arka planını yeşil yap
        selectedMethod.setBackgroundResource(R.drawable.green_bg_selected)
        updateTextColor(selectedMethod, R.color.black)

        // Diğer yöntemi gri yap
        unselectedMethod.setBackgroundResource(R.drawable.grey_bg_selected)
        updateTextColor(unselectedMethod, R.color.grey)
    }

    private fun updateTextColor(methodView: View, colorResId: Int) {
        val title = methodView.findViewById<TextView>(R.id.methodTitle1) // methodTitle1 doğru isim mi kontrol edin
        val subtitle = methodView.findViewById<TextView>(R.id.methodSubtitle1) // methodSubtitle1 doğru isim mi kontrol edin

        title.setTextColor(ContextCompat.getColor(this, colorResId))
        subtitle.setTextColor(ContextCompat.getColor(this, colorResId))
    }

    private fun calculatorCart() {
        val delivery = 10.0
        val total = Math.round((managmentCart.getTotalFee() + delivery) * 100) / 100
        val itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100

        with(binding) {
            totalFeeTxt.text = "TL$itemTotal"
            deliveryTxt.text = "TL$delivery"
            totalTxt.text = "TL$total"
        }
    }

    private fun placeOrder() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val order = OrderModel(
                items = managmentCart.getListCart(),
                totalAmount = managmentCart.getTotalFee() + 10.0,
                orderDate = System.currentTimeMillis().toString(),
                paymentMethod = selectedPaymentMethod
            )

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("orders")
                .add(order)
                .addOnSuccessListener { documentReference ->
                    showToast("Sipariş başarıyla verildi: ${documentReference.id}")
                    initCartList()
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
