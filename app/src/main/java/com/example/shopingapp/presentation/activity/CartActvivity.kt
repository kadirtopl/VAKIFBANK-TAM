package com.example.shopingapp.presentation.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.example.shopingapp.R
import com.example.shopingapp.databinding.ActivityCartActvitiyBinding
import com.example.shopingapp.presentation.adapter.CartAdapter

class CartActvivity : BasicActivity() {
    private lateinit var binding: ActivityCartActvitiyBinding // Layout için ViewBinding
    private lateinit var managmentCart: ManagmentCart // Sepet verilerini yöneten sınıf

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityCartActvitiyBinding.inflate(layoutInflater) // Layout'u ViewBinding ile şişir
        setContentView(binding.root) // İçerik görünümünü şişirilmiş layout ile ayarla
        managmentCart = ManagmentCart(this) // Sepet yönetimini başlat

        setVariable() // UI olay dinleyicilerini ve başlangıç durumlarını ayarla
        initCartList() // Sepet listesini başlat ve yapılandır
        calculatorCart() // Sepet toplamlarını hesapla ve görüntüle
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            ) // RecyclerView için düzen yöneticisini ayarla
        binding.viewCart.adapter =
            CartAdapter(managmentCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculatorCart() // Ürün sayısı değiştiğinde sepet toplamlarını yeniden hesapla
                }
            })
        with(binding) {
            emptyTxt.visibility =
                if (managmentCart.getListCart()
                        .isEmpty()
                ) View.VISIBLE else View.GONE // Sepet boşsa mesajı göster/gizle
            scrollView3.visibility =
                if (managmentCart.getListCart()
                        .isEmpty()
                ) View.GONE else View.VISIBLE // Sepet içeriğini göster/gizle
        }
    }

    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener {
                val intent = Intent(this@CartActvivity, MainActivity::class.java)
                startActivity(intent)
            }

            method1.setOnClickListener {
                method1.setBackgroundResource(R.drawable.green_bg_selected) // Yöntem 1 seçildiğinde arka planı yeşil yap
                methodIc1.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@CartActvivity,
                            R.color.black
                        )
                    ) // İkon rengini sarı yap
                methodTitle1.setTextColor(getResources().getColor(R.color.black)) // Başlık rengini sarı yap
                methodSubtitle1.setTextColor(getResources().getColor(R.color.black)) // Alt başlık rengini sarı yap

                method2.setBackgroundResource(R.drawable.grey_bg_selected) // Yöntem 2'yi gri arka plan yap
                methodIc2.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@CartActvivity,
                            R.color.black
                        )
                    ) // İkon rengini siyah yap
                methodTitle2.setTextColor(getResources().getColor(R.color.black)) // Başlık rengini siyah yap
                methodSubtitle2.setTextColor(getResources().getColor(R.color.black)) // Alt başlık rengini gri yap
            }

            method2.setOnClickListener {
                method2.setBackgroundResource(R.drawable.green_bg_selected) // Yöntem 2 seçildiğinde arka planı yeşil yap
                methodIc2.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@CartActvivity,
                            R.color.black
                        )
                    ) // İkon rengini sarı yap
                methodTitle2.setTextColor(getResources().getColor(R.color.black)) // Başlık rengini sarı yap
                methodSubtitle2.setTextColor(getResources().getColor(R.color.black)) // Alt başlık rengini sarı yap

                method1.setBackgroundResource(R.drawable.grey_bg_selected) // Yöntem 1'i gri arka plan yap
                methodIc1.imageTintList =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this@CartActvivity,
                            R.color.black
                        )
                    ) // İkon rengini siyah yap
                methodTitle1.setTextColor(getResources().getColor(R.color.black)) // Başlık rengini siyah yap
                methodSubtitle1.setTextColor(getResources().getColor(R.color.black)) // Alt başlık rengini gri yap
            }
        }
    }

    private fun calculatorCart() {
        val percentTax = 0.02 // Vergi oranı
        val delivery = 10.0 // Teslimat ücreti
        val total =
            Math.round((managmentCart.getTotalFee() + delivery) * 100) / 100 // Toplam ücreti hesapla
        val itemTotal =
            Math.round(managmentCart.getTotalFee() * 100) / 100 // Ürün toplamını hesapla

        with(binding) {
            totalFeeTxt.text = "TL$itemTotal" // Ürün toplamını görüntüle
            deliveryTxt.text = "TL$delivery" // Teslimat ücretini görüntüle
            totalTxt.text = "TL$total" // Toplam ücreti görüntüle
        }
    }
}


