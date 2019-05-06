package com.example.studentmanagementsystem.validator

import com.example.studentmanagementsystem.constant.Constant
import com.example.studentmanagementsystem.model.StudentKotlin
import java.util.ArrayList
import java.util.regex.Pattern

 class ValidateKotlin {

     companion object {
         fun isValidName(name: String): Boolean {
             val PATTERN = Constant.NAME_MATCH
             val pattern = Pattern.compile(PATTERN)
             val matcher = pattern.matcher(name)
             return matcher.matches()
         }

         fun isValidRollNo(rollNo: String): Boolean {
             val PATTERN = Constant.ROLL_MATCH
             val pattern = Pattern.compile(PATTERN)
             val matcher = pattern.matcher(rollNo)
             return matcher.matches()
         }

         fun isUniqueRollNo(rollNo: String, studentList: ArrayList<StudentKotlin>): Boolean {
             for (student in studentList) {
                 if (student.mRollNo == rollNo) {
                     return false
                 }
             }
             return true
         }
     }


}
