package com.example.studentmanagementsystem.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.studentmanagementsystem.AddStudentFragment;
import com.example.studentmanagementsystem.Communication;
import com.example.studentmanagementsystem.StudentListFragment;
import com.example.studentmanagementsystem.MyPagerAdapter;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.constant.Constant;

public class MainActivity extends AppCompatActivity implements Communication {

    FragmentPagerAdapter adapterViewPager;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void changeTab(){
        if(viewPager.getCurrentItem()==0){
            viewPager.setCurrentItem(1);
        }else if(viewPager.getCurrentItem()==1){
            viewPager.setCurrentItem(0);
        }
    }


    @Override
    public void communicateAdd(Bundle bundle) {
        String tag ="android:switcher:"+R.id.vpPager+":"+0;
        StudentListFragment f = (StudentListFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.addStudent(bundle);
        changeTab();
    }

    @Override
    public void communicateUpdate(Bundle bundle) {
        String tag ="android:switcher:"+R.id.vpPager+":"+1;
        AddStudentFragment f = (AddStudentFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.updateStudentDetails(bundle);
        changeTab();
    }
}//end of MainActivity class

