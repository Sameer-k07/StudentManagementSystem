package com.example.studentmanagementsystem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.validator.Validate;

public class AddStudentActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText name, roll;
    private String position;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        initViews();
        setValuesFromIntent();
    }

    private void initViews() {
        saveButton = findViewById(R.id.btn_save);
        name = findViewById(R.id.etStudentName);
        roll = findViewById(R.id.et_studentRoll);
    }

    private void setValuesFromIntent(){
        Intent intent = getIntent();
        //intent picks up the mode type and matches with dialog box modes to perform operations
        switch (intent.getStringExtra(Constant.MODE)) {
            case Constant.NORMAL:
                save(intent);
                break;
            case Constant.VIEW:
                viewMode(intent);
                break;
            case Constant.EDIT:
                editMode(intent);
                break;
            default:
                break;
        }
    }
    //method to view data
    public void save(Intent intent){
        setTitle(R.string.addStudent);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    //method to save the details of the student
    public void saveData() {
        //to check if the the entered name is in valid format
        if (!Validate.isValidName(name.getText().toString().trim())) {
            Toast.makeText(AddStudentActivity.this, getString(R.string.invalidName),
                    Toast.LENGTH_LONG).show();
            return;
        }
        //to check if the the entered roll number is in valid format
        if (!Validate.isValidRollNo(roll.getText().toString().trim())) {
            Toast.makeText(AddStudentActivity.this, getString(R.string.invalidRoll),
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
        Intent returnIntent = getIntent();
        returnIntent.putExtra(Constant.NAME, name.getText().toString());
        returnIntent.putExtra(Constant.ROLL, roll.getText().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    /*
    method to view details of student
    *@param intent - to take details from Main Activity
    */
    public void viewMode(Intent intent) {
        setTitle(R.string.details);
        saveButton.setVisibility(View.INVISIBLE);
        name.setText(intent.getStringExtra(Constant.VIEW_NAME));
        roll.setText(intent.getStringExtra(Constant.VIEW_ROLL));
        name.setEnabled(false);
        roll.setEnabled(false);
    }

    /*
    method to edit details of student
    *@param intent - to take details from Main Activity
    */
    @SuppressLint("SetTextI18n")
    public void editMode(Intent intent) {
        setTitle(R.string.edit);
        name.setText(intent.getStringExtra(Constant.VIEW_NAME));
        roll.setText(intent.getStringExtra(Constant.VIEW_ROLL));
        position = getIntent().getStringExtra(Constant.POSITION);
        saveButton.setText(getString(R.string.update));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
}//end of AddStudentActivity class
