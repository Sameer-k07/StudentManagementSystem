package com.example.studentmanagementsystem.comparator

import com.example.studentmanagementsystem.model.StudentKotlin

class SortByRollNoKotlin : Comparator<StudentKotlin>{
    override fun compare(s1: StudentKotlin, s2: StudentKotlin): Int {
        return Integer.parseInt(s1.mRollNo.toString()) - Integer.parseInt(s2.mRollNo.toString())
    }
}