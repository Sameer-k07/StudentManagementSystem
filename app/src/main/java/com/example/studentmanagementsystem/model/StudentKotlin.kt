package com.example.studentmanagementsystem.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*class StudentKotlin : Parcelable {
     var mName: String? = null
     var mRollNo: String? = null

    constructor(name: String, rollNo: String) {
        this.mName = name
        this.mRollNo = rollNo
    }

    protected constructor(`in`: Parcel) {
        mName = `in`.readString()
        mRollNo = `in`.readString()
    }
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(mName)
        dest.writeString(mRollNo)
    }

    companion object CREATOR : Parcelable.Creator<StudentKotlin> {
        override fun createFromParcel(parcel: Parcel): StudentKotlin {
            return StudentKotlin(parcel)
        }

        override fun newArray(size: Int): Array<StudentKotlin?> {
            return arrayOfNulls(size)
        }
    }*/

    @Parcelize
    data class StudentKotlin(
            var mName: String? = null,
            var mRollNo: String? = null
    ) : Parcelable


