package com.example.studentmanagementsystem.Comparator;

import com.example.studentmanagementsystem.Model.Student;

import java.util.Comparator;

public final class SortByName implements Comparator<Student> {
    /*method to compare objects of Student based on names
    @param s1-object1 of student
    @param s2-object2 of student
    @return names in sorted order
    */
    @Override
    public int compare(Student s1, Student s2) {
        return s1.getmName().compareTo(s2.getmName());
    }
}//end of SortByName Class
