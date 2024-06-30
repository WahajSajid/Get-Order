package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@SuppressLint("ParcelCreator")
data class Items(
//    @SerializedName("Name")
    var Name: String = "",
//    @SerializedName("Price")
    var Price: Int = 0,
//    @SerializedName("Availability")
    var Availability: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readBoolean()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeInt(Price)
        parcel.writeBoolean(Availability)
    }

    companion object CREATOR : Parcelable.Creator<Items> {
        override fun createFromParcel(parcel: Parcel): Items = Items(parcel)
        override fun newArray(size: Int): Array<Items?> = arrayOfNulls(size)
    }
}
