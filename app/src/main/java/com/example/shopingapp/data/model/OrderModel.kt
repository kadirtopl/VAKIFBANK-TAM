package com.example.shopingapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class OrderModel(
    var items: List<ItemsModel> = listOf(),
    var totalAmount: Double = 0.0,
    var orderDate: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(ItemsModel.CREATOR) ?: listOf(),
        parcel.readDouble(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(items)
        parcel.writeDouble(totalAmount)
        parcel.writeString(orderDate)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<OrderModel> {
        override fun createFromParcel(parcel: Parcel): OrderModel {
            return OrderModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderModel?> {
            return arrayOfNulls(size)
        }
    }
}
