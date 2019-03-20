package com.example.studentmanagementsystem.backgroundTask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;

public class BackgroundAsyncTaskGet extends AsyncTask<String, Void, ArrayList<Student>> {
    Context ctx;
    Callback callback;

    public BackgroundAsyncTaskGet(Context ctx, Callback callback) {
        this.ctx = ctx;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //returning database entries through AsyncTask
    @Override
    protected ArrayList<Student> doInBackground(String... params) {
        DatabaseHelper db = new DatabaseHelper(ctx);
        return db.getAllStudents();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<Student> result) {
        callback.getResult(result);
    }
    //interface to send arraylist to MainActivity
    public interface Callback {
        void getResult(ArrayList<Student> result);
    }
}
