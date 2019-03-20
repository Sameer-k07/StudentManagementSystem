package com.example.studentmanagementsystem.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.AddStudentFragment;
import com.example.studentmanagementsystem.Communication;
import com.example.studentmanagementsystem.backgroundTasks.BackgroundIntentService;
import com.example.studentmanagementsystem.backgroundTasks.BackgroundAsyncTaskOperations;
import com.example.studentmanagementsystem.backgroundTasks.BackgroundService;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.validator.Validate;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity implements Communication {

    private Button mSaveButton;
    private EditText mEtStudentName, mEtStudentRollNo;
    private DatabaseHelper db;
    private String[] mDialogItems={"ASYNC","SERVICE","INTENT SERVICE"};


    private  Long id;
    private Bundle bundle;
    private AddStudentFragment addStudentFragment;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        bundle=getIntent().getExtras();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        addStudentFragment=new AddStudentFragment();
        fragmentTransaction.add(R.id.frag_container,addStudentFragment,"");
        fragmentTransaction.commit();

    }

    @Override
    public void communicateAdd(Bundle bundle) {

    }

    @Override
    public void communicateUpdate(Bundle bundle) {

    }
    @Override
    protected void onStart() {
        super.onStart();
        addStudentFragment.viewMode(bundle);

    }
}//end of AddStudentActivity class
