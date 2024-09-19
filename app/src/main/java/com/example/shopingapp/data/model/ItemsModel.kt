package com.example.shopingapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class ItemsModel(
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var picUrl: List<String> = listOf(),
    var numberInCart: Int = 0,
    var model: List<String> = listOf() // Bu satırı ekleyin
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.createStringArrayList() ?: listOf(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: listOf() // Bu satırı ekleyin
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeDouble(rating)
        parcel.writeStringList(picUrl)
        parcel.writeInt(numberInCart)
        parcel.writeStringList(model) // Bu satırı ekleyin
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ItemsModel> {
        override fun createFromParcel(parcel: Parcel): ItemsModel {
            return ItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemsModel?> {
            return arrayOfNulls(size)
        }
    }
}
