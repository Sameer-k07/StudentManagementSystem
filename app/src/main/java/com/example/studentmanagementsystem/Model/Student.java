package com.example.studentmanagementsystem.Model;

public class Student {
    private String mName,mRoll;

    /*
    *@param name - name of student
    *@param roll - roll number of student
    */
    public  Student(String name ,String roll ){
        this.mName=name;
        this.mRoll=roll;
    }
    /*
    *@return name of the student
    */
    public String getmName() {
        return mName;
    }
    /*
    *@return roll number of the student
    */
    public String getmRoll() {
        return mRoll;
    }
    /*
    *@param name - name of student
    */
    public void setmName(String name){
        this.mName=name;
    }
    /*
    *@param roll - roll number of student
    */
    public void setmRoll(String roll){
        this.mRoll=roll;
    }
}//end of Student class
