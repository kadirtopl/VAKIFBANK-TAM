package com.example.shopingapp.presentation.activity

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityAdressesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddressesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdressesBinding
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var addressListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addressListLayout = binding.addressList

        binding.backBtn.setOnClickListener {
            finish() // Geri dönüş için mevcut aktiviteyi kapat
        }

        binding.addAddressButton.setOnClickListener {
            val newAddress = binding.addressInput.text.toString()
            if (newAddress.isNotBlank()) {
                addAddress(newAddress)
                binding.addressInput.text.clear() // Giriş alanını temizle
            }
        }

        loadAddresses() // Mevcut adresleri yükle
    }

    private fun loadAddresses() {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).collection("addresses")
            .get()
            .addOnSuccessListener { documents ->
                addressListLayout.removeAllViews() // Önceki adresleri temizle
                for (document in documents) {
                    val address = document.getString("address") ?: continue
                    val addressId = document.id
                    displayAddress(address, addressId)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Adresleri yüklerken hata: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun displayAddress(address: String, addressId: String) {
        val addressView = layoutInflater.inflate(R.layout.item_address, null)
        val addressTextView = addressView.findViewById<TextView>(R.id.addressTextView)
        val deleteButton = addressView.findViewById<TextView>(R.id.deleteButton)

        addressTextView.text = address
        deleteButton.setOnClickListener {
            deleteAddress(addressId) // Adresi sil
        }

        addressListLayout.addView(addressView)
    }

    private fun addAddress(address: String) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).collection("addresses")
            .add(hashMapOf("address" to address))
            .addOnSuccessListener {
                loadAddresses() // Yeni adres eklendikten sonra adresleri yeniden yükle
                Toast.makeText(this, "Adres başarıyla eklendi.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Adres eklerken hata: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteAddress(addressId: String) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).collection("addresses")
            .document(addressId)
            .delete()
            .addOnSuccessListener {
                loadAddresses() // Adres silindikten sonra adresleri yeniden yükle
                Toast.makeText(this, "Adres başarıyla silindi.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Adres silerken hata: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
