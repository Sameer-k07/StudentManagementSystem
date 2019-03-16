package com.example.studentmanagementsystem.backgroundTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.studentmanagementsystem.constant.Constant;
import com.example.studentmanagementsystem.database.DatabaseHelper;

public class BackgroundAsyncTaskOperations extends AsyncTask<String,Void,String> {

    Context context;

    public BackgroundAsyncTaskOperations(Context context){
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //to insert and update through Async task
    @Override
    protected String doInBackground(String... params) {
        String method=params[0];
        String name=params[1];
        String roll=params[2];
        DatabaseHelper dbHelper=new DatabaseHelper(context);
        switch (method){
            case Constant.NORMAL:
                dbHelper.insertStudent(name,roll);
                dbHelper.close();
                break;
            case Constant.EDIT:
                dbHelper.updateStudent(name,roll);
                dbHelper.close();
                break;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }
    @Override
    protected void onPostExecute(String result) {
    }

}
