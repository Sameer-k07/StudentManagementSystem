package com.example.studentmanagementsystem.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.activity.AddStudentActivity;
import com.example.studentmanagementsystem.communicator.Communication;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.backgroundTask.BackgroundAsyncTaskOperations;
import com.example.studentmanagementsystem.backgroundTask.BackgroundIntentService;
import com.example.studentmanagementsystem.backgroundTask.BackgroundService;
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
    private ArrayList<Student> mStudentList = new ArrayList<Student>();
    private StudentBroadcastReceiver mStudentBroadcastReceiver = new StudentBroadcastReceiver();

    //to register broadcast receiver
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Constant.ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mStudentBroadcastReceiver,intentFilter);
    }
    //to unregister broadcast receiver
    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mStudentBroadcastReceiver);
    }

    public AddStudentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_add_student,container,false);
        initValues(view);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        try {
            mCommunication=(Communication)mContext;
        }catch (ClassCastException e) {
            throw new ClassCastException(getString(R.string.error));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCommunication = null;
    }

    //to initialize button and editText fields
    private void initValues(View view) {
        mSaveButton = view.findViewById(R.id.btn_save);
        mEtStudentName = view.findViewById(R.id.et_studentName);
        mEtStudentRollNo = view.findViewById(R.id.et_studentRoll);
    }
    /*
    *method to update details of student
    *@param bundle - to pass data
    */
    public void updateStudentDetails(Bundle bundle){
        if(bundle.getString(Constant.MODE).equals(Constant.EDIT)) {
            mEtStudentName.setText(bundle.getString(Constant.NAME));
            mEtStudentRollNo.setText(bundle.getString(Constant.ROLL_NO));
            editMode();
        }else if(bundle.getString(Constant.MODE).equals(Constant.NORMAL)){
            mStudentList=bundle.getParcelableArrayList(Constant.STUDENT_LIST_FROM_MAIN);
            onClickButton();
        }
    }
    /*
    *method to view details of student
    *@param bundleData - to pass data
    */
    public void viewMode(Bundle bundleData){
        mEtStudentName.setText(bundleData.getString(Constant.VIEW_NAME));
        mEtStudentRollNo.setText(bundleData.getString(Constant.VIEW_ROLL));
        mSaveButton.setVisibility(View.GONE);
        mEtStudentName.setEnabled(false);
        mEtStudentRollNo.setEnabled(false);
    }

    /*method to generate dialog box for choosing your operation
    *@param operation - to select operation to perform
    *@param name - name of student
    *@param rollNo - roll number of student
    *@param sendBundle - to send data
    */
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
                        break;
                    case Constant.SERVICE :
                        Intent service=new Intent(mContext, BackgroundService.class);
                        service.putExtra(Constant.OPERATION,operation);
                        service.putExtra(Constant.NAME,name);
                        service.putExtra(Constant.ROLL_NO,rollNo);
                        mContext.startService(service);
                        break;
                    case Constant.INTENT_SERVICE :
                        Intent intent=new Intent(mContext, BackgroundIntentService.class);
                        intent.putExtra(Constant.OPERATION,operation);
                        intent.putExtra(Constant.NAME,name);
                        intent.putExtra(Constant.ROLL_NO,rollNo);
                        mContext.startService(intent);
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
    //method to edit details of student
    @SuppressLint("SetTextI18n")
    public void editMode() {
        getActivity().setTitle(R.string.edit);
        mSaveButton.setText(getString(R.string.update));
        //to return name and roll number through bundle to StudentList Fragment and update data using preferred operation
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

    //to set on click listener on save button
    private void onClickButton(){
        mSaveButton.setText(R.string.add_student);
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
                //to check if the the entered roll number is unique or not
                if (!Validate.isUniqueRollNo(mEtStudentRollNo.getText().toString().trim(),mStudentList)) {
                    Toast.makeText(getContext(), getString(R.string.rollNotUnique),
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //to return name and roll number through bundle to StudentList Fragment and save data using preferred operation
                String name = mEtStudentName.getText().toString();
                String roll = mEtStudentRollNo.getText().toString();
                Bundle bundle= new Bundle();
                bundle.putString(Constant.MODE,Constant.NORMAL);
                bundle.putString(Constant.NAME,name);
                bundle.putString(Constant.ROLL_NO,roll);
                mEtStudentName.getText().clear();
                mEtStudentRollNo.getText().clear();
                generateDialogBox(Constant.NORMAL,name,roll,bundle);
            }
        });
    }
    //Inner broadcast receiver that receives the broadcast if the services have indeed added the elements in the database.
    public class StudentBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,intent.getStringExtra(getString(R.string.message)),Toast.LENGTH_LONG).show();
        }
    }
}
