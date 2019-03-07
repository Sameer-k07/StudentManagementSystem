package com.example.studentmanagementsystem.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.Constant.Constant;
import com.example.studentmanagementsystem.Model.Student;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.Validator.Validate;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {
    Button saveButton;
    EditText name,roll;
    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        saveButton =(Button) findViewById(R.id.btn_save);
        name = (EditText) findViewById(R.id.et_studentName);
        roll = (EditText) findViewById(R.id.et_studentRoll);
        Intent intent = getIntent();

       //intent picks up the mode type and matches with dialog box modes to perform operations
       if(intent.getStringExtra(Constant.MODE).equals(Constant.NORMAL)) {
           setTitle(R.string.addStudent);
           saveButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   saveData();
               }
           });
       }else if (intent.getStringExtra(Constant.MODE).equals(Constant.VIEW)){
           viewMode(saveButton,name,roll,intent);
       }else if(intent.getStringExtra(Constant.MODE).equals(Constant.EDIT)){
           editMode(saveButton,intent);
       }
    }
    //method to save the details of the student
    public void saveData(){
        //to check if the the entered name is in valid format
        if (!Validate.isValidName(name.getText().toString())){
            Toast.makeText(AddStudentActivity.this,getString(R.string.invalidName),
                    Toast.LENGTH_LONG).show();
            return;
        }
        //to check if the the entered roll number is in valid format
        if (!Validate.isValidRollNo(roll.getText().toString())){
            Toast.makeText(AddStudentActivity.this,getString(R.string.invalidRoll),
                    Toast.LENGTH_LONG).show();
            return;
        }
        //to check if the the entered roll number is unique or not
        if (!Validate.isUniqueRollNo(roll.getText().toString().trim())) {
            Toast.makeText(AddStudentActivity.this, getString(R.string.rollNotUnique),
                    Toast.LENGTH_LONG).show();
            return;

        }
            //to return name and roll number through intent to Main Activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constant.NAME,name.getText().toString());
            returnIntent.putExtra(Constant.ROLL,roll.getText().toString());
            setResult(RESULT_OK,returnIntent);
            finish();
    }
    /*
    method to view details of student
    *@param saveButton - to hide the button
    *@param name - to enter name of student
    *@param roll - to enter roll number of student
    *@param intent - to take details from Main Activity
    */
    public void viewMode(Button saveButton,EditText name,EditText roll,Intent intent){
        setTitle(R.string.details);
        saveButton.setVisibility(View.INVISIBLE);
        name.setText(intent.getStringExtra(Constant.VIEW_NAME));
        name.setTypeface(Typeface.DEFAULT_BOLD);
        roll.setText(intent.getStringExtra(Constant.VIEW_ROLL));
        roll.setTypeface(Typeface.DEFAULT_BOLD);
        name.setEnabled(false);
        roll.setEnabled(false);
    }
    /*
    method to edit details of student
    *@param saveButton - to update details of student
    */
    @SuppressLint("SetTextI18n")
    public void editMode(Button saveButton, Intent intent) {
        setTitle(R.string.edit);
        name.setText(intent.getStringExtra(Constant.VIEW_NAME));
        roll.setText(intent.getStringExtra(Constant.VIEW_ROLL));
        position=getIntent().getStringExtra(Constant.POSITION);
        saveButton.setText(getString(R.string.update));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to check if the the entered name is in valid format
                if (!Validate.isValidName(name.getText().toString())) {
                    Toast.makeText(AddStudentActivity.this,getString(R.string.invalidName),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //to check if the the entered roll number is in valid format
                if (!Validate.isValidRollNo(roll.getText().toString())) {
                    Toast.makeText(AddStudentActivity.this,getString(R.string.invalidRoll),
                            Toast.LENGTH_LONG).show();
                    return;

                }
                //to check if the the entered roll number is unique or not
                if (!Validate.isUniqueRollNo(roll.getText().toString())) {
                    Toast.makeText(AddStudentActivity.this, getString(R.string.rollNotUnique),
                            Toast.LENGTH_LONG).show();
                    return;

                }
                    //to return updated name and roll number through intent to Main Activity
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(Constant.UPDATED_NAME, name.getText().toString());
                    returnIntent.putExtra(Constant.UPDATED_ROLL, roll.getText().toString());
                    setResult(RESULT_OK, returnIntent);
                    finish();

            }
        });
    }
}//end of AddStudentActivity class
