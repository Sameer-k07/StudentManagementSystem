package com.example.studentmanagementsystem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.validator.Validate;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {
    private Button mSaveButton;
    private EditText mEtStudentName, mEtStudentRollNo;
    private String mPosition;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        //to initialize button and editText fields
        initValues();
        //to perform operations and sending data through intent
        setValuesFromIntent();
    }

    private void initValues() {
        mSaveButton = findViewById(R.id.btn_save);
        mEtStudentName = findViewById(R.id.et_studentName);
        mEtStudentRollNo = findViewById(R.id.et_studentRoll);
    }

    private void setValuesFromIntent(){
        Intent intent = getIntent();
        final ArrayList<Student> studentList=intent.getParcelableArrayListExtra("mStudentList");
        //intent picks up the mode type and matches with dialog box modes to perform operations
        switch (intent.getStringExtra(Constant.MODE)) {
            case Constant.NORMAL:
                save(intent,studentList);
                break;
            case Constant.VIEW:
                viewMode(intent);
                break;
            case Constant.EDIT:
                editMode(intent,studentList);
                break;
            default:
                break;
        }
    }
    /*method to view data
    *@param intent - to pass data
    *@param studentList - arraylist of Student object
    */
    public void save(Intent intent, final ArrayList<Student> studentList){
        setTitle(R.string.addStudent);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(studentList);
            }
        });
    }
    /*method to save the details of the student
    *@param studentList - arraylist of Student object
    */
    public void saveData(final ArrayList<Student> studentList) {
        //to check if the the entered name is in valid format
        if (!Validate.isValidName(mEtStudentName.getText().toString().trim())) {
            Toast.makeText(AddStudentActivity.this, getString(R.string.invalidName),
                    Toast.LENGTH_LONG).show();
            return;
        }
        //to check if the the entered roll number is in valid format
        if (!Validate.isValidRollNo(mEtStudentRollNo.getText().toString().trim())) {
            Toast.makeText(AddStudentActivity.this, getString(R.string.invalidRoll),
                    Toast.LENGTH_LONG).show();
            return;
        }
        //to check if the the entered roll number is unique or not
        if (!Validate.isUniqueRollNo(mEtStudentRollNo.getText().toString().trim(),studentList)) {
            Toast.makeText(AddStudentActivity.this, getString(R.string.rollNotUnique),
                    Toast.LENGTH_LONG).show();
            return;

        }
        //to return name and roll number through intent to Main Activity
        Intent returnIntent = getIntent();
        returnIntent.putExtra(Constant.NAME, mEtStudentName.getText().toString());
        returnIntent.putExtra(Constant.ROLL_NO, mEtStudentRollNo.getText().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    /*
    method to view details of student
    *@param intent - to take details from Main Activity
    */
    public void viewMode(Intent intent) {
        setTitle(R.string.details);
        mSaveButton.setVisibility(View.INVISIBLE);
        mEtStudentName.setText(intent.getStringExtra(Constant.VIEW_NAME));
        mEtStudentRollNo.setText(intent.getStringExtra(Constant.VIEW_ROLL));
        mEtStudentName.setEnabled(false);
        mEtStudentRollNo.setEnabled(false);
    }

    /*
    method to edit details of student
    *@param intent - to take details from Main Activity
    *@param studentList - arraylist of Student object
    */
    @SuppressLint("SetTextI18n")
    public void editMode(Intent intent,final ArrayList<Student> studentList) {
        setTitle(R.string.edit);
        mEtStudentName.setText(intent.getStringExtra(Constant.VIEW_NAME));
        mEtStudentRollNo.setText(intent.getStringExtra(Constant.VIEW_ROLL));
        mPosition = getIntent().getStringExtra(Constant.POSITION);
        mSaveButton.setText(getString(R.string.update));
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(studentList);
            }
        });
    }
}//end of AddStudentActivity class
