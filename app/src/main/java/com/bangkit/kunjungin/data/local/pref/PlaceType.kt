package com.bangkit.kunjungin.data.local.pref

import android.os.Parcel
import android.os.Parcelable

data class PlaceType(val id: String, val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceType> {
        override fun createFromParcel(parcel: Parcel): PlaceType {
            return PlaceType(parcel)
        }

        override fun newArray(size: Int): Array<PlaceType?> {
            return arrayOfNulls(size)
        }
    }
}
