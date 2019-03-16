package com.example.studentmanagementsystem.backgroundTasks;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.database.DatabaseHelper;

public class BackgroundService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //to insert and update in database using service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DatabaseHelper DatabaseHelper=new DatabaseHelper(this);
        if(intent.getStringExtra(Constant.OPERATION).equals(Constant.NORMAL)){
            DatabaseHelper.insertStudent(intent.getStringExtra(Constant.NAME),intent.getStringExtra(Constant.ROLL_NO));
        }else if(intent.getStringExtra(Constant.OPERATION).equals(Constant.EDIT)){
            DatabaseHelper.updateStudent(intent.getStringExtra(Constant.NAME),intent.getStringExtra(Constant.ROLL_NO));
        }
        stopSelf();
        return START_NOT_STICKY;
    }
}
