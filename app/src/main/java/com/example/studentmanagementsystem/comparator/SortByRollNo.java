package com.example.studentmanagementsystem.comparator;

import com.example.studentmanagementsystem.model.Student;

import java.util.Comparator;

public final class SortByRollNo implements Comparator<Student> {
    /*method to compare objects of Student based on roll numbers
    @param s1-object1 of student
    @param s2-object2 of student
    @return roll no in sorted order
    */
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.parseInt(s1.getmRollNo()) - Integer.parseInt(s2.getmRollNo());
    }
}//end of SortByRoll Class
