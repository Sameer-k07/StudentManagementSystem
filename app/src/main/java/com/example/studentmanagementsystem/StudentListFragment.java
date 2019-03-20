package com.example.studentmanagementsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.studentmanagementsystem.activity.AddStudentActivity;
import com.example.studentmanagementsystem.activity.MainActivity;
import com.example.studentmanagementsystem.adapter.StudentAdapter;
import com.example.studentmanagementsystem.backgroundTasks.BackgroundAsyncTaskGet;
import com.example.studentmanagementsystem.comparator.SortByName;
import com.example.studentmanagementsystem.comparator.SortByRollNo;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.listener.TouchListener;
import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;
import java.util.Collections;


public class StudentListFragment extends Fragment {
//implements BackgroundAsyncTaskGet.Callback {

    private Button mAddButton;
    private RelativeLayout mRlNoStudent;
    private ArrayList<Student> mStudentList = new ArrayList<Student>();
    private RecyclerView mRecyclerView;
    private StudentAdapter mAdapter;
    private String[] mDialogItems;
    private int pos;
    private DatabaseHelper db;
    private Context mContext;
    private Communication mCommunication;



    // TODO: Rename and change types of parameters



    public StudentListFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_student_list,container,false);
        db = new DatabaseHelper(getContext());
        mStudentList.addAll(db.getAllStudents());
        init(view);

        if(db.getStudentsCount()==0){
            mRlNoStudent.setVisibility(View.VISIBLE);
        }else{
            mRlNoStudent.setVisibility(View.INVISIBLE);
        }

        recyclerViewOperations();
        setHasOptionsMenu(true);
        //BackgroundAsyncTaskGet backgroundTask = new BackgroundAsyncTaskGet(mContext,mContext);
        //backgroundTask.execute();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


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
    //to initialize recycler view and adapter
    private void init(View view) {
        mAdapter = new StudentAdapter(mStudentList);
        mDialogItems = getResources().getStringArray(R.array.Dialog_Operations);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mRlNoStudent = view.findViewById(R.id.rl_noStudent);

        //default layout for recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //to add dividers in view
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //to show effect while on clicking elements in recycler view
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //setting adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);
        mAddButton = view.findViewById(R.id.btn_addStudent);


        //setting OnClickListener on add button to add data through intent via startActivityForResult method
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();

                //((MainActivity)mContext).changeTab();
            }
        });

    }
    //to perform operations on recycler view
    private void recyclerViewOperations(){
        //performing operations such as view,edit or delete while clicking on touch listener
        mRecyclerView.addOnItemTouchListener(new TouchListener(getContext(), mRecyclerView, new TouchListener.ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                final Student student = mStudentList.get(position);
                //alert dialog box to show list of operations
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                mBuilder.setTitle(R.string.choose);
                mBuilder.setSingleChoiceItems(mDialogItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final Intent intent = new Intent(mContext, AddStudentActivity.class);
                        Bundle bundle = new Bundle();

                        switch(i){
                            case Constant.VIEW_DATA :
                                viewDetails(intent,student);
                                break;
                            case Constant.EDIT_DATA :
                                setposition(position);
                                bundle.putString(Constant.MODE,Constant.EDIT);
                                bundle.putString(Constant.NAME,student.getmName());
                                bundle.putString(Constant.ROLL_NO,student.getmRollNo());
                                mCommunication.communicateUpdate(bundle);
                                //((MainActivity)mContext).changeTab();
                                break;
                            case Constant.DELETE_DATA:
                                setposition(position);
                                deleteDetails();
                                break;

                        }
                        dialogInterface.dismiss();
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

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    /*to view details of student
    @param intent - intent to pass data
    @param student - object to get student details
    */
    private void viewDetails(Intent intent,Student student){
        intent.putExtra(Constant.MODE,Constant.VIEW);
        Toast.makeText(getContext(),getString(R.string.your_choice_view),Toast.LENGTH_LONG).show();
        intent.putExtra(Constant.VIEW_NAME,student.getmName());
        intent.putExtra(Constant.VIEW_ROLL, student.getmRollNo());
        mContext.startActivity(intent);
    }
    //to delete details of student
    private void deleteDetails(){
        //alert dialog box for confirmation before deleting details of particular student
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(R.string.confirmation);
        alertDialogBuilder.setPositiveButton(R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        db.deleteStudent(mStudentList.get(pos));
                        mStudentList.remove(getposition());
                        //if list is empty after deletion, show initial MainActivity
                        if(mStudentList.isEmpty()){
                            mRlNoStudent.setVisibility(View.VISIBLE);
                            mAdapter.notifyDataSetChanged();

                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Toast.makeText(getContext(),getString(R.string.your_choice_delete),Toast.LENGTH_LONG).show();

    }


    //this is done to take position of edited element and use the position in OnActivityResult
    /*
     *@param position - index of the edited element
     */
    private void setposition(int position){
        pos=position;
    }
    /*
     *@return index of the edited element
     */
    private int getposition(){
        return pos;
    }

    /*method to get result from async task through callback
     *@param result - object of arraylist student
     *//*
    @Override
    public void getResult(ArrayList<Student> result) {
        mStudentList=result;
        mAdapter = new StudentAdapter(mStudentList);
        mRecyclerView.setAdapter(mAdapter);
    }*/

    public void addStudent(Bundle bundle){
        if(bundle.getString(Constant.MODE).equals(Constant.NORMAL)) {
            Student student = new Student(bundle.getString("Name"), bundle.getString("Roll"));
            mStudentList.add(student);
            mAdapter.notifyDataSetChanged();
        }else if(bundle.getString(Constant.MODE).equals(Constant.EDIT)){
            Student student=mStudentList.get(pos);
            student.setmRollNo(bundle.getString(Constant.ROLL_NO));
            student.setmName(bundle.getString(Constant.NAME));
            mAdapter.notifyDataSetChanged();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = (MenuItem) menu.findItem(R.id.menu_switchId);
        //setting the action view by using switch layout
        item.setActionView(R.layout.switch_layout);
        Switch switch_toggle = menu.findItem(R.id.menu_switchId).getActionView().findViewById(R.id.switch_toggle);
        //toggle button to switch between linear layout and grid layout
        switch_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    //setting the recycler view for grid layout
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,GridLayoutManager.VERTICAL));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,GridLayoutManager.HORIZONTAL));
                    Toast.makeText(mContext,getString(R.string.grid_layout),Toast.LENGTH_LONG).show();
                } else {
                    //setting the recycler view for linear layout
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,LinearLayoutManager.VERTICAL));
                    Toast.makeText(mContext,getString(R.string.linear_layout),Toast.LENGTH_LONG).show();
                }
            }
        });
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //sort by name using collections and notifying adapter about the changes
            case R.id.menu_sort_by_name:
                Toast.makeText(getContext(),getString(R.string.sortByName),Toast.LENGTH_LONG).show();
                Collections.sort(mStudentList,new SortByName());
                mAdapter.notifyDataSetChanged();
                return true;
            //sort by roll number using collections and notifying adapter about the changes
            case R.id.menu_sort_by_roll:
                Toast.makeText(getContext(),getString(R.string.sortByRoll),Toast.LENGTH_LONG).show();
                Collections.sort(mStudentList,new SortByRollNo());
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
