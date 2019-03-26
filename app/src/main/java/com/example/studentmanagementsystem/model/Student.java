package com.example.studentmanagementsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {


    private String mName,mRollNo;
    /*
    *@param name - name of student
    *@param rollNo - roll number of student
    */
    public  Student(String name ,String rollNo ){
        this.mName=name;
        this.mRollNo=rollNo;
    }
    /*
    *constructor used for parcel to read data
    *@param in - object of parcel to read
    */
    protected Student(Parcel in) {
        mName = in.readString();
        mRollNo = in.readString();
    }
    //to bind data
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    /*
    *@return name of the student
    */
    public String getmName() {
        return mName;
    }
    /*
    *@return roll number of the student
    */
    public String getmRollNo() {
        return mRollNo;
    }
    /*
    *@param name - name of student
    */
    public void setmName(String name){
        this.mName=name;
    }
    /*
    *@param rollNo - roll number of student
    */
    public void setmRollNo(String rollNo){
        this.mRollNo=rollNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
    *write object values to parcel for storage
    *@param dest - the parcel in which the object should be written
    *@param flags - Additional flags about how the object should be written
    */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mRollNo);
    }

}//end of Student class
