package com.example.studentmanagementsystem.validator;

import android.util.Log;
import android.widget.Toast;

import com.example.studentmanagementsystem.activity.AddStudentActivity;
import com.example.studentmanagementsystem.activity.MainActivity;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    /*
    method to check if user enters valid name only i.e characters
    *@param name-name of student
    *@return true or false depending upon validation
    */
    public static boolean isValidName(String name) {
        String PATTERN = Constant.NAME_MATCH;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    /*
    method to check if user enters valid roll no only i.e integer values
    *@param rollNo-roll number of student
    *@return true or false depending upon validation
    */
    public static boolean isValidRollNo(String rollNo) {
        String PATTERN = Constant.ROLL_MATCH;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(rollNo);
        return matcher.matches();
    }
    /*method to check if the entered roll no is unique or not
    *@param rollNo-roll number of student
    *@param studentList - arraylist of object Student
    *@return false if roll no is unique else true
    */
    public static boolean isUniqueRollNo(String rollNo,final ArrayList<Student> studentList){
        for(Student student: studentList){
            if(student.getmRollNo().equals(rollNo)){
                return false;
            }
        }
        return true;
    }
}//end of Validate class
