package com.example.studentmanagementsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = Constant.DB_VERSION;

    // Database Name
    private static final String DATABASE_NAME = Constant.DB_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table SQL query
        final String CREATE_TABLE =
                "CREATE TABLE " + Constant.TABLE_NAME + "("
                        + Constant.COLUMN_NAME + " TEXT,"
                        + Constant.COLUMN_ROLL_NO + " TEXT"
                        + ")";

        // create student table
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    /*
    *method to insert data in database
    *@param name - name of student
    *@param rollNo - roll number of student
    *@return id - row of data
    */
    public long insertStudent(String name , String rollNo ) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constant.COLUMN_NAME, name);
        values.put(Constant.COLUMN_ROLL_NO, rollNo);


        // insert row
        long id = db.insert(Constant.TABLE_NAME, null, values);


        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Constant.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                students.add(new Student(cursor.getString(cursor.getColumnIndex(Constant.COLUMN_NAME)),cursor.getString(cursor.getColumnIndex(Constant.COLUMN_ROLL_NO))));
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        // return students list
        return students;
    }


    public int getStudentsCount() {
        String countQuery = "SELECT  * FROM " + Constant.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*
    *method to update data in database
    *param name - name of student
    *param rollNo - roll number of student
    */
    public void updateStudent(String name,String rollNo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.COLUMN_NAME, name);

        // updating row
         db.update(Constant.TABLE_NAME, values, Constant.COLUMN_ROLL_NO + " = ?",
                new String[]{rollNo});

    }
    /*
    *method to delete data from database
    *@param student- object of Student
    */
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(Constant.TABLE_NAME, Constant.COLUMN_ROLL_NO + " = ?",
                new String[]{String.valueOf(student.getmRollNo())});
        db.close();
    }
}