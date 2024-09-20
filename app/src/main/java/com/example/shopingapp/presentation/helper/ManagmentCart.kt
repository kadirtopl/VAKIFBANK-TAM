package com.example.project1762.Helper

import android.content.Context
import android.widget.Toast
import com.example.shopingapp.presentation.helper.TinyDB
import com.example.shopingapp.data.model.ItemsModel

// Sepet yönetimi için yardımcı sınıf
class ManagmentCart(val context: Context) {

    private val tinyDB = TinyDB(context) // TinyDB örneği, veriyi kalıcı olarak saklamak için

    // Sepete ürün ekler
    fun insertItem(item: ItemsModel) {
        var listFood = getListCart() // Sepetteki ürünleri alır
        val existAlready = listFood.any { it.title == item.title } // Aynı başlığa sahip bir ürün var mı kontrol eder
        val index = listFood.indexOfFirst { it.title == item.title } // Aynı başlığa sahip ürünün indeksini bulur

        if (existAlready) {
            listFood[index].numberInCart = item.numberInCart // Ürünün miktarını günceller
        } else {
            listFood.add(item) // Yeni ürünü sepete ekler
        }
        tinyDB.putListObject("CartList", listFood) // Güncellenmiş sepeti TinyDB'ye kaydeder
        Toast.makeText(context, "Sepete Eklendi", Toast.LENGTH_SHORT).show() // Kullanıcıya bilgi verir
    }

    // Sepetteki ürünlerin listesini alır
    fun getListCart(): ArrayList<ItemsModel> {
        return tinyDB.getListObject("CartList") ?: arrayListOf() // TinyDB'den ürün listesini alır veya boş liste döndürür
    }

    // Sepetteki ürün miktarını azaltır
    fun minusItem(listFood: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (listFood[position].numberInCart == 1) {
            listFood.removeAt(position) // Ürün miktarı 1 ise ürünü sepetten çıkarır
        } else {
            listFood[position].numberInCart-- // Ürün miktarını bir azaltır
        }
        tinyDB.putListObject("CartList", listFood) // Güncellenmiş sepeti TinyDB'ye kaydeder
        listener.onChanged() // Dinleyiciyi günceller
    }

    // Sepetteki ürün miktarını artırır
    fun plusItem(listFood: ArrayList<ItemsModel>, position: Int, listener: ChangeNumberItemsListener) {
        listFood[position].numberInCart++ // Ürün miktarını bir artırır
        tinyDB.putListObject("CartList", listFood) // Güncellenmiş sepeti TinyDB'ye kaydeder
        listener.onChanged() // Dinleyiciyi günceller
    }

    // Sepetteki tüm ürünlerin toplam ücretini hesaplar
    fun getTotalFee(): Double {
        val listFood = getListCart() // Sepetteki ürünleri alır
        var fee = 0.0 // Toplam ücret değişkeni
        for (item in listFood) {
            fee += item.price * item.numberInCart // Ürünün fiyatını ve miktarını kullanarak toplam ücreti hesaplar
        }
        return fee // Toplam ücreti döndürür
    }

    // Sepeti temizler
    fun clearCart() {
        tinyDB.putListObject("CartList", arrayListOf()) // Sepeti temizlemek için boş bir liste kaydeder
        Toast.makeText(context, "Sepet temizlendi", Toast.LENGTH_SHORT).show() // Kullanıcıya bilgi verir
    }
}
