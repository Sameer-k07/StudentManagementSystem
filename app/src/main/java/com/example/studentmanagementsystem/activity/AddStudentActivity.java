package com.example.studentmanagementsystem.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.backgroundTask.BackgroundIntentService;
import com.example.studentmanagementsystem.backgroundTask.BackgroundAsyncTaskOperations;
import com.example.studentmanagementsystem.backgroundTask.BackgroundService;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.validator.Validate;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {

    private Button mSaveButton;
    private EditText mEtStudentName, mEtStudentRollNo;
    private DatabaseHelper db;
    private String[] mDialogItems={"ASYNC","SERVICE","INTENT SERVICE"};
    private StudentBroadcastReceiver mStudentBroadcastReceiver = new StudentBroadcastReceiver();

    //to register broadcast receiver
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(getString(R.string.broadcast));
        LocalBroadcastManager.getInstance(this).registerReceiver(mStudentBroadcastReceiver,intentFilter);
    }
    //to unregister broadcast receiver
    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mStudentBroadcastReceiver);

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        //to initialize button and editText fields
        initValues();

        //initializing database
        db = new DatabaseHelper(this);

        //to perform operations and sending data through intent
        setValuesFromIntent();
    }
    //to initialize button and editText fields
    private void initValues() {
        mSaveButton = findViewById(R.id.btn_save);
        mEtStudentName = findViewById(R.id.et_studentName);
        mEtStudentRollNo = findViewById(R.id.et_studentRoll);
    }
    //to perform operations and sending data through intent
    private void setValuesFromIntent(){
        Intent intent = getIntent();
        final ArrayList<Student> studentList=intent.getParcelableArrayListExtra(getString(R.string.student_list));
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

    //method to generate dialog box for choosing your operation
    private void generateDialogBox(final String operation,final String name,final String rollNo){
        //alert dialog box to show list of operations
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddStudentActivity.this);
        mBuilder.setTitle(R.string.choose);
        mBuilder.setSingleChoiceItems(mDialogItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                switch(i){
                    case Constant.ASYNC :
                        (new BackgroundAsyncTaskOperations(AddStudentActivity.this)).execute(operation,name,rollNo);
                        finish();
                        break;
                    case Constant.SERVICE :
                        Intent service=new Intent(AddStudentActivity.this, BackgroundService.class);
                        service.putExtra(Constant.OPERATION,operation);
                        service.putExtra(Constant.NAME,name);
                        service.putExtra(Constant.ROLL_NO,rollNo);
                        startService(service);
                        finish();
                        break;
                    case Constant.INTENT_SERVICE :
                        Intent intent=new Intent(AddStudentActivity.this, BackgroundIntentService.class);
                        intent.putExtra(Constant.OPERATION,operation);
                        intent.putExtra(Constant.NAME,name);
                        intent.putExtra(Constant.ROLL_NO,rollNo);
                        startService(intent);
                        finish();
                        break;
                    default:
                        break;

                }
            }
        });
        mBuilder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    /*method to view data
    *@param intent - to pass data
    *@param studentList - arraylist of Student object
    */
    private void save(Intent intent, final ArrayList<Student> studentList){
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
    private void saveData(final ArrayList<Student> studentList) {
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
        //to return name and roll number through intent to Main Activity and save data using preferred operation
        Intent returnIntent = getIntent();
        String name = mEtStudentName.getText().toString();
        String roll = mEtStudentRollNo.getText().toString();
        returnIntent.putExtra(Constant.NAME, mEtStudentName.getText().toString());
        returnIntent.putExtra(Constant.ROLL_NO, mEtStudentRollNo.getText().toString());
        setResult(RESULT_OK, returnIntent);
        generateDialogBox(Constant.NORMAL,name,roll);
    }

    /*
    method to view details of student
    *@param intent - to take details from Main Activity
    */
    private void viewMode(Intent intent) {
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
    private void editMode(Intent intent,final ArrayList<Student> studentList) {
        setTitle(R.string.edit);
        mEtStudentName.setText(intent.getStringExtra(Constant.VIEW_NAME));
        mEtStudentRollNo.setText(intent.getStringExtra(Constant.VIEW_ROLL));
        mSaveButton.setText(getString(R.string.update));
        //to return name and roll number through intent to Main Activity and update data using preferred operation
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =mEtStudentName.getText().toString();
                String roll =mEtStudentRollNo.getText().toString();
                Intent returnIntent = getIntent();
                returnIntent.putExtra(Constant.NAME,name);
                returnIntent.putExtra(Constant.ROLL_NO,roll);
                setResult(RESULT_OK, returnIntent);
                generateDialogBox(Constant.EDIT,name,roll);
            }
        });
    }
    //Inner broadcast receiver that receives the broadcast if the services have indeed added the elements in the database.
    public class StudentBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
            Toast.makeText(AddStudentActivity.this,getString(R.string.broadcast_received),Toast.LENGTH_LONG).show();
        }
    }

}//end of AddStudentActivity class
