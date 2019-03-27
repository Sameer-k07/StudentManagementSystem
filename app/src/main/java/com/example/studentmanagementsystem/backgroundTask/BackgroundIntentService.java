package com.example.studentmanagementsystem.backgroundTask;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.database.DatabaseHelper;

public class BackgroundIntentService extends IntentService {

    // Creates an BackgroundIntentService.
    public BackgroundIntentService() {
        super("BackgroundIntentService");

    }
    /*
    *to handle events i.e insertion and updation in database using BackgroundIntentService
    *@param intent - to pass data
    */
    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseHelper DatabaseHelper=new DatabaseHelper(this);
        if(intent.getStringExtra(Constant.OPERATION).equals(Constant.NORMAL)){
            DatabaseHelper.insertStudent(intent.getStringExtra(Constant.NAME),intent.getStringExtra(Constant.ROLL_NO));
        }else if(intent.getStringExtra(Constant.OPERATION).equals(Constant.EDIT)){
            DatabaseHelper.updateStudent(intent.getStringExtra(Constant.NAME),intent.getStringExtra(Constant.ROLL_NO));
        }
        //setting up broadcast receiver
        intent.setAction(Constant.ACTION);
        String echoMessage = getString(R.string.broadcast_message) ;
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra(getString(R.string.message), echoMessage));
    }
}