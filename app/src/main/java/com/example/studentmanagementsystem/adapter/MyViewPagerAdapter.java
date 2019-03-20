package com.example.studentmanagementsystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.fragment.AddStudentFragment;
import com.example.studentmanagementsystem.fragment.StudentListFragment;

public class MyViewPagerAdapter extends FragmentPagerAdapter {


    public MyViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    //@return - total number of pages
    @Override
    public int getCount() {
        return Constant.NUM_ITEMS;
    }
    /*
    *to get fragment to dispaly page
    *@param position - index
    *@return - fragment to display for that page
    */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constant.STUDENT_LIST:
                return new StudentListFragment();
            case Constant.ADD_STUDENT:
                return new AddStudentFragment();
            default:
                return null;
        }
    }
    /*
    *to get page title
    *@param position - index
    *@return - page title for the top indicator
    */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == Constant.STUDENT_LIST) {
            return "Student List";
        } else if (position == Constant.ADD_STUDENT) {
            return "Add Student";
        }
        return null;
    }
}

