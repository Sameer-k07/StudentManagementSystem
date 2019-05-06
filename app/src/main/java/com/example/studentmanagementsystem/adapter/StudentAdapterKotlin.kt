package com.example.studentmanagementsystem.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studentmanagementsystem.R
import com.example.studentmanagementsystem.model.StudentKotlin
import java.util.ArrayList

class StudentAdapterKotlin
(private var mStudentList: ArrayList<StudentKotlin>) : RecyclerView.Adapter<StudentAdapterKotlin.MyViewHolder>() {
    inner class MyViewHolder
    (view: View) : RecyclerView.ViewHolder(view) {
        val mName: TextView
        val mRollNo: TextView

        init {
            mName = view.findViewById(R.id.tv_name)
            mRollNo = view.findViewById(R.id.tv_rollNo)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): StudentAdapterKotlin.MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_layout, viewGroup,
                false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val student = mStudentList[p1]
        p0.mName.text = student.mName
        p0.mRollNo.text = student.mRollNo
    }

    override fun getItemCount(): Int {
        return mStudentList.size
    }

    fun updateData(newList: ArrayList<StudentKotlin>) {
        this.mStudentList = ArrayList()
        this.mStudentList.addAll(newList)
        notifyDataSetChanged()
    }
}
