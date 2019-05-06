package com.example.studentmanagementsystem.comparator

import com.example.studentmanagementsystem.model.StudentKotlin
import java.util.Comparator


class SortByNameKotlin {
    companion object : Comparator<StudentKotlin> {
        override fun compare(s1: StudentKotlin, s2: StudentKotlin): Int {
            return s1.mName!!.compareTo(s2.mRollNo.toString())
        }
    }
}