package com.example.studentmanagementsystem.validator;

import com.example.studentmanagementsystem.activity.MainActivity;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.model.Student;

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
    *@param roll-roll number of student
    *@return true or false depending upon validation
    */
    public static boolean isValidRollNo(String roll) {
        String PATTERN = Constant.ROLL_MATCH;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(roll);
        return matcher.matches();
    }
    /*method to check if the entered roll no is unique or not
    *@param roll-roll number of student
    *@return true or false depending upon whether unique or not
    */
    public static boolean isUniqueRollNo(String roll){
        for(Student student: MainActivity.studentList){
            if(student.getmRoll().equals(roll)){
                return false;
            }
        }
        return true;
    }
}//end of Validate class
