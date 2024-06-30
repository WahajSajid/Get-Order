package com.application.foodapp

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Suppress("DEPRECATION")
@SuppressLint("ParcelCreator")
data class Sections(
    @SerializedName("Name")
    var sectionName:String = "",
    var items:Map<String,Items> = emptyMap()):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readHashMap(Items::class.java.classLoader) as Map<String, Items>
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sectionName)
        parcel.writeMap(items)
    }

    companion object CREATOR : Parcelable.Creator<Sections> {
        override fun createFromParcel(parcel: Parcel): Sections = Sections(parcel)
        override fun newArray(size: Int): Array<Sections?> = arrayOfNulls(size)
    }

}