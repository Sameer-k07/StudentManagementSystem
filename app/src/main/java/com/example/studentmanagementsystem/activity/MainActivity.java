package com.example.studentmanagementsystem.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.studentmanagementsystem.adapter.StudentAdapter;
import com.example.studentmanagementsystem.comparator.SortByName;
import com.example.studentmanagementsystem.comparator.SortByRoll;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.listener.TouchListener;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.R;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity  {

    Button addButton;
    RelativeLayout emptyView;
    public static ArrayList<Student> studentList = new ArrayList<Student>();
    private RecyclerView mRecyclerView;
    private StudentAdapter mAdapter;
    private String[] mDialogItems;
    private int pos;
    private static final int VIEW=0;
    private static final int EDIT=1;
    private static final int DELETE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        recyclerViewOperations();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,GridLayoutManager.VERTICAL));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,GridLayoutManager.HORIZONTAL));
                    Toast.makeText(MainActivity.this,getString(R.string.grid_layout),Toast.LENGTH_LONG).show();
                } else {
                    //setting the recycler view for linear layout
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,LinearLayoutManager.VERTICAL));
                    Toast.makeText(MainActivity.this,getString(R.string.linear_layout),Toast.LENGTH_LONG).show();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    /*
    to sort either by name or by roll number
    *@param item - menu item
    *@return true or false
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //sort by name using collections and notifying adapter about the changes
            case R.id.menu_sort_by_name:
                Toast.makeText(MainActivity.this,getString(R.string.sortByName),Toast.LENGTH_LONG).show();
                Collections.sort(studentList,new SortByName());
                mAdapter.notifyDataSetChanged();
                return true;
            //sort by roll number using collections and notifying adapter about the changes
            case R.id.menu_sort_by_roll:
                Toast.makeText(MainActivity.this,getString(R.string.sortByRoll),Toast.LENGTH_LONG).show();
                Collections.sort(studentList,new SortByRoll());
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init(){
        mDialogItems=getResources().getStringArray(R.array.Dialog_Operations);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new StudentAdapter(studentList);

        //default layout for recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //to add dividers in view
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,LinearLayoutManager.VERTICAL));
        //to show effect while on clicking elements in recycler view
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //setting adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);
        addButton =(Button) findViewById(R.id.btn_add);

        //setting OnClickListener on add button to add data through intent via startActivityForResult method
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                intent.putExtra(Constant.MODE,Constant.NORMAL);
                startActivityForResult(intent, 1);
            }
        });

    }

     public void recyclerViewOperations(){

            //performing operations such as view,edit or delete while clicking on touch listener
            mRecyclerView.addOnItemTouchListener(new TouchListener(MainActivity.this, mRecyclerView, new TouchListener.ClickListener() {
                @Override
                public void onClick(final View view, final int position) {
                    final Student student = studentList.get(position);
                    //alert dialog box to show list of operations
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    mBuilder.setTitle(R.string.choose);
                    mBuilder.setSingleChoiceItems(mDialogItems, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            final Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                            switch(i){
                                case VIEW :
                                    view(intent,student);
                                    break;
                                case EDIT :
                                    setposition(position);
                                    edit(intent,student);
                                    break;
                                case DELETE :
                                    setposition(position);
                                    delete(intent,student);
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

        //to view details of student
        public void view(Intent intent,Student student){
            intent.putExtra(Constant.MODE,Constant.VIEW);
            Toast.makeText(MainActivity.this,getString(R.string.your_choice_view),Toast.LENGTH_LONG).show();
            intent.putExtra(Constant.VIEW_NAME,student.getmName());
            intent.putExtra(Constant.VIEW_ROLL, student.getmRoll());
            startActivity(intent);
        }
        //to edit details of student
        public void edit(Intent intent,Student student){
            intent.putExtra(Constant.MODE,Constant.EDIT);
            intent.putExtra(Constant.VIEW_NAME,student.getmName());
            intent.putExtra(Constant.VIEW_ROLL, student.getmRoll());
            intent.putExtra(Constant.POSITION,Integer.toString(getposition()));
            Toast.makeText(MainActivity.this,getString(R.string.your_choice_edit),Toast.LENGTH_LONG).show();
            startActivityForResult(intent, 2);
        }
        //to delete details of student
        public void delete(Intent intent,Student student){
            //alert dialog box for confirmation before deleting details of particular student
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setMessage(R.string.confirmation);
            alertDialogBuilder.setPositiveButton(R.string.yes,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            studentList.remove(getposition());
                            //if list is empty after deletion, show initial MainActivity
                            if(studentList.size()==0){
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                startActivity(intent);
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
            Toast.makeText(MainActivity.this,getString(R.string.your_choice_delete),Toast.LENGTH_LONG).show();

        }


    //this is done to take position of edited element and use the position in OnActivityResult
    /*
    *@param position - index of the edited element
    */
    public void setposition(int position){
        pos=position;
    }
    /*
     *@return index of the edited element
     */
    public int getposition(){
        return pos;
    }
    /*
    *@param requestCode - to match code for fetching data
    *@param resultCode - to verify the data
    *@param data - data passed through intent
    */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        emptyView=(RelativeLayout) findViewById(R.id.empty_view);
        //set no student added screen when countof students is zero
        if(mAdapter.getItemCount()==-1){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.INVISIBLE);
        }
        //matching requestCode and resultCode
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //fetching data through intent and adding it to list and notifying adapter about the changes
            String name=data.getStringExtra(Constant.NAME);
            String roll=data.getStringExtra(Constant.ROLL);
            studentList.add(new Student(name,roll));
            mAdapter.notifyDataSetChanged();

            }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //fetching updated data through intent and adding it to list and notifying adapter about the changes
            String updatedName=data.getStringExtra(Constant.NAME);
            String updatedRoll=data.getStringExtra(Constant.ROLL);
            //updating information at same index
            Student updatedInfo=studentList.get(getposition());
            updatedInfo.setmName(updatedName);
            updatedInfo.setmRoll(updatedRoll);
            mAdapter.notifyDataSetChanged();
        }
    }
}//end of MainActivity class
