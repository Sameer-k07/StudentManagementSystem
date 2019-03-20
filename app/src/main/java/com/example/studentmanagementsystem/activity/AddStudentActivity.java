package com.example.studentmanagementsystem.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentmanagementsystem.fragment.AddStudentFragment;
import com.example.studentmanagementsystem.communicator.Communication;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.database.DatabaseHelper;

public class AddStudentActivity extends AppCompatActivity implements Communication {

    private Bundle mBundle;
    private AddStudentFragment mAddStudentFragment;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        //to set fragment transaction manager
        mBundle=getIntent().getExtras();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        mAddStudentFragment=new AddStudentFragment();
        fragmentTransaction.add(R.id.frag_container,mAddStudentFragment,"");
        fragmentTransaction.commit();

    }

    @Override
    public void communicateAdd(Bundle bundle) {

    }

    @Override
    public void communicateUpdate(Bundle bundle) {

    }
    //to reuse fragment for viewing data
    @Override
    protected void onStart() {
        super.onStart();
        mAddStudentFragment.viewMode(mBundle);

    }
}//end of AddStudentActivity class
