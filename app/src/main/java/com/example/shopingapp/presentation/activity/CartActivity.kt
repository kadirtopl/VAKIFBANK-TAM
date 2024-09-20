package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.example.shopingapp.R
import com.example.shopingapp.data.model.OrderModel
import com.example.shopingapp.databinding.ActivityCartActvitiyBinding
import com.example.shopingapp.presentation.adapter.CartAdapter
import com.example.shopingapp.presentation.adapter.AddressAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CartActivity : BasicActivity() {
    private lateinit var binding: ActivityCartActvitiyBinding
    private lateinit var managmentCart: ManagmentCart
    private var selectedPaymentMethod: String = ""
    private var selectedAddress: String = ""
    private var isProcessingOrder = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartActvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)
        binding.apply {
            addressBtn.setOnClickListener {
                val intent = Intent(this@CartActivity, AddressesActivity::class.java)
                startActivity(intent)
            }
        }

        setVariable()
        initCartList()
        initAddressList()
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

    private fun initAddressList() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").document(userId).collection("addresses")
                .get()
                .addOnSuccessListener { documents ->
                    val addresses = documents.map { it.getString("address") ?: "" }
                    setupAddressRecyclerView(addresses)
                }
                .addOnFailureListener { e ->
                    showToast("Adresleri yüklerken hata: ${e.message}")
                }
        }
    }

    private fun setupAddressRecyclerView(addresses: List<String>) {
        binding.addressRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.addressRecyclerView.adapter = AddressAdapter(addresses) { address ->
            selectedAddress = address
            updateAddressSelection(addresses.indexOf(address))
            showToast("Seçilen adres: $selectedAddress")
        }
    }

    private fun updateAddressSelection(selectedIndex: Int) {
        for (i in 0 until binding.addressRecyclerView.childCount) {
            val child = binding.addressRecyclerView.getChildAt(i)
            if (i == selectedIndex) {
                child.setBackgroundResource(R.drawable.grey_bg_selected)
            } else {
                child.setBackgroundResource(R.drawable.grey_bg)
            }
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.buyBtn.setOnClickListener {
            if (isProcessingOrder) return@setOnClickListener // Eğer işlem devam ediyorsa çık

            if (selectedAddress.isNotEmpty() && selectedPaymentMethod.isNotEmpty()) {
                isProcessingOrder = true // Sipariş işleme alındı
                placeOrder()
            } else {
                if (selectedAddress.isEmpty()) {
                    showToast("Lütfen bir adres ekleyin veya seçin.")
                    // Adres ekleme sayfasına yönlendir
                    val intent = Intent(this, AddressesActivity::class.java)
                    startActivity(intent)
                } else {
                    showToast("Lütfen bir ödeme yöntemi seçiniz.")
                }
            }
        }

        binding.method1.setOnClickListener { selectPaymentMethod(binding.method1, "Kapıda Ödeme") }
        binding.method2.setOnClickListener { selectPaymentMethod(binding.method2, "Kredi Kartı") }
    }

    private fun selectPaymentMethod(selectedMethod: View, paymentMethod: String) {
        selectedPaymentMethod = paymentMethod
        updatePaymentMethodSelection(selectedMethod)
    }

    private fun updatePaymentMethodSelection(selectedView: View) {
        resetPaymentMethods()
        selectedView.setBackgroundResource(R.drawable.grey_bg_selected)
    }

    private fun resetPaymentMethods() {
        binding.method1.setBackgroundResource(R.drawable.grey_bg)
        binding.method2.setBackgroundResource(R.drawable.grey_bg)
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

    private fun updateCartVisibility() {
        with(binding) {
            emptyTxt.visibility = if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView3.visibility = if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun placeOrder() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            // Sepetteki ürünleri al
            val cartItems = managmentCart.getListCart()

            // Her bir ürün için ayrı sipariş oluştur
            val order = OrderModel(
                items = cartItems,
                totalAmount = managmentCart.getTotalFee() + 10.0, // Toplam tutar
                orderDate = getCurrentDateTime(),
                paymentMethod = selectedPaymentMethod,
                address = selectedAddress
            )

            FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("orders")
                .add(order)
                .addOnSuccessListener { documentReference ->
                    showToast("Sipariş başarıyla verildi: ")
                    managmentCart.clearCart() // Sepeti temizle
                    navigateToOrderConfirmation() // Onay sayfasına yönlendir
                }
                .addOnFailureListener { e ->
                    showToast("Sipariş verirken hata: ${e.message}")
                    isProcessingOrder = false // Hata durumunda işlemi iptal et
                }
        } else {
            showToast("Kullanıcı oturumu açmamış.")
        }
    }

    private fun navigateToOrderConfirmation() {
        val intent = Intent(this, OrderConfirmationActivity::class.java)
        startActivity(intent)
        finish() // Bu activity'i kapat
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentDateTime(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}
