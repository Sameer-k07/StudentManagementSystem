package com.example.studentmanagementsystem.Comparator;

import com.example.studentmanagementsystem.Model.Student;

import java.util.Comparator;

public final class SortByRoll implements Comparator<Student> {
    /*method to compare objects of Student based on roll numbers
    @param s1-object1 of student
    @param s2-object2 of student
    @return roll no in sorted order
    */
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.parseInt(s1.getmRoll()) - Integer.parseInt(s2.getmRoll());
    }
}//end of SortByRoll Class
