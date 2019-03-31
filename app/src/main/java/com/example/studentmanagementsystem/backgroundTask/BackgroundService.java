package com.example.studentmanagementsystem.backgroundTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.studentmanagementsystem.R;
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
        //setting up broadcast receiver
        intent.setAction(Constant.ACTION);
        String echoMessage = Constant.MESSAGE;
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra(Constant.MESSAGE, echoMessage));
        stopSelf();
        return START_NOT_STICKY;
    }
}
