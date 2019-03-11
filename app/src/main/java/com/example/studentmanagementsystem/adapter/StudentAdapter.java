package com.example.studentmanagementsystem.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private ArrayList<Student> mStudentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mName, mRoll;
        //holds the particular view of recycler view
        public MyViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.tv_name);
            mRoll = (TextView) view.findViewById(R.id.tv_roll);
        }
    }
    /*creates the view
    *@param viewGroup - contains collection of views
    *@param i - position or index of view
    *@return particular view of recycler view
    */
    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup,
                false);
        return new MyViewHolder(itemView);
    }
    /*
    *param list - object of arraylist Student
    */
    public StudentAdapter(ArrayList<Student> mStudentList) {
        this.mStudentList = mStudentList;
    }
    //binds the data to the view and also uses old views
    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder myViewHolder, int i) {
        Student student = mStudentList.get(i);
        MyViewHolder viewHolder = (MyViewHolder) myViewHolder;
        viewHolder.mName.setText(student.getmName());
        viewHolder.mRoll.setText(student.getmRoll());
    }
    /*
    *@return size of arraylist to be inflated
    */
    @Override
    public int getItemCount() {
        return mStudentList.size();
    }
}
