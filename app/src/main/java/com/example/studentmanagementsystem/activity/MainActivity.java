package com.example.studentmanagementsystem.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.studentmanagementsystem.fragment.AddStudentFragment;
import com.example.studentmanagementsystem.communicator.Communication;
import com.example.studentmanagementsystem.fragment.StudentListFragment;
import com.example.studentmanagementsystem.adapter.MyViewPagerAdapter;
import com.example.studentmanagementsystem.R;

public class MainActivity extends AppCompatActivity implements Communication {

    private FragmentPagerAdapter mAdapterViewPager;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to set view pager
        mViewPager = findViewById(R.id.vp_viewPager);
        mAdapterViewPager = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapterViewPager);
        //to add tabLayout in view pager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
    //to switch between the two tabs
    public void changeTab(){
        if(mViewPager.getCurrentItem()==0){
            mViewPager.setCurrentItem(1);
        }else if(mViewPager.getCurrentItem()==1){
            mViewPager.setCurrentItem(0);
        }
    }
    /*
    *to provide communication method to add student details
    *@param bundle - to pass data
    */
    @Override
    public void communicateAdd(Bundle bundle) {
        String tag =getString(R.string.tag)+R.id.vp_viewPager+":"+0;
        StudentListFragment studentListFragment = (StudentListFragment) getSupportFragmentManager().findFragmentByTag(tag);
        studentListFragment.addStudent(bundle);
        changeTab();
    }
    /*
    *to provide communication method to update student details
    *@param bundle - to pass data
    */
    @Override
    public void communicateUpdate(Bundle bundle) {
        String tag =getString(R.string.tag)+R.id.vp_viewPager+":"+1;
        AddStudentFragment addStudentFragment = (AddStudentFragment) getSupportFragmentManager().findFragmentByTag(tag);
        addStudentFragment.updateStudentDetails(bundle);
        changeTab();
    }
}//end of MainActivity class

