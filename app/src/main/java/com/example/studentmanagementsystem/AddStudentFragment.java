package com.example.studentmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.activity.MainActivity;
import com.example.studentmanagementsystem.backgroundTasks.BackgroundAsyncTaskOperations;
import com.example.studentmanagementsystem.backgroundTasks.BackgroundIntentService;
import com.example.studentmanagementsystem.backgroundTasks.BackgroundService;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.validator.Validate;

import java.util.ArrayList;


public class AddStudentFragment extends Fragment {


    private Button mSaveButton;
    private EditText mEtStudentName, mEtStudentRollNo;
    private DatabaseHelper db;
    private String[] mDialogItems={"ASYNC","SERVICE","INTENT SERVICE"};
    private Context mContext;
    private Communication mCommunication;

    public AddStudentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //to initialize button and editText fields
    private void initValues(View view) {
        mSaveButton = view.findViewById(R.id.btn_save);
        mEtStudentName = view.findViewById(R.id.et_studentName);
        mEtStudentRollNo = view.findViewById(R.id.et_studentRoll);
    }

    public void updateStudentDetails(Bundle bundle){
        //bundle.getString(Constant.MODE,Constant.EDIT);
        mEtStudentName.setText(bundle.getString(Constant.NAME));
        mEtStudentRollNo.setText(bundle.getString(Constant.ROLL_NO));
        editMode();
    }
    public void viewMode(Bundle bundleData){
        mEtStudentName.setText(bundleData.getString(Constant.VIEW_NAME));
        mEtStudentRollNo.setText(bundleData.getString(Constant.VIEW_ROLL));
        mSaveButton.setVisibility(View.GONE);
        mEtStudentName.setEnabled(false);
        mEtStudentRollNo.setEnabled(false);
    }

    //method to generate dialog box for choosing your operation
    private void generateDialogBox(final String operation, final String name, final String rollNo, final Bundle sendBundle){
        //alert dialog box to show list of operations
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setTitle(R.string.choose);
        mBuilder.setSingleChoiceItems(mDialogItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                switch(i){
                    case Constant.ASYNC :
                        (new BackgroundAsyncTaskOperations(mContext)).execute(operation,name,rollNo);
                        //((MainActivity)mContext).changeTab();
                        break;
                    case Constant.SERVICE :
                        Intent service=new Intent(mContext, BackgroundService.class);
                        service.putExtra(Constant.OPERATION,operation);
                        service.putExtra(Constant.NAME,name);
                        service.putExtra(Constant.ROLL_NO,rollNo);
                        mContext.startService(service);
                        //((MainActivity)mContext).changeTab();
                        break;
                    case Constant.INTENT_SERVICE :
                        Intent intent=new Intent(mContext, BackgroundIntentService.class);
                        intent.putExtra(Constant.OPERATION,operation);
                        intent.putExtra(Constant.NAME,name);
                        intent.putExtra(Constant.ROLL_NO,rollNo);
                        mContext.startService(intent);
                      //  ((MainActivity)mContext).changeTab();
                        break;
                    default:
                        break;

                }
                mCommunication.communicateAdd(sendBundle);

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
    /*
    method to edit details of student
    *@param intent - to take details from Main Activity
    *@param studentList - arraylist of Student object
    */
    @SuppressLint("SetTextI18n")
    public void editMode() {
        getActivity().setTitle(R.string.edit);
        mSaveButton.setText(getString(R.string.update));
        //to return name and roll number through intent to Main Activity and update data using preferred operation
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEtStudentName.getText().toString();
                String roll = mEtStudentRollNo.getText().toString();
                Bundle bundle= new Bundle();
                bundle.putString(Constant.MODE,Constant.EDIT);
                bundle.putString(Constant.NAME,name);
                bundle.putString(Constant.ROLL_NO,roll);
                generateDialogBox(Constant.EDIT,name,roll,bundle);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_add_student,container,false);
        initValues(view);
       // setValuesFromIntent();
        db = new DatabaseHelper(mContext);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to check if the the entered name is in valid format
                if (!Validate.isValidName(mEtStudentName.getText().toString().trim())) {
                    Toast.makeText(mContext, getString(R.string.invalidName),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //to check if the the entered roll number is in valid format
                if (!Validate.isValidRollNo(mEtStudentRollNo.getText().toString().trim())) {
                    Toast.makeText(mContext, getString(R.string.invalidRoll),
                            Toast.LENGTH_LONG).show();
                    return;
                }
               /* //to check if the the entered roll number is unique or not
                if (!Validate.isUniqueRollNo(mEtStudentRollNo.getText().toString().trim(),studentList)) {
                    Toast.makeText(getContext(), getString(R.string.rollNotUnique),
                            Toast.LENGTH_LONG).show();
                    return;
                }*/
                //to return name and roll number through intent to Main Activity and save data using preferred operation
                String name = mEtStudentName.getText().toString();
                String roll = mEtStudentRollNo.getText().toString();
                Bundle bundle= new Bundle();
                bundle.putString(Constant.MODE,Constant.NORMAL);
                bundle.putString(Constant.NAME,name);
                bundle.putString(Constant.ROLL_NO,roll);
                //mCommunication.communicateAdd(bundle);
                //db.insertStudent(name,roll);
                mEtStudentName.getText().clear();
                mEtStudentRollNo.getText().clear();
                generateDialogBox(Constant.NORMAL,name,roll,bundle);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        try {
            mCommunication=(Communication)mContext;
        }catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCommunication = null;
    }


}
